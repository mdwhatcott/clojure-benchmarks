(ns clojure-benchmark.bench)

(def nanosecond 1)
(def microsecond (* nanosecond 1000))
(def millisecond (* microsecond 1000))
(def second_ (* millisecond 1000))

(defn format-duration
  "format-duration turns nanosecond durations, such as those provided
  by bench and converts them to a microsecond, millisecond, or second
  value (ie. 32ns, 43.2µs, 54.7ms, 3.1s)."
  [ns]
  (cond
    (zero? ns) "0"
    (>= ns second_),,,, (format "%.1fs" (float (/ ns second_)))
    (>= ns millisecond) (format "%.1fms" (float (/ ns millisecond)))
    (>= ns microsecond) (format "%.1fµs" (float (/ ns microsecond)))
    :else,,,,,,,,,,,,,, (format "%dns" ns)))

(def max-ops 10000000)
(def max-duration second_)

(defmacro ^:private time-ns [ex]
  `(let [start# (. System (nanoTime))]
     (do ~ex (- (. System (nanoTime)) start#))))

(defn time-it [f n]
  (time-ns (dotimes [_ n] (f))))

(defn bench
  "bench receives a function `f` and an integer `n`.
  It runs `f` `n` times and reports the total elapsed
  time as well as the per-operation average time elapsed.
  It is intended to be called by a higher-level function
  that will pass in successively higher values of `n`
  until a time threshold is crossed."
  [f n]
  (let [elapsed (time-it f n)
        average (quot elapsed n)]
    {:total-ops  n
     :total-ns   elapsed
     :per-op-ns  average}))

(defn- within-time-threshold [{:keys [total-ns]}] (< total-ns max-duration))
(defn- within-ops-threshold [{:keys [total-ops]}] (< total-ops max-ops))

(def ^:private within-thresholds
  (every-pred within-time-threshold
              within-ops-threshold))

(def ^:private powers-of-10 (partial * 10))

(defn benchmark
  "benchmark passes the provided function `f` to bench
  with successively higher `n` values until it has either:
  1. exhausted a reasonable amount of time or
  2. measured enough executions to have an accurate
  per-operation average.
  It returns the low-level results of the last call to bench."
  [f]
  (->> (iterate powers-of-10 1)
       (map (partial bench f))
       (drop-while within-thresholds)
       first))

(defn report
  "Translates the result of benchmark into more human-readable values.
  Can be passed to println for a decent report."
  [f]
  (let [{:keys [total-ops total-ns per-op-ns]} (benchmark f)]
    {:total-ops   (format "10**%d" (count (filter #{\0} (str total-ops))))
     :total-time  (format-duration total-ns)
     :per-op-time (format-duration per-op-ns)}))
