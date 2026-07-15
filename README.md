# cloud-itonami-isic-3311

Open Business Blueprint for **ISIC Rev.5 3311**: repair of fabricated
metal products.

This repository designs a forkable OSS business for fabricated-metal-
product-repair-shop operations coordination: run by a qualified operator
so a community keeps its own operating records instead of renting a
closed SaaS.

ISIC 3311 covers repair of **fabricated metal products** specifically --
structural steel members and frames, storage tanks, boilers/pressure
vessels and metal furniture -- distinct from the more specific/other
repair classes 3312 (machinery and equipment), 3313 (electronic and
optical equipment), 3314 (electrical equipment), 3315 (transport
equipment, except motor vehicles) and the residual class 3319 (other
equipment).

## Scope -- this is a COORDINATION-ONLY actor, not equipment control

This is a safety-relevant domain: fabricated-metal-product repair can
involve structural-integrity risk, weld-repair-quality defects and
pressure-vessel/boiler test failures. **This actor does NOT hold
repair-equipment/welding-tool control authority, and it does NOT hold
return-to-service sign-off authority.** Both are the licensed repair
technician's/inspector's exclusive authority, always. The Repair Advisor
(LLM) never issues an equipment/tool-control command and never signs off
on returning repaired equipment to service; the independent **Repair
Governor** HARD-blocks any proposal that even tries (un-overridable by
any human approval -- see `fabricated-metal-repair.governor` ns
docstring). This actor coordinates *potential* diagnostic/repair/testing
dispatch (a proposed schedule window, a flagged concern, a supply-order
proposal) -- it never directly actuates.

Structurally, EVERY proposal this actor's advisor can produce carries
`:effect :propose`, and the Repair Governor HARD-holds any proposal that
doesn't -- this is a permanent invariant distinguishing this actor from
actors whose sibling ops DO commit real-world effects.

## Core Contract

```text
equipment/work-order record + independent verification
        |
        v
Advisor -> Repair Governor -> proceed (log/schedule/flag/order proposal), hold, or human approval
        |
        v
coordination artifacts (schedule proposal, safety-concern flag,
supply-order proposal) + audit ledger -- NEVER repair-equipment/
welding-tool dispatch, NEVER a return-to-service sign-off
```

No automated advice can propose a schedule the governor refuses, suppress
a safety-concern flag, or slip an equipment-control/return-to-service
marker past the governor -- and `:flag-safety-concern` always needs a
human sign-off regardless of how clean the governor's check comes back
(see `Actuation` below).

## Capability layer

