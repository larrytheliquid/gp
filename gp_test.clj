(ns test.gp (:use gp fact.core fact.output.verbose))

(fact "initialize returns full tree of specified height" 
  [[height tree]
   {0 true
    1 '(nand true true)
    2 '(nand
	(nand true true)
	(nand true true))}]
  (= tree (initialize ['nand] [true] height)))

(fact "count-nodes returns number of nodes in tree" 
  [[tree result]
   {true 1
    '(not true) 2
    '(and true true) 3
    '(and (and true true)
	  (and true true)) 7
    '(and (and true true)
	  (and (not true) true)) 8}]
  (= result (count-nodes tree)))

(fact "height returns height of the longest branch" 
  [[tree result]
   {true 0
    '(and true true) 1
    '(not true true) 1
    '(and (and true true)
	  (and true true)) 2
    '(and (and true true)
	  (and (not true) 
	       true)) 3}]
  (= result (height tree)))

(fact "nth-node returns node at given point according to depth-first traversal"
  [[[tree n] result] 
   {[true 0] true
    [false 0] false
    ['(and true false) 1] true
    ['(and true false) 2] false
    ['(and (not true) 
	   (or false (not false))) 2] true
    ['(and (not true) 
	   (or false (not false))) 5] '(not false)
    ['(and
       (not (not (not true)))
       (or (or false false) (not false))) 8] false}]
  (= result (nth-node n tree)))

;; (fact "replace-node returns tree with node at given point replaced according to depth-first traversal"
;;   [[[tree n replacement] result]
;;    {[true 0 false] false
;;     [false 0 true] true
;;     [(p 'and true false) 1 false] (p 'and false false)
;;     [(p 'and true false) 2 true] (p 'and true true)
;;     [(p 'and 
;; 	(p 'not true)
;; 	(p 'or false (p 'not false))) 2 false]
;;     (p 'and 
;;        (p 'not false)
;;        (p 'or false (p 'not false)))
;; ;;     [(p 'and 
;; ;; 	(p 'not true)
;; ;; 	(p 'or false (p 'not false))) 5 false]
;; ;;     (p 'and 
;; ;;        false
;; ;;        (p 'or false (p 'not false)))
;; ;;     [(p 'and
;; ;; 	(p 'not (p 'not (p 'not true)))
;; ;; 	(p 'or (p 'or false false) (p 'not false))) 8 true]
;; ;;     (p 'and
;; ;;        (p 'not (p 'not (p 'not true)))
;; ;;        (p 'or (p 'or false true) (p 'not false)))
;;     }]
;; (prn (replace-node n replacement tree))
;;   (= result (replace-node n replacement tree)))

(fact "to-fn returns function from tree sexp" 
  [[tree returned]
   {true true
    false false
    '(and true true) true
    '(and true false) false
    '(or true false) true
    '(or false (and true true)) true
    '(or false (and false true)) false}]
  (= returned (apply (to-fn tree))))

(print-results "gp" (verify-facts 'test.gp))