(ns fabricated-metal-repair.facts-test
  (:require [clojure.test :refer [deftest is]]
            [fabricated-metal-repair.facts :as facts]))

(deftest jpn-has-a-spec-basis
  (is (some? (facts/spec-basis "JPN")))
  (is (string? (:repair-safety-provenance (facts/spec-basis "JPN"))))
  (is (= :qualitative (:threshold-model (facts/spec-basis "JPN"))))
  (is (nil? (:notification-lead-days (facts/spec-basis "JPN")))))

(deftest usa-is-honestly-qualitative-not-fabricated
  (is (= :qualitative (:threshold-model (facts/spec-basis "USA"))))
  (is (nil? (:notification-lead-days (facts/spec-basis "USA")))))

(deftest deu-is-honestly-qualitative-not-fabricated
  (is (= :qualitative (:threshold-model (facts/spec-basis "DEU"))))
  (is (nil? (:notification-lead-days (facts/spec-basis "DEU")))))

(deftest unknown-jurisdiction-has-no-fabricated-spec-basis
  (is (nil? (facts/spec-basis "ATL"))))

(deftest coverage-never-reports-a-missing-jurisdiction-as-covered
  (let [report (facts/coverage ["JPN" "ATL" "USA"])]
    (is (= 2 (:covered report)))
    (is (= ["ATL"] (:missing-jurisdictions report)))
    (is (= ["JPN" "USA"] (:covered-jurisdictions report)))))

;; ----------------------------- notification-lead-insufficient? -----------------------------
;; UNLIKE installation.facts (JPN quantitative), this catalog found NO
;; quantitative jurisdiction for the post-repair inspection-before-
;; return-to-service duty -- every covered jurisdiction always returns
;; :qualitative, never a fabricated true/false.

(deftest jpn-never-gets-a-fabricated-true-false
  (is (= :qualitative (facts/notification-lead-insufficient? "JPN" {})))
  (is (= :qualitative (facts/notification-lead-insufficient? "JPN" {:anything 100}))))

(deftest usa-never-gets-a-fabricated-true-false
  (is (= :qualitative (facts/notification-lead-insufficient? "USA" {})))
  (is (= :qualitative (facts/notification-lead-insufficient? "USA" {:anything 0}))))

(deftest deu-never-gets-a-fabricated-true-false
  (is (= :qualitative (facts/notification-lead-insufficient? "DEU" {})))
  (is (= :qualitative (facts/notification-lead-insufficient? "DEU" {:anything 0}))))

(deftest unknown-jurisdiction-returns-nil-not-a-guess
  (is (nil? (facts/notification-lead-insufficient? "ATL" {:anything 100}))))

(deftest no-jurisdiction-in-this-catalog-is-ever-quantitative
  (is (every? #(= :qualitative (:threshold-model (facts/spec-basis %))) (keys facts/catalog))
      "cloud-itonami-isic-3311's honest research found zero quantitative jurisdictions for the post-repair inspection-before-return-to-service duty -- see ns docstring"))

;; ----------------------------- catalog citation honesty -----------------------------

(deftest jpn-cites-real-boiler-pressure-vessel-change-inspection-law
  (let [sb (facts/spec-basis "JPN")]
    (is (re-find #"ボイラー及び圧力容器安全規則" (:repair-safety-basis sb)))
    (is (re-find #"第41条" (:repair-safety-basis sb)))
    (is (re-find #"第42条" (:repair-safety-basis sb)))
    (is (re-find #"laws\.e-gov\.go\.jp" (:repair-safety-provenance sb)))))

(deftest usa-cites-real-national-board-inspection-code-provision
  (let [sb (facts/spec-basis "USA")]
    (is (re-find #"National Board Inspection Code" (:repair-safety-basis sb)))
    (is (re-find #"3\.2\.2" (:repair-safety-basis sb)))
    (is (re-find #"nationalboard\.org" (:repair-safety-provenance sb)))))

(deftest deu-cites-real-betrsichv-anhang-2-provision
  (let [sb (facts/spec-basis "DEU")]
    (is (re-find #"Betriebssicherheitsverordnung" (:repair-safety-basis sb)))
    (is (re-find #"Anhang 2" (:repair-safety-basis sb)))
    (is (re-find #"gesetze-im-internet\.de" (:repair-safety-provenance sb)))
    (is (re-find #"2009/104" (:repair-safety-basis sb))
        "cites the correct EU use-of-work-equipment directive backing the BetrSichV inspection duty")))

(deftest uncovered-jurisdiction-has-no-fabricated-catalog-entry
  (is (nil? (facts/spec-basis "ATL"))))