Resolves via [`kotoba-lang/industry`](https://github.com/kotoba-lang/industry)
(ISIC `3311`). Required capabilities:

- `:identity`
- `:forms`
- `:audit-ledger`

## Implemented slice (`src/fabricated_metal_repair`)

`blueprint.edn` names the governor `:fabricated-metal-repair-governor`
and is now `:implemented`. This repo implements it end-to-end --
**Repair Advisor ⊣ Repair Governor** -- following the SAME `.cljc` actor
pattern (langgraph-clj StateGraph, mock-by-default advisor, dual
MemStore/Datomic backend, 0→3 phase rollout) every prior
`cloud-itonami-isic-*` actor in this fleet uses, structured after
[`cloud-itonami-isic-3314`](https://github.com/cloud-itonami/cloud-itonami-isic-3314)
(Repair of Electrical Equipment) and
[`cloud-itonami-isic-3319`](https://github.com/cloud-itonami/cloud-itonami-isic-3319)
(Repair of Other Equipment) -- the closest structural analogs: also
coordination-only repair actors with the same closed op-allowlist and
schedule-op auto-eligibility -- narrowed to fabricated-metal-product-
repair-shop diagnostic/repair/testing coordination as described above.

### Closed op-allowlist (4 ops, all `:effect :propose`)

| Op | Ask | Implementation |
|---|---|---|
| `:log-repair-record` | diagnostic-finding / repair-work-performed / parts-used data logging | Normalizes and commits a patch onto the equipment/work-order's ground-truth fields (`:equipment-verified?`, concern resolution, etc.) and appends an immutable repair-record-log entry. No direct capital/safety risk -- MAY auto-commit at phase 3. |
| `:schedule-repair-operation` | diagnostic/repair/testing scheduling proposal | Drafts a proposed schedule WINDOW (never a repair-equipment/welding-tool control command or a return-to-service sign-off). MAY auto-commit at phase 3 when the governor is clean -- see `Actuation` below. |
| `:flag-safety-concern` | surface a structural-integrity failure / weld-repair-quality defect / pressure-test failure concern | Drafts a safety-concern flag; ALWAYS escalates to a human, unconditionally. Once approved, `fabricated-metal-repair.notify` sends the notice (mail + phone, mock only -- see `Actuation`) to the equipment/work-order's repair-technician/shop-safety-officer contact roster. |
| `:order-supplies` | replacement-parts / welding-consumables procurement proposal | Drafts a supply-order proposal. Escalates above a cost threshold or below the confidence floor; may auto-commit at phase 3 otherwise. |

**Legal basis is data, not code** -- `src/fabricated_metal_repair/facts.cljc`'s
`catalog` is the per-jurisdiction EDN source-of-truth the governor checks
every `:schedule-repair-operation` proposal against (JPN/USA/DEU seeded,
the same honest-coverage convention `installation.facts`/`demolition.
facts`/`construction.facts`/`electrical-equipment-repair.facts`/`other-
equipment-repair.facts` use; DEU stands in for the EU):

| Jurisdiction | Post-repair inspection-before-return-to-service legal basis |
|---|---|
| 🇯🇵 Japan | ボイラー及び圧力容器安全規則（昭和47年労働省令第33号）第41条（ボイラー変更届）・第42条（変更検査 -- 胴、ドーム、炉筒、火室、鏡板、天井板、管板、管寄せ又はステー等の構造上重要な部分を溶接により修繕しようとするときは所轄労働基準監督署長にボイラー変更届を提出し、変更検査に合格した後でなければ変更後のボイラーを使用してはならない義務。第一種圧力容器についても第76条・第77条に同様の義務） -- [e-Gov](https://laws.e-gov.go.jp/law/347M50002000033) |
| 🇺🇸 USA | National Board Inspection Code (NBIC, ANSI/NB-23, published by the National Board of Boiler and Pressure Vessel Inspectors) Part 3 (Repairs and Alterations): 3.2.2(e) requires a repaired pressure-retaining part to receive a pressure test as required by the original code of construction; 4.4.2(c) permits NDE in lieu of a hydrostatic test for an alteration, subject to Inspector/jurisdiction concurrence. Most U.S. state boiler-and-pressure-vessel-safety laws adopt the NBIC as the legally binding repair code -- [nationalboard.org](https://www.nationalboard.org/index.aspx?pageID=164&ID=440) |
| 🇪🇺 EU (DEU proxy) | Betriebssicherheitsverordnung (BetrSichV) Anhang 2 (zu den §§15, 16) Abschnitt 4, Nummer 4.2 (Prüfungen vor Inbetriebnahme und nach prüfpflichtigen Änderungen -- the inspection following a test-obligatory change, e.g. a structural weld repair of a pressure-bearing wall, must confirm the equipment was changed in conformity and functions safely before returning to operation), grounded in Directive 2009/104/EC (minimum safety and health requirements for the use of work equipment by workers) -- [gesetze-im-internet.de](https://www.gesetze-im-internet.de/betrsichv_2015/anhang_2.html) |

All three seeded jurisdictions are honestly `:qualitative` here -- every
source is a PROCEDURAL requirement (notify/inspect a repaired structural
or pressure-bearing part before it returns to service, with a pressure
test or an equivalent non-destructive test) with no fixed numeric
advance-notice-days count this actor could independently verify.
`fabricated-metal-repair.facts/notification-lead-insufficient?` reports
`:qualitative` for every covered jurisdiction rather than fabricating a
number. See `fabricated-metal-repair.facts` ns docstring for the full
honesty discipline.

**Governor -- six HARD checks, ALL un-overridable by human approval:**
unknown op (outside the closed 4-op allowlist), `:effect` not
`:propose`, forbidden action class (repair-equipment/welding-tool-
control / direct-actuation / return-to-service-sign-off markers),
equipment/work-order not independently verified/registered, legal-basis
missing, unresolved safety concern. See `fabricated-metal-repair.
governor` ns docstring for the full enumeration, rationale and real-law
citations behind each.

## Actuation

This actor performs **no real-world actuation** -- every committed
record carries `:effect :propose` (see `fabricated-metal-repair.
governor` ns docstring). `:flag-safety-concern` NEVER auto-commits at any
phase -- it always needs a human sign-off, even when the governor is
completely clean (`fabricated-metal-repair.phase` ns docstring
'Actuation' section, `fabricated-metal-repair.governor`'s `high-stakes`
set).

**Like `cloud-itonami-isic-3314`'s/`cloud-itonami-isic-3319`'s
`:schedule-repair-operation` (and UNLIKE `cloud-itonami-isic-3320`'s
`:schedule-installation-operation`, which always escalates because it
coordinates potential heavy-lift/rigging-equipment dispatch), this
actor's `:schedule-repair-operation` MAY auto-commit at phase 3** when
the governor is clean (equipment independently verified, legal-basis on
file, no unresolved safety concern). The schedule op itself is still only
ever a proposed diagnostic/repair/testing WINDOW, never a live-work
authorization, and this actor's own HARD checks (equipment-verification,
legal-basis-on-file, no-unresolved-concern) PLUS its forbidden-action-
class block on any `:return-to-service-sign-off?` marker already gate
the real hazard surface independently of phase. `:log-repair-record`
(pure data logging) and `:order-supplies` BELOW the cost threshold
(`fabricated-metal-repair.governor/supply-order-cost-threshold-usd`)
also MAY auto-commit at phase 3 when the governor is clean.

This build also deliberately ships **NO JVM-only interop anywhere in
`src/`** -- `fabricated-metal-repair.notify` ships only the deterministic
mock `Notifier` (no real Resend/Twilio transport), per this workspace's
cljs-first `.cljc` runtime-priority rule. A real transport can be added
later behind the same protocol via a portable HTTP client without
changing this actor's shape.

```bash
clojure -M:dev:run    # demo: full coordination episode + every HARD hold
clojure -M:dev:test   # test suite
clojure -M:lint       # clj-kondo, errors fail
```

## License

AGPL-3.0-or-later.
