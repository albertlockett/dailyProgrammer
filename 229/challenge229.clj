(ns clojure4.challenge229)

(loop [cnt 100 dottie 0]
  (if (= cnt 0)
    dottie
    (recur (dec cnt) (Math/cos dottie))))

(loop [old 0 new 1]
  (if(= old new)
    new
    (recur new (Math/cos new))))