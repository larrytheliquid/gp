(ns gp (:require [clojure.zip :as zip]))

(defn- rand-elem [coll]
  (nth coll (rand-int (count coll))))

(defn- replicate-fn [n f]
  (take n (repeatedly f)))

(defn initialize [functions terminals height]
  (if (= 0 height)
    (rand-elem terminals)
    (list (rand-elem functions) 
       (initialize functions terminals (dec height))
       (initialize functions terminals (dec height)))))

(defn count-nodes [tree]
  (if-let [children (and (seq? tree) (next tree))]
    (apply + 1
	   (map #(count-nodes %) children))
    1))

(defn height [tree]
  (if-let [children (and (seq? tree) (next tree))]
    (apply max 
	   (map #(inc (height %)) children))
    0))

(defn- nth-zipper [n tree f]
  (loop [distance n
	 zipper (zip/seq-zip tree)]
    (if (= 0 distance)
      (f zipper)
      (recur (if (zip/branch? zipper) 
	       distance
	       (dec distance))
	     (zip/next zipper)))))

(defn get-node [n tree]
  (nth-zipper n tree zip/node))

(defn set-node [n replacement tree]
  (nth-zipper n tree 
    #(zip/root (zip/replace % replacement))))

(defn crossover [max-height male female]
  (let [child
	(set-node 
	 (rand-int (count-nodes male))
	 (get-node (rand-int (count-nodes female))
		   female)
	 male)]
    (if (> (height child) max-height)
      male
      child)))

(defn to-fn [args tree] 
  (eval `(memoize (fn [~@args] ~tree))))
(def to-fn (memoize to-fn))

(defn- fitter [fitness parameters x y]
  (if (> (fitness (to-fn parameters x)) 
	 (fitness (to-fn parameters y))) 
    x y))

(defn select [fitness parameters individuals]
  (let [x (rand-elem individuals)
	y (rand-elem individuals)]
    (fitter fitness parameters x y)))

(defn fittest [fitness parameters individuals]
  (reduce #(fitter fitness parameters %1 %2) 
	  individuals))

(defn evolve [{:keys [generations population-size max-height
		      fitness termination
		      functions parameters terminals] :as options}]
  (loop [generation 0 
	 population (replicate-fn population-size
                      #(initialize functions 
		         (concat terminals parameters) max-height))
	 best (fittest fitness parameters population)]
    (when-let [o (:output options)] (o generation best population))
    (if (or (termination (to-fn parameters best)) 
	    (= generation generations))
      (do (shutdown-agents) best)
      (recur (inc generation)	     
	     (pmap (fn [_] (crossover max-height
		             (select fitness parameters population)
			     (select fitness parameters population)))
		   population)
	     (fittest fitness parameters (conj population best))))))