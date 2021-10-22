# clojure-benchmark

A simple library for benchmarking bits of clojure code.


## Examples

```clojure
(require '[clojure-benchmark.bench :as bench])

(println "- big reduce:        " (bench/report #(reduce * (range 10000000))))
(println "- int/float division:" (bench/report #(int (/ 101 10))))
(println "- float division:    " (bench/report #(/ 101 10)))
(println "- integer division:  " (bench/report #(quot 101 10)))
```

### Output:

```text
- big reduce:         {:total-ops 10**1, :total-time 3.1s, :per-op-time 314.1ms}
- int/float division: {:total-ops 10**7, :total-time 1.7s, :per-op-time 173ns}
- float division:     {:total-ops 10**7, :total-time 865.0ms, :per-op-time 86ns}
- integer division:   {:total-ops 10**7, :total-time 74.7ms, :per-op-time 7ns}
```

## Utilities

Generate HTML documentation:

```
$ lein codox
```

Run tests (once):

```
$ lein spec
```

Run Tests (auto-run on-save):

```
$ lein spec -a
```

Deploy to Clojars (requires username and deploy token):

```
$ lein deploy clojars
```
