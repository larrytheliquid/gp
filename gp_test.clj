(ns test.gp (:use gp fact.core fact.output.verbose))

(fact "initialize returns full tree of specified height" 
  [[height tree]
   {0 (p true)
    1 (p 'nand (p true) (p true))
    2 (p 'nand
	 (p 'nand (p true) (p true))
	 (p 'nand (p true) (p true)))}]
  (= tree (initialize ['nand] [true] height)))

(fact "count-nodes returns number of nodes in tree" 
  [[tree result]
   {(p true) 1
    (p 'not (p true) ) 2
    (p 'and (p true) (p true)) 3
    (p 'and
       (p 'and (p true) (p true))
       (p 'and (p true) (p true))) 7
    (p 'and 
       (p 'and (p true) (p true))
       (p 'and 
	  (p 'not (p true)) 
	  (p true))) 8}]
  (= result (count-nodes tree)))

(fact "height returns height of the longest branch" 
  [[tree result]
   {(p true) 0
    (p 'and (p true) (p true)) 1
    (p 'not (p true) ) 1
    (p 'and
       (p 'and (p true) (p true))
       (p 'and (p true) (p true))) 2
    (p 'and 
       (p 'and (p true) (p true))
       (p 'and 
	  (p 'not (p true)) 
	  (p true))) 3}]
  (= result (height tree)))

(fact "nth-node returns node at given point according to depth-first traversal"
  [[[tree n] result] 
   {[(p true) 0] (p true)
    [(p false) 0] (p false)
    [(p 'and (p true) (p false)) 1] (p true)
    [(p 'and (p true) (p false)) 2] (p false)
    [(p 'and 
	(p 'not (p true))
	(p 'or (p false) (p 'not (p false)))) 2] (p true)
    [(p 'and 
	(p 'not (p true))
	(p 'or (p false) (p 'not (p false)))) 5] (p 'not (p false))
    [(p 'and
	(p 'not (p 'not (p 'not (p true))))
	(p 'or (p 'or (p false) (p false)) (p 'not (p false)))) 8] (p false)}]
  (= result (nth-node n tree)))

(fact "replace-node returns tree with node at given point replaced according to depth-first traversal"
  [[[tree n replacement] result]
   {[(p true) 0 (p false)] (p false)
    [(p false) 0 (p true)] (p true)
    [(p 'and (p true) (p false)) 1 (p false)] (p 'and (p false) (p false))
    [(p 'and (p true) (p false)) 2 (p true)] (p 'and (p true) (p true))
    [(p 'and 
	(p 'not (p true))
	(p 'or (p false) (p 'not (p false)))) 2 (p false)]
    (p 'and 
       (p 'not (p false))
       (p 'or (p false) (p 'not (p false))))
;;     [(p 'and 
;; 	(p 'not (p true))
;; 	(p 'or (p false) (p 'not (p false)))) 5 (p false)]
;;     (p 'and 
;;        (p false)
;;        (p 'or (p false) (p 'not (p false))))
;;     [(p 'and
;; 	(p 'not (p 'not (p 'not (p true))))
;; 	(p 'or (p 'or (p false) (p false)) (p 'not (p false)))) 8 (p true)]
;;     (p 'and
;;        (p 'not (p 'not (p 'not (p true))))
;;        (p 'or (p 'or (p false) (p true)) (p 'not (p false))))
    }]
(prn (replace-node n replacement tree))
  (= result (replace-node n replacement tree)))

(fact "to-sexp returns sexp from tree" 
  [[tree sexp]
   {(p true) true
    (p false) false
    (p 'and (p true) (p true)) '(and true true)
    (p 'and (p true) (p false)) '(and true false)
    (p 'or (p true) (p false)) '(or true false)
    (p 'or (p false) (p 'and (p true) (p true))) '(or false (and true true))
    (p 'or (p false) (p 'and (p false) (p true))) '(or false (and false true))}]
  (= sexp (to-sexp tree)))

(fact "to-fn returns function from tree sexp" 
  [[tree returned]
   {(p true) true
    (p false) false
    (p 'and (p true) (p true)) true
    (p 'and (p true) (p false)) false
    (p 'or (p true) (p false)) true
    (p 'or (p false) (p 'and (p true) (p true))) true
    (p 'or (p false) (p 'and (p false) (p true))) false}]
  (= returned (apply (to-fn tree))))

(print-results "gp" (verify-facts 'test.gp))