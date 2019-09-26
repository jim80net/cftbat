(ns fwpd.core
  (:gen-class))

(def filename "suspect.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity :glitter-index str->int})

(defn convert
   [vamp-key value]
   ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(def ex1
 (map #(:name %) (glitter-filter 5 (mapify (parse (slurp filename))))))

(def the_map
  (mapify (parse (slurp filename))))

(defn valid_name
  [name]
  (if (string? name) name ""))

(defn valid_glitter_index
  [index]
  (if (and (<= index 10) (>= index 0)) index 0))

(def validations
  {:name (partial valid_name) :glitter-index (partial valid_glitter_index)})

(defn validate
  [keyword value]
  ((get validations keyword) value)
)

(defn append
  [name glitter-index]
  (conj the_map {:name (validate :name  name) :glitter-index (validate :glitter-index glitter-index)}))

(defn values
  [map]
  (reduce (fn [vec item]
            (conj vec (second item)))
          []
          map)
)
(defn string_values
  [map]
  (clojure.string/join "," (values map))
  )
(defn list_to_string
  [list]
  (clojure.string/join "\n" list)
  )


(defn list_o_maps_to_csv
  ([] (list_o_maps_to_csv the_map))
  ([list_o_maps]
   (list_to_string (map #(string_values %) list_o_maps))))

(defn write
  ([] (write the_map))
  ([map] (spit filename (list_o_maps_to_csv map))))


