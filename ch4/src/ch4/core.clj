(ns ch4.core)

(defn titleize
  [topic]
  (str topic " for the Brave and True"))

(map titleize ["Hamsters" "Ragnarok"])
(map titleize #{"Bob" "Mary"})
(map titleize '("Albert" "Vegetables"))
(map #(titleize (second %)) {:uncomfortable-thing "Winking"})

(seq '(1 2 3))
(seq #{1 2 3})
(seq [1 2 3])
(seq (#(titleize (second %)) {:a :b :c :d}))
(seq {:name "Bill Compton" :occupation "Dead mopey guy"})

(into {} (seq {:a 1 :b 2 :c 3}))

(into {}  (cons [:d 4]  (seq {:a 1 :b 2 :c 3})))
(cons [:d 4] {:a 1 :b 2})
(assoc {:a 1} :b 2)
(merge {:a 1} {:b 2})

(map inc [1 2 3])
(map str ["a" "b" "c"] ["A" "B" "C"])

(def human-consumption [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data 
  [human critter]
  {:human human :critter critter})
(unify-diet-data 1 2)
(map unify-diet-data human-consumption critter-consumption)

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))
(stats [3 4 10])

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Tooth Fairy" :real "Your mom, again"}])

(map :real identities)

(reduce (fn [new-map [key val]] (assoc new-map key (inc val)))
        {} {:max 30 :min 10})

(reduce (fn [new-map [key val]] (if (> val 4) (assoc new-map key val) new-map)) {} {:human 4.1 :critter 3.9 :varmint 5.3})

(take  3 [1 2 3 4 5])
(drop 3 [ 1 2 3 4 5] )

(def food-journal 
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.3 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.3 :critter 3.9}
   ])

(take-while (fn [entry] (< (:month entry) 3)) food-journal)
(drop-while #(> (:human %) 4.5) food-journal)

(take-while #(< (:month %) 4) (drop-while #(< (:month %) 2) food-journal))

(filter #(< (:human %) 5.0) food-journal)
(some #(> (:critter %) 3) food-journal)

(some #(and (> (:critter %) 3) %) food-journal)


(sort [3 1 2])
(sort-by count ["aaa" "bb" "c"])
(sort-by #(reduce + %) [[1 2 333] [4 5 6] [78]])


(concat (take 8 (repeat "na")) ["Batman!"])
(take 3 (repeatedly (fn [] (rand-int 10))))

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq  (even-numbers (+ n 2))))))

(take 2 (even-numbers))

(cons 0 '(2 4 6))

(count [])
(empty? [1])
(every? #(= 1 %) [1 1 2] )

(map identity {:sunlight-reaction "Glitter!"})
(into {} (map identity {:sunlight-reaction "Glitter!"}))
(map identity [:garlic :sesame-oil :fried-eggs])
(into [] (map identity [:garlic :sesame-oil :fried-eggs]))

(into #{} (map identity [:garlic-clove :garlic-clove]))
(into {:favorite-emotion "gloomy"} [[:sunlight-reaction  "Glitter!"]])
(into ["cherry"] '("pine" "spruce"))

(into {:favorite-animal "kitty"} {:least-favorite-smell "dog" :relationship-with-teenager "creepy"})

(into [0] [1])
(conj [0] [1])
(conj [0] 1)
(conj [0] 1 2 3 )
(conj {:time "midnight"} [:place "ye old cemetarium"])
(into {:time "midnight"} [:place "ye old cemetarium"])

(defn my-conj
  [target & additions]
  (into target additions))

(my-conj {:time "midnight"} [:place "ye old cemetarium"])

(max 0 1 2)
(apply max [0 1 2])
(defn my-into
 [target additions]
 (apply conj target additions))

(my-into [0] [1])
(def add10 (partial + 10))
(add10 3)
(add10 27)

(def add-missing-elements
  (partial conj ["water" "air"]))
(add-missing-elements "asdf")

(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn (into args more-args))))

(def add20 (my-partial + 20))
(add20 3)

(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))

(def warn (partial lousy-logger :warn))

(warn "this house is on fire")

(def emergency (partial lousy-logger :emergency))
(emergency "I'm turning 300 years old")

(defn my-complement
  [fun]
  (fn [& args]
    (not (apply fun args))))

(def my-pos? (my-complement neg?))
(my-pos? 1)


