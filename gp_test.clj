(ns test.gp (:use gp fact.core fact.output.verbose))

(fact "initialize: full tree of specified height" 
  [[height tree] 
   {0 (p true)
    1 (p 'nand (p true) (p true))
    2 (p 'nand
	 (p 'nand (p true) (p true))
	 (p 'nand (p true) (p true)))}]
  (= tree (initialize ['nand] [true] height)))

(print-results "gp:" (verify-facts 'test.gp))