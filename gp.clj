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