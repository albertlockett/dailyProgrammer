(ns clojure4.challenge226
  (require [clojure.string :as str]))

(def alphabet ["a" "b" "c" "d" "e" "f" "g" "h" "i" "j" "k" "l" "m" "n" "o" "p"
               "q" "r" "s" "t" "u" "v" "w" "x" "y" "z"])

(def isAlphabetical 
  (fn [str]  
    (def lasti 0)
    (def alphabetical true)
    (doseq [c (seq (str/split str #""))] 
      (do
        (def curi (.indexOf alphabet c))
        (if (< curi lasti) (def alphabetical false))
        (def lasti curi)
       )
     )
    (boolean alphabetical)
   )
 )


(doseq [c 
 ["billowy"
	"biopsy"
	"chinos"
	"defaced"
	"chintz"
	"sponged"
	"bijoux"
	"abhors"
	"fiddle"
	"begins"
	"chimps"
	"wronged"]] 
  (if (isAlphabetical (str/reverse c))
    (println c "REVERSE ORDER")
    (if (isAlphabetical c) 
      (println c "IN ORDER") 
      (println c "NOT IN ORDER"))))