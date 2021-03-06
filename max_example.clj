(ns gp.problems.max (:use gp))

(evolve {:generations 500 :population-size 200 :max-height 5
	 :fitness apply :termination #(= (%) (Math/pow 4 (Math/pow 2 (- 5 3)))) 
	 :functions [['+ 2] ['* 2]] :parameters [] :terminals [0.5]
	 :output (fn [g b _] 
		   (println "Generation: " g) 
		   (println "Fitness: " ((to-fn [] b))) 
		   (prn b))})