(ns fabricated-metal-repair.facts
  "Per-jurisdiction post-repair (weld-repair on a pressure-bearing or
  structural load-bearing part) inspection-before-return-to-service
  regulatory catalog -- the spec-basis table the Repair Governor checks
  every `:schedule-repair-operation` proposal against ('did the advisor
  cite an OFFICIAL public source for this jurisdiction's change-
  notification-plus-inspection / pressure-test-or-NDE / qualified-
  inspector duty before a repaired fabricated-metal item returns to
  service, or did it invent one?'). Same honest-coverage discipline
  `installation.facts`/`demolition.facts`/`construction.facts`/
  `electrical-equipment-repair.facts`/`other-equipment-repair.facts`
  established for this fleet: a jurisdiction not in this table has NO
  spec-basis, full stop -- the advisor must not fabricate one, and the
  governor holds if it tries.

  Coverage is reported HONESTLY (see `coverage`); this is a STARTING
  catalog (JPN/USA/DEU/SWE), not a from-scratch survey of all ~194
  jurisdictions. Extending coverage is additive: add one map to `catalog`,
  cite a real source, done -- never invent a jurisdiction's requirements
  to make coverage look bigger.

  UNLIKE `installation.facts` (which found ONE `:quantitative`
  jurisdiction -- Japan's Industrial Safety and Health Act Article 88 --
  for an installation-notification PLAN filing), this catalog's research
  found ZERO `:quantitative` jurisdictions among JPN/USA/DEU/SWE for the
  post-repair inspection-before-return-to-service duty itself: every
  seeded source is a PROCEDURAL requirement (notify/inspect a repaired
  structural or pressure-bearing part before it returns to service, with
  a pressure test or an equivalent non-destructive test) with no fixed
  numeric advance-notice-days count. This actor does NOT invent one:
    :qualitative -- the law imposes a documented change-notification-
                    plus-inspection / pressure-test-or-NDE / qualified-
                    inspector duty before a welded/structurally repaired
                    fabricated-metal item (tank, boiler, pressure vessel,
                    structural steel member) returns to service (JPN/USA/
                    DEU/SWE below), with NO fixed jurisdiction-wide
                    numeric lead-time this actor could independently
                    verify at the time this catalog was built.
                    `notification-lead-insufficient?` therefore always
                    returns `:qualitative` for a covered jurisdiction in
                    this catalog -- the Repair Governor's `legal-basis-
                    missing` HARD check (see `fabricated-metal-repair.
                    governor` ns docstring) is the bright line this
                    catalog actually supports; there is no numeric
                    lead-time bright line to independently re-check on
                    top of it.

  Real sources, verified before this catalog was written (no
  fabrication):
    JPN -- ボイラー及び圧力容器安全規則（昭和47年労働省令第33号）第41条
           （ボイラー変更届）・第42条（変更検査）: an employer that
           repairs a boiler by welding on a structurally significant
           part (胴、ドーム、炉筒、火室、鏡板、天井板、管板、管寄せ又は
           ステー等) must submit a ボイラー変更届 (boiler-change
           notification) to the competent Labour Standards Inspection
           Office chief, and the boiler may not be put back into use
           after the repair until it has passed a 変更検査 (change
           inspection). The same regulation applies an equivalent
           変更届/変更検査 duty to Class-1 pressure vessels at Article 76
           (change notification) and Article 77 (change inspection) --
           https://laws.e-gov.go.jp/law/347M50002000033
    USA -- National Board Inspection Code (NBIC, ANSI/NB-23, published by
           the National Board of Boiler and Pressure Vessel Inspectors),
           Part 3 (Repairs and Alterations): NBIC 3.2.2(e) requires a
           repaired pressure-retaining part to receive a pressure test as
           required by the original code of construction; NBIC 4.4.2(c)
           permits non-destructive examination (NDE) in lieu of a
           hydrostatic test for an alteration, subject to the concurrence
           of the Inspector and, where applicable, the jurisdiction. Most
           U.S. state boiler-and-pressure-vessel-safety laws adopt the
           NBIC as the legally binding repair/alteration code within
           their jurisdiction -- https://www.nationalboard.org/index.aspx?pageID=164&ID=440
    DEU -- Betriebssicherheitsverordnung (BetrSichV) Anhang 2 (zu den
           §§15 und 16) Abschnitt 4, Nummer 4.2 (Prüfungen vor
           Inbetriebnahme und nach prüfpflichtigen Änderungen): the
           inspection following a prüfpflichtige Änderung (a
           test-obligatory change, including a structural weld repair of
           a pressure-bearing wall) must confirm the pressure-equipment
           installation was changed in conformity with the regulation and
           functions safely before it is returned to operation. Nummer
           5.7 permits the required static pressure test
           (Festigkeitsprüfung) to be replaced by a non-destructive
           examination (zerstörungsfreie Prüfung) when the employer
           presents an inspection concept confirmed by an approved
           monitoring body (zugelassene Überwachungsstelle, e.g. TÜV) --
           https://www.gesetze-im-internet.de/betrsichv_2015/anhang_2.html.
           Grounded, like this fleet's other DEU/EU entries, in Directive
           2009/104/EC (minimum safety and health requirements for the
           use of work equipment by workers), which requires that workers
           carrying out repairs, modifications, maintenance or servicing
           of work equipment be specifically designated to do so.
    SWE -- Arbetsmiljöverkets föreskrifter (AFS 2023:11) om
           arbetsutrustning och personlig skyddsutrustning – säker
           användning (i kraft sedan den 1 januari 2025, ändrad genom
           AFS 2024:4; upphäver och ersätter AFS 2017:3 om användning
           och kontroll av trycksatta anordningar -- this actor verified
           AFS 2023:11 is the CURRENT instrument, not the repealed
           AFS 2017:3 a naive search would surface), 9 kap. (Användning
           av trycksatta anordningar) 40-42 §§ tillsammans med 10 kap.
           (Kontroll av trycksatta anordningar) 7, 20 och 21 §§: 9 kap.
           40 § kräver att en arbetsgivare som utför eller låter utföra
           en väsentlig reparation eller ändring av en trycksatt
           anordning i klass A eller B upprättar dokumentation enligt
           41 §, som enligt 41 § punkterna 4-5 ska visa att 'en
           oförstörande provning är utförd med godkänt resultat' och
           att 'en tryckkontroll är utförd efter åtgärden' (a
           non-destructive examination has been performed with an
           approved/passing result, and a pressure test has been
           carried out after the repair/alteration); 10 kap. 20 §
           requires the employer to have a revisionskontroll (revision
           inspection) carried out on the repaired/altered class A/B
           device by an accredited kontrollorgan (inspection body
           accredited under SS-EN ISO/IEC 17020 and Regulation (EC)
           No 765/2008), which per 10 kap. 21 § checks whether the
           device after the measure 'fortfarande är lämplig för sitt
           ändamål' (is still fit for its purpose) and whether the
           9 kap. 40-42 §§ documentation was met; 10 kap. 7 § forbids
           the employer from pressurising ('trycksätta') a class A or
           B device unless a kontrollorgan has, at a kontroll, assessed
           that it meets this chapter's requirements -- i.e. a
           repaired/altered pressure-bearing device may not be
           returned to service until the accredited body's post-repair
           revisionskontroll has passed --
           https://www.av.se/globalassets/filer/publikationer/foreskrifter/konsoliderade-foreskrifter/arbetsutrustning-och-personlig-skyddsutrustning-saker-anvandning-afs2023-11-konsoliderad.pdf
           (263-page consolidated PDF fetched and read in full for this
           catalog entry; landing page
           https://www.av.se/arbetsmiljoarbete-och-inspektioner/publikationer/foreskrifter/afs-202311/).
           Like USA/DEU, and UNLIKE JPN's ボイラー変更届 filed with the
           Labour Standards Inspection Office, Sweden's duty runs
           through an accredited third-party inspection body rather
           than a pre-repair filing with Arbetsmiljöverket itself --
           this catalog does not force a notification-to-authority
           element onto a jurisdiction whose verified source does not
           have one.

  DEU is used as the EU-jurisdiction proxy, the SAME convention
  `installation.facts`/`demolition.facts`/`construction.facts`/
  `aerospace.facts`/`electrical-equipment-repair.facts`/`other-
  equipment-repair.facts` established -- there is no ISO-3166 alpha-3
  code for the EU itself.")

(def catalog
  "iso3 -> requirement map. `:repair-safety-basis` / its `-provenance`,
  plus `:owner-authority`, are the citation the governor requires before a
  `:schedule-repair-operation` proposal can ever commit."
  {"JPN" {:name "Japan"
          :owner-authority "厚生労働省（労働基準監督署長）"
          :repair-safety-basis "ボイラー及び圧力容器安全規則（昭和47年労働省令第33号）第41条・第42条（ボイラー変更届・変更検査 -- 胴、ドーム、炉筒、火室、鏡板、天井板、管板、管寄せ又はステー等の構造上重要な部分を溶接により修繕しようとするときは所轄労働基準監督署長にボイラー変更届を提出し、変更検査に合格した後でなければ変更後のボイラーを使用してはならない義務。第一種圧力容器についても第76条・第77条に同様の変更届・変更検査の義務がある）"
          :repair-safety-provenance "https://laws.e-gov.go.jp/law/347M50002000033"
          :threshold-model :qualitative
          :notification-lead-days nil
          :threshold-note "ボイラー及び圧力容器安全規則第41条・第42条は構造上重要な部分の溶接修繕について変更届の提出と変更検査への合格を義務付けるが、固定日数の事前届出リードタイムは定めていない -- ここで数値を創作しない。"}
   "USA" {:name "United States"
          :owner-authority "National Board of Boiler and Pressure Vessel Inspectors; the jurisdictional (state) Chief Boiler Inspector"
          :repair-safety-basis "National Board Inspection Code (NBIC, ANSI/NB-23) Part 3 (Repairs and Alterations): 3.2.2(e) requires a repaired pressure-retaining part to receive a pressure test as required by the original code of construction; 4.4.2(c) permits non-destructive examination (NDE) in lieu of a hydrostatic test for an alteration, subject to Inspector and jurisdiction concurrence. Most U.S. state boiler-and-pressure-vessel-safety laws adopt the NBIC as the legally binding repair code."
          :repair-safety-provenance "https://www.nationalboard.org/index.aspx?pageID=164&ID=440"
          :threshold-model :qualitative
          :notification-lead-days nil
          :threshold-note "The NBIC requires a pressure test (or NDE substitute, Inspector/jurisdiction permitting) be passed BEFORE a repaired pressure-retaining fabricated-metal item returns to service, but sets no fixed nationwide advance-notice-days count -- this actor does not invent one. This actor's `:schedule-repair-operation` still always requires an on-file legal-basis citation regardless (see `fabricated-metal-repair.governor` ns docstring `legal-basis-missing`)."}
   "DEU" {:name "Germany (EU jurisdiction proxy, see ns docstring)"
          :owner-authority "Bundesministerium für Arbeit und Soziales (BMAS); Vollzug durch die zuständige Landesbehörde und die zugelassene Überwachungsstelle (ZÜS, z. B. TÜV)"
          :repair-safety-basis "Betriebssicherheitsverordnung (BetrSichV) Anhang 2 (zu den §§15, 16) Abschnitt 4 Nummer 4.2 (Prüfungen vor Inbetriebnahme und nach prüfpflichtigen Änderungen -- die Prüfung nach einer prüfpflichtigen Änderung, z.B. einer strukturellen Schweißreparatur an einer drucktragenden Wandung, hat zu bestätigen, dass die Druckanlage vorschriftsmäßig geändert wurde und sicher funktioniert, bevor sie wieder in Betrieb genommen wird); Nummer 5.7 erlaubt den Ersatz der Festigkeitsprüfung (statische Druckprobe) durch eine zerstörungsfreie Prüfung bei bestätigtem Prüfkonzept einer zugelassenen Überwachungsstelle; grounded in Directive 2009/104/EC (minimum safety and health requirements for the use of work equipment by workers), which requires that workers carrying out repairs of work equipment are specifically designated to do so"
          :repair-safety-provenance "https://www.gesetze-im-internet.de/betrsichv_2015/anhang_2.html"
          :threshold-model :qualitative
          :notification-lead-days nil
          :threshold-note "BetrSichV Anhang 2 Abschnitt 4 Nummer 4.2/5.7は圧力設備の構造的な溶接修繕後、再稼働前に検査（耐力試験又は非破壊検査）を義務付けるのみで、日本のボイラー及び圧力容器安全規則第41条・第42条のような固定日数の事前届出リードタイムはEU/ドイツの圧力設備関連規則では法定されていない -- ここで数値を創作しない。"}
   "SWE" {:name "Sweden"
          :owner-authority "Arbetsmiljöverket (Swedish Work Environment Authority); kontroll performed by an accredited kontrollorgan (inspection body accredited by Swedac under Regulation (EC) No 765/2008 and SS-EN ISO/IEC 17020)"
          :repair-safety-basis "Arbetsmiljöverkets föreskrifter (AFS 2023:11) om arbetsutrustning och personlig skyddsutrustning – säker användning (i kraft sedan den 1 januari 2025, ändrad genom AFS 2024:4; upphäver och ersätter AFS 2017:3 om användning och kontroll av trycksatta anordningar) 9 kap. 40–42 §§ (reparation eller ändring av trycksatta anordningar i klass A eller B) tillsammans med 10 kap. 7, 20 och 21 §§ (kontroll av trycksatta anordningar) -- 9 kap. 40 § kräver att en arbetsgivare som utför eller låter utföra en väsentlig reparation eller ändring av en trycksatt anordning i klass A eller B upprättar dokumentation enligt 41 §, som enligt 41 § punkterna 4–5 ska visa att 'en oförstörande provning är utförd med godkänt resultat' och att 'en tryckkontroll är utförd efter åtgärden' (a non-destructive examination has been performed with an approved/passing result, and a pressure test has been carried out after the repair/alteration); 10 kap. 20 § requires the employer to have a revisionskontroll (revision inspection) carried out on the repaired/altered class A/B device by an accredited kontrollorgan, which per 10 kap. 21 § checks whether the device after the measure 'fortfarande är lämplig för sitt ändamål' (is still fit for its purpose) and whether the documentation meets the 9 kap. 40–42 §§ requirements; 10 kap. 7 § forbids the employer from pressurising ('trycksätta') a class A or B device unless a kontrollorgan has, at a kontroll, assessed that it meets this chapter's requirements -- i.e. a repaired/altered pressure-bearing device may not be returned to service (pressurised) until the accredited inspection body's post-repair revisionskontroll has passed. Supersedes the previously current AFS 2017:3 om användning och kontroll av trycksatta anordningar, repealed by AFS 2023:11's transitional provisions p.1-2 effective 2025-01-01."
          :repair-safety-provenance "https://www.av.se/globalassets/filer/publikationer/foreskrifter/konsoliderade-foreskrifter/arbetsutrustning-och-personlig-skyddsutrustning-saker-anvandning-afs2023-11-konsoliderad.pdf"
          :threshold-model :qualitative
          :notification-lead-days nil
          :threshold-note "AFS 2023:11 9 kap. 40–42 §§ and 10 kap. 7, 20–21 §§ require a documented non-destructive test or pressure test after a substantial repair/alteration of a class A/B pressure-bearing device, and forbid the employer from re-pressurising it until an accredited inspection body's revisionskontroll (revision inspection) confirms conformity, but set no fixed jurisdiction-wide numeric advance-notice-days count this actor could independently verify -- this actor does not invent one. Like USA/DEU, Sweden's duty runs through an accredited third-party inspection body rather than a direct pre-repair filing with Arbetsmiljöverket itself (unlike JPN's ボイラー変更届 to the Labour Standards Inspection Office) -- this actor does not force a notification-to-authority element where the verified source does not have one."}})

(defn spec-basis
  "The jurisdiction's requirement map, or nil -- nil means NO spec-basis,
  and the governor must hold any `:schedule-repair-operation` proposal
  that tries to cite one."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report: how many of the requested jurisdictions actually
  have a spec-basis entry. Never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-isic-3311 R0: " (count catalog)
                 " jurisdictions seeded with an official spec-basis. "
                 "This is a starting catalog, not a survey of all ~194 "
                 "jurisdictions -- extend `fabricated-metal-repair."
                 "facts/catalog`, never fabricate a jurisdiction's requirements.")})))

(defn notification-lead-insufficient?
  "Independently recompute whether a jurisdiction has a fixed numeric
  advance-notice-days requirement this actor could re-check. Three-valued,
  deliberately (the same shape `installation.facts/notification-lead-
  insufficient?` established):
    true/false   -- never produced by this catalog (see ns docstring):
                    none of JPN/USA/DEU/SWE carries a `:quantitative`
                    threshold-model for the post-repair inspection-
                    before-return-to-service duty.
    :qualitative -- a jurisdiction with NO fixed numeric lead-time (every
                    covered jurisdiction in this catalog). This actor
                    cannot independently confirm 'sufficient' or
                    'insufficient' by arithmetic alone. Never fabricate a
                    lead-time here.
    nil          -- no spec-basis at all for `iso3` (a jurisdiction not in
                    `catalog`)."
  [iso3 _equipment]
  (when-let [{:keys [threshold-model]} (spec-basis iso3)]
    (case threshold-model
      :qualitative :qualitative
      nil)))
