(ns gp)

(defn- rand-elem [coll]
  (nth coll (rand-int (count coll))))

(defn p [value & children]
  {:value value :children children})

(defn initialize [functions terminals height]
  (if (= 0 height)
    (p (rand-elem terminals))
    (p (rand-elem functions) 
       (initialize functions terminals (dec height))
       (initialize functions terminals (dec height)))))

(defn count-nodes [tree]
  (if-let [children (:children tree)]
    (apply + 1
	   (map #(count-nodes %) children))
    1))

(defn height [tree]
  (if-let [children (:children tree)]
    (apply max 
	   (map #(inc (height %)) children))
    0))

(defn nth-node
  ([n tree] (nth-node n 0 tree))
  ([target index tree]
     (if (= target index) tree
       (if-let [children (:children tree)] 
	 (reduce #(if (integer? %1)
		    (nth-node target (inc %1) %2)
		     %1)
		 index children)
	 index))))

(defn to-sexp [tree]
  (if-let [children (:children tree)]
    (apply list (:value tree)
	   (map #(to-sexp %) children))
    (:value tree)))

(defn to-fn [tree]
  (eval `(fn [] ~(to-sexp tree))))