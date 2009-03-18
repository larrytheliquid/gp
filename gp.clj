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

(defn to-sexp [tree]
  (if-let [children (:children tree)]
    (apply list (:value tree)
	   (map #(to-sexp %) children))
    (:value tree)))

(defn to-fn [tree]
  (eval `(fn [] ~(to-sexp tree))))