(ns benchmarks.bench-spec
  (:require [speclj.core :refer :all]
            [benchmarks.bench :as bench]))

(defn fake-time-it
  "This hand-rolled fake is meant to stand in for the time-it
  function. It treats 'f' as a function that returns a multiplier,
  which, when applied to 'n' produces a spoofed duration value.
  The benchmark algorithm calls time-it with successively higher
  values of n, so all the test need do is provide an 'f' that
  returns a multiplier to elicit the intended behavior for the
  rest of the algorithm."
  [f n] (* n (f)))

(defn slow-f [] bench/millisecond)
(defn fast-f [] bench/nanosecond)

(describe "Durations"
  (for [[input expected] {1                          "1ns"
                          1000                       "1.0µs"
                          1100                       "1.1µs"
                          (* 2200 bench/microsecond) "2.2ms"
                          (* 3300 bench/millisecond) "3.3s"}]
    (it (format "formats %d nanoseconds as: %s" input expected)
      (should= expected (bench/format-duration input)))))

(describe "Benchmarking"
  (with-stubs)

  (context "with the low-level bench function"
    (it "invokes a function (f) a specified number of times (n)"
      (let [n 100
            f (stub :f)
            {:keys [total-ops]} (bench/bench f n)]
        (->> total-ops (should= n))
        (should-have-invoked :f {:times n :with []})))

    (it "measures the total time elapsed and the per-op average"
      (with-redefs [bench/time-it (stub :time-it {:return 50})]
        (let [n 10
              f #()
              {:keys [total-ns per-op-ns]} (bench/bench f n)]
          (->> total-ns (should= 50))
          (->> per-op-ns (should= 5))
          (should-have-invoked :time-it {:with [f n] :times 1})))))

  (context "with increasing values of 'n'"

    (context "and a slow-running function"
      (it "returns the first stats where :total_elapsed >= max time threshold"
        (with-redefs [bench/time-it fake-time-it]
          (let [{:keys [total-ops total-time per-op-time]} (bench/report slow-f)]
            (->> total-time (should= "1.0s"))
            (->> per-op-time (should= "1.0ms"))
            (->> total-ops (should= "10**3"))))))

    (context "and a fast-running function"
      (it "returns the first stats where 'n' >= max operations threshold"
        (with-redefs [bench/time-it fake-time-it]
          (let [{:keys [total-ops total-time per-op-time]} (bench/report fast-f)]
            (->> total-ops (should= "10**7"))
            (->> per-op-time (should= "1ns"))
            (->> total-time (should= "10.0ms")))))))
  )

#_(describe "Examples"
    (it "prints reports"
      (println "- big reduce:        " (bench/report #(reduce * (range 10000000))))
      (println "- int/float division:" (bench/report #(int (/ 101 10))))
      (println "- float division:    " (bench/report #(/ 101 10)))
      (println "- integer division:  " (bench/report #(quot 101 10)))))
