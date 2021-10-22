(ns clojure-benchmark.bench-spec
  (:require [speclj.core :refer :all]
            [clojure-benchmark.bench :as sut]))

(describe "Benchmarks"
  (with-stubs)

  (it "invokes a function (f) a specified number of times (n)"
    (let [n 100
          f (stub :f)
          {:keys [total-times]} (sut/bench n f)]
      (->> total-times (should= n))
      (should-have-invoked :f {:times n :with []})))

  (it "measures the total time elapsed and the per-op average"
    (with-redefs [sut/time-it (stub :time-it {:return 50})]
      (let [n 10
            f #()
            {:keys [total-elapsed per-op-average]} (sut/bench n f)]
        (->> total-elapsed (should= 50))
        (->> per-op-average (should= 5))
        (should-have-invoked :time-it {:with [n f] :times 1}))))
  )

