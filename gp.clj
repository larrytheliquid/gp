(ns gp (:require [clojure.zip :as zip]))

(defn- rand-elem [coll]
  (nth coll (rand-int (count coll))))

(defn initialize [functions terminals height]
  (if (= 0 height)
    (rand-elem terminals)
    (list (rand-elem functions) 
       (initialize functions terminals (dec height))
       (initialize functions terminals (dec height)))))

(defn count-nodes [tree]
  (if-let [children (and (list? tree) (next tree))]
    (apply + 1
	   (map #(count-nodes %) children))
    1))

(defn height [tree]
  (if-let [children (and (list? tree) (next tree))]
    (apply max 
	   (map #(inc (height %)) children))
    0))

(defn get-node
  ([n tree] (get-node n 0 tree))
  ([target index tree]
     (if (= target index) 
       tree
       (if-let [children (and (list? tree) (next tree))] 
	 (reduce #(if (integer? %1)
		    (get-node target (inc %1) %2)
		     %1)
		 index children)
	 index))))

;; (defn get-node [n tree]
;;   (loop [distance n
;; 	 zipper (zip/seq-zip tree)]
;;     (if (= 0 distance)
;;       (zip/node zipper)
;;       (recur (if (zip/branch? zipper)
;; 	       (dec distance)
;; 	       distance)
;; 	     (zip/next zipper)))))

(defn set-node
  ([n replacement tree] (set-node n 0 replacement tree))
  ([target index replacement tree]
     (if (= target index)
       replacement
       (if-let [children (and (list? tree) (next tree))]
	 (apply list (first tree)
		(map #(set-node 
		       target (inc index)
		       replacement %)
		     children))
	 tree))))

(defn to-fn [tree]
  (eval `(fn [] ~tree)))