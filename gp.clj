(ns gp)
;;; TODO: Height limit for crossover

(defn- rand-elem [coll]
  (nth coll (rand-int (count coll))))

(defstruct program :value :children)
(defn p [value & children]
  (struct program value children))

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

;; (defn replace-node
;;   ([n replacement tree] (replace-node n 0 replacement tree))
;;   ([target index replacement tree]
;;      (if (= target index)
;;        replacement
;;        (if-let [children (:children tree)]
;; 	 (apply p (:value tree)
;; 		(map #(replace-node 
;; 		       target (inc index)
;; 		       replacement %)
;; 		     children))
;; 	 tree))))

(defn nth-node
  ([n tree] (nth-node n 0 tree))
  ([target index tree]
     (if (= target index) 
       tree
       (if-let [children (:children tree)] 
	 (reduce #(if (integer? %1)
		    (nth-node target (inc %1) %2)
		     %1)
		 index children)
	 index))))

;; (defn replace-node
;;   ([n replacement tree] (first (replace-node n 0 replacement tree)))
;;   ([target index replacement tree]
;;      (if (= target index)
;;        [replacement index]
;;        (if-let [children (:children tree)]
;; 	 (apply p (:value tree)
;; 	   (first (reduce
;; 	     (fn [[acc i] child]
;; 	       (conj acc (replace-node target (inc i) replacement child)))
;; 	     [(vector) index] children)))
;; 	 [tree index]))))

;;; TODO: rename to set-node
(defn replace-node
  ([n replacement tree] (replace-node n 0 replacement tree))
  ([target index replacement tree]
     (if (= target index)
       replacement
       (if-let [children (:children tree)]
	 (apply p (:value tree)
	   (first (reduce
	     (fn [[acc i] child]
	       [(conj acc (replace-node target (inc i) replacement child))
		(inc i)])
	     [(vector) index] children)))
	 tree))))

(defn to-sexp [tree]
  (if-let [children (:children tree)]
    (apply list (:value tree)
	   (map #(to-sexp %) children))
    (:value tree)))

(defn to-fn [tree]
  (eval `(fn [] ~(to-sexp tree))))