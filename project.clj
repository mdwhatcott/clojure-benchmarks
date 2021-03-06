(defproject org.clojars.mdwhatcott/benchmarks "0.3.1"
  :description "Benchmarking utilities for Clojure code."
  :url "https://github.com/mdwhatcott/clojure-benchmarks"
  :license {:name "MIT"
            :url  "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.3"]]
  :profiles {:dev {:dependencies [[speclj "3.3.2"]]}}
  :plugins [[speclj "3.3.2"]
            [lein-codox "0.10.7"]]
  :codox {:project     {:name "clojure-benchmark"}
          :output-path "docs"
          :metadata    {:doc/format :markdown}
          :source-uri  "https://github.com/mdwhatcott/clojure-benchmark/blob/{version}/{filepath}#L{line}"}
  :test-paths ["spec"])
