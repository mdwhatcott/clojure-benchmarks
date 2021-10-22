(ns clojure-benchmark.bench)

(defmacro time-ns [ex]
  `(let [start# (. System (nanoTime))]
     (do ~ex (- (. System (nanoTime)) start#))))

(defn time-it [n f]
  (time-ns (dotimes [_ n] (f))))

(defn bench
  "bench receives a function `f` and an integer `n`.
  It runs `f` `n` times and reports the total elapsed
  time as well as the per-operation average time elapsed.
  It is intended to be called by a higher-level function
  that will pass in successively higher values of `n`
  until a time threshold is crossed."
  [n f]
  (let [elapsed (time-it n f)
        average (quot elapsed n)]
    {:total-times    n
     :total-elapsed  elapsed
     :per-op-average average}))
