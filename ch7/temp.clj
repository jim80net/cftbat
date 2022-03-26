;; This buffer is for Clojure experiments and evaluation.

;; Press C-j to evaluate the last expression.

;; You can also press C-u C-j to evaluate the expression and pretty-print its result.

(defn entries
  [file]
  (vec (map clojure.string/trim (clojure.string/split file #"\# at \d+\n.*"))))

(defn lines
  [file]
  (vec (map #(clojure.string/split % #"\n") (entries file))))


(defn statements
  [line]
  (let [my-statements (rest line)]
    (if (empty? my-statements)
      '("")
      my-statements)))

(defn command
  [line]
  (clojure.string/upper-case (first (clojure.string/split line #" "))))


(defn commands
  [line]
  (remove #(or (= "SET" %) (= "USE") (re-find #"\/\*\!" %)) (map command (statements line))))

(defn metadata
  [line]
  (or (first line) ""))

(defn exec_time
  [line]
  (Long/parseLong
   (let
    [matches (last (re-find #"exec_time=(\d+)" (metadata line)))]
     (if matches matches "0"))))

(defn time
  [line]
  (let [timestring (or
                    (re-find #"\d{6} \d\d:\d\d:\d\d" (metadata line))
                    "200101 00:00:00")]
    (.parse (java.text.SimpleDateFormat. "yyMMdd hh:mm") timestring)))

(def lines-by-frequencies
  (frequencies
   (map (fn [line]
          (vec [(time line) (commands line)]))
        lines)))

(defn time-command-cmp
  [a b]
  (let [get-time (fn [n] (.getTime (first (first n))))
        get-command (fn [n] (first (second (first n))))]
    (let [c  (compare (get-time a) (get-time b))]
      (if (not= c 0)
        c
        (let [c (compare (get-command a) (get-command b))]
          c)))))

(def sorted-lines-by-frequencies
  (sort time-command-cmp lines-by-frequencies))

(defn simple-date
  [date]
  (.format (java.text.SimpleDateFormat. "HH:mm") date))

(def simple-freq-chart
  (map
   (fn [freq]
     (vec [(simple-date (first (first freq)))
           (second (first freq))
           (second freq)])) sorted-lines-by-frequencies))

(for [command-time simple-freq-chart]
  (println command-time))

(defn restream-seq
  [^java.util.regex.Pattern re ^java.io.BufferedReader rdr]
  (let [s (java.util.Scanner. rdr)]
    ((fn step []
       (if-let [token (.findInLine s re)]
         (cons token (lazy-seq (step)))
         (when (.hasNextLine s) (.nextLine s) (step)))))))

(with-open [rdr (clojure.java.io/reader "/Users/jimp/workspace/github.com/jim80net/cftbat/ch7/mysql-binlog.005144")]
  (doall

   (re-seq  #"\# at \d+\n" rdr)))

(defn lazy-open
  [file]
  (defn helper
    [rdr]
    (lazy-seq
     (if-let [line (.readLine rdr)]
       (cons line (helper rdr))
       (do (.close rdr) nil))))
  (lazy-seq
   (helper (clojure.java.io/reader file))))

(let [stream (lazy-open "/Users/jimp/workspace/github.com/jim80net/cftbat/ch7/mysql-binlog.005144")]
  (first (re-seq #"\# at \d+\n" stream)))

