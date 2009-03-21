(ns gp.problems.max (:use gp))

(evolve {:generations 500 :population-size 200 :max-height 4
	 :fitness apply :termination #(= (%) (Math/pow 4 (Math/pow 2 (- 4 3)))) 
	 :functions ['+ '*] :terminals [0.5]
	 :output (fn [g b _] (prn g b))})