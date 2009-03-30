(ns gp.problems.atm.charge (:use gp))

(defn fitness [individual]
  (+ 
   (if (= (individual true true true)    true) 1 0)
   (if (= (individual true true false)   true) 1 0)
   (if (= (individual true false true)   false) 1 0)
   (if (= (individual true true true)    true) 1 0)
   (if (= (individual true false false)  true) 1 0)
   (if (= (individual false true true)   true) 1 0)
   (if (= (individual false false true)  false) 1 0)
   (if (= (individual false false false) false) 1 0)))

(defn termination [individual]
  (= 8 (fitness individual)))

(def parameters
  ['statement-printed
   'money-withdrawn
   'money-deposited])

(evolve {:generations 500 :population-size 200 :max-height 5
	 :fitness fitness :termination termination
	 :functions [['and 2] ['or 2] ['not 1]] :terminals [true false]
	 :parameters parameters
	 :output (fn [g b _]
                   (println "Generation: " g)
                   (println "Fitness: "
                     (fitness (to-fn parameters b)))
                   (prn b))})