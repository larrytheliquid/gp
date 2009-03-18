(ns test.gp (:use gp fact.core fact.output.verbose))

(fact "initialize: full tree of specified height" 
  [[height tree]
   {0 (p true)
    1 (p 'nand (p true) (p true))
    2 (p 'nand
	 (p 'nand (p true) (p true))
	 (p 'nand (p true) (p true)))}]
  (= tree (initialize ['nand] [true] height)))

(fact "to-sexp: sexp from tree" 
  [[tree sexp]
   {(p true) true
    (p false) false
    (p 'and (p true) (p true)) '(and true true)
    (p 'and (p true) (p false)) '(and true false)
    (p 'or (p true) (p false)) '(or true false)
    (p 'or (p false) (p 'and (p true) (p true))) '(or false (and true true))
    (p 'or (p false) (p 'and (p false) (p true))) '(or false (and false true))}]
  (= sexp (to-sexp tree)))

(fact "to-fn: function from tree sexp" 
  [[tree returned]
   {(p true) true
    (p false) false
    (p 'and (p true) (p true)) true
    (p 'and (p true) (p false)) false
    (p 'or (p true) (p false)) true
    (p 'or (p false) (p 'and (p true) (p true))) true
    (p 'or (p false) (p 'and (p false) (p true))) false}]
  (= returned (apply (to-fn tree))))

(print-results "gp:" (verify-facts 'test.gp))