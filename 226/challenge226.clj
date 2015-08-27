(ns clojure4.challenge226
  (require [clojure.string :as str]))

(defn numerator [fraction] 
  (Long. (get (str/split fraction #"/") 0)))

(defn denom [fraction]
  (Long. (get (str/split fraction #"/") 1)))

(defn addFractions [ & fracs] 
  (def highestDenom (apply * (map denom fracs)))
  (def addedNum (apply + (map 
    (fn [x] (*(numerator x) ( / highestDenom (denom x)))) fracs)))
  (str addedNum "/" highestDenom))

(defn reduceFraction [frac]
  (def lrgFactor 1)
  (def lrgFactorFound false)
  (doall (map 
    (fn [x] 
      (do (if (and 
                (not lrgFactorFound) 
                (= (mod (numerator frac) x) 0)
                (= (mod (denom frac) x) 0)) 
            (do
              (def lrgFactor x)
              (def lrgFactorFound true)
           ))))
    (seq (range (denom frac) 1 -1))))
 (def reducedFraction 
   (str (/ (numerator frac) lrgFactor) "/" (/ (denom frac) lrgFactor)))
 (if lrgFactorFound (reduceFraction reducedFraction) reducedFraction)
)


(reduceFraction (addFractions "2/9" "4/35" "7/34" "1/2" "16/33"))

(reduceFraction (addFractions "1/7" "35/192" "61/124" "90/31" "5/168" "31/51" 
                              "69/179" "32/5" "15/188" "10/17"))