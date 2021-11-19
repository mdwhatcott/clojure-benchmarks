# clojure-benchmarks

[![cljdoc badge](https://cljdoc.org/badge/org.clojars.mdwhatcott/benchmarks)](https://cljdoc.org/d/org.clojars.mdwhatcott/benchmarks) [![Clojars Project](https://img.shields.io/clojars/v/org.clojars.mdwhatcott/benchmarks.svg)](https://clojars.org/org.clojars.mdwhatcott/benchmarks)


A simple library for benchmarking bits of clojure code.


## [API Documentation](https://mdwhatcott.github.io/clojure-benchmarks/)

## Examples

```clojure
(require '[benchmarks.bench :as bench])

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

## Dev Utilities

Generate HTML documentation:

```
$ make docs
```

Run tests (once):

```
$ make test
```

Run Tests (auto-run on-save):

```
$ make test-auto
```

Publish to Clojars (runs tests, generates docs, and requires username, deploy token, and GPG key):

```
$ make publish
```
