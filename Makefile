#!/usr/bin/make -f

test:
	lein spec

test-auto:
	lein spec -a

docs:
	lein codox && git add . && git commit "Latest docs." && git push origin main

publish: test docs
	lein deploy clojars
