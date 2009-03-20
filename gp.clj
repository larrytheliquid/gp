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

(defn to-fn [tree]
  (eval `(fn [] ~tree)))