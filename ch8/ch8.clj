(defmacro parse-from-books
  [string]
  (-> (clojure.string/replace
       (clojure.string/replace string "_" " ")
       "“"
       "")
      read-string))

(macroexpand '(when true
                    expression-1
                    expression-2))


(defmacro infix
  [[operand1 op operand2]]
  (list op operand1 operand2))

(infix (1 + 2))

(and nil true)
   
(defmacro print-n-return
  [expression]
  (list 'let ['result expression]
        (list 'println 'result)
        'result))   

(macroexpand  '(print-n-return "a"))

