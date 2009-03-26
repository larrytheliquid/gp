(ns gp.problems.atm.charge (:use gp))

(defn nand [x y]
  (not (and x y)))

(defn nor [x y]
  (not (or x y)))

;; (defn fitness [individual]
;;   (+ 
;;    (if (= (individual true true true)    true) 1 0)
;;    (if (= (individual true true false)   true) 1 0)
;;    (if (= (individual true false true)   false) 1 0)
;;    (if (= (individual true true true)    true) 1 0)
;;    (if (= (individual true false false)  true) 1 0)
;;    (if (= (individual false true true)   true) 1 0)
;;    (if (= (individual false false true)  false) 1 0)
;;    (if (= (individual false false false) false) 1 0)))

(defn fitness [i] 
  (prn (i))
  8)

(defn termination [individual]
  (= 8 (fitness individual)))

(evolve {:generations 500 :population-size 200 :max-height 5
	 :fitness fitness :termination termination
	 :functions ['nand 'nor] :terminals [true false]
	 :paramaters ['statement-printed
		      'money-withdrawn
		      'money-deposited]
	 :output (fn [g b _] (println "Generation: " g) (prn b))})