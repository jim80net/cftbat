;; This buffer is for Clojure experiments and evaluation.

;; Press C-j to evaluate the last expression.

;; You can also press C-u C-j to evaluate the expression and pretty-print its result.

(def addition-list (list + 1 2))

(eval addition-list)

(eval (concat addition-list [10]))

(eval (list 'def 'lucky-number (concat addition-list [10])))
#'user/lucky-number
lucky-number
13

(inc 'addition-list)
addition-list
(#function[clojure.core/+] 1 2)

(eval (read-string "(+ 1 2)"))
(read-string "#(+ 1 %)")
(fn* [p1__5881#] (+ 1 p1__5881#))
(read-string "'(1)")
(quote (1))
(read-string "@abc")
(clojure.core/deref abc)
(if true :a :b)
:a
if
(let [x 2] (+ x 3))
5
(def x 15)
#'user/x
(let [x 2] (+ x 3))
5
(+ x 3)
18
(defn exclaim [x] (str x "!"))
#'user/exclaim
(exclaim "hello world")
"hello world!"
(map inc [1 2 3])
(2 3 4)

(defn infix
  [infix]
  (eval (list (second infix) (first infix) (last infix))))
#'user/infix

#'user/infix

(+ 1 1)

(infix '(1 + 1))
2

(+ 1 1)

(defmacro ignore-last-operand
  [function-call]
  (butlast function-call))
#'user/ignore-last-operand
(macroexpand  '(ignore-last-operand (+ 1 2 10)))
(+ 1 2)
(+ 1 2 3)
6


(defmacro infix
  [input]
  (list (second input) (first input) (last input)))
#'user/infix
(infix (1 + 1))
2


(defn parse_from_books
[string]
  (read-string (clojure.string/replace string " " " ")))

(parse_from_books "(defn read-resource
                         \"Read a resource into a string\"
                         [path]
                         (read-string (slurp (clojure.java.io/resource path))))")


(defn parse_from_books
  [string]
  (-> (clojure.string/replace string "_" " ")
      read-string))







