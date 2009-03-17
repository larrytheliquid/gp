(ns test.gp (:use fact.core fact.output.color))

(fact "testing" []
  (= 3 (+ 1 2)))

(print-results "gp" (verify-facts 'test.gp))