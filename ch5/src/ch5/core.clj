
(ns ch5.core
  (:gen-class))

(defn wisdom
  [words]
  (str words ", Daniel-san"))

(defn year-end-evaluation
  []
  (if (> (rand) 0.5)
    "You get a raise!"
    "Better luck next year!"))


(defn analysis
  [text]
  (str "Character count: " (count text)))

(defn analyze-file
  [filename]
  (analysis (slurp filename)))

(def great-baby-name "Rosanthony")


(let [great-baby-name "Bloodthunder"]
  great-baby-name)

(defn sum
  ([vals] (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total 
     (recur (rest vals) (+ (first vals) accumulating-total)))))

(require '[clojure.string :as s])
(defn clean
  [text]
  (s/replace (s/trim text) #"lol" "LOL"))


(def character 
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})

(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))
(c-int character)

(defn spell-slots
  [char]
  (int (inc (/ (c-int char) 2))))
(spell-slots character)

(def spell-slots-comp
  (comp int inc #(/ % 2 ) c-int))
(spell-slots-comp character)

(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

(def spell-slots-comp
  (two-comp #(/ % 2 ) c-int))
(spell-slots-comp character)

(defn sum
  ([vals] (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total 
     (recur (rest vals) (+ (first vals) accumulating-total)))))

(defn my-comp
  "my comp function.
   TODO: If I replace recur with my-comp, this results in a StackOverflowError. Why?"
  [f & remaining-fn]
  (if (empty? remaining-fn)
    f
    (recur (fn 
               [& args]
               (f (apply (first remaining-fn) args)))
             (rest remaining-fn))))

(def spell-slots-comp
  (my-comp int inc #(/ % 2 ) c-int))
(spell-slots-comp character)

(defn sleepy-identity
  "Returns the given value after 1 second"
  [x]
  (Thread/sleep 1000)
  x)
 
(sleepy-identity "Mr. Fantastico") 

(def memo-sleepy-identity (memoize sleepy-identity))
(memo-sleepy-identity "Flash Gordon")
