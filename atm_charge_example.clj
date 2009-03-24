(ns gp.problems.atm.charge (:use gp))

(defn fitness []
  )

(defn termination []
  )

(evolve {:generations 500 :population-size 200 :max-height 5
	 :fitness fitness :termination termination
	 :functions ['and 'or 'not]
	 :terminals [true false 
		     'statement-printed 
		     'money-withdrawn 
		     'money-deposited]
	 :paramaters ['statement-printed
		      'money-withdrawn
		      'money-deposited]
	 :output (fn [g b _] (println "Generation: " g) (prn b))})