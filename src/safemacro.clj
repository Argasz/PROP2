(ns safemacro
  (:import (java.io Closeable File FileReader)
           (java.net Socket)))

(defmacro safe
  ([expr]
   `(try ~expr
         (catch Exception e#
           e#
           )))
  ([vec expr]
   `(try (let ~vec
           (try ~expr
                (catch Exception e#
                  (do
                    (if (instance? Closeable ~(nth vec 0))
                      (.close ~(nth vec 0)))
                    e#)))
           )
         (catch Exception e#
           e#)))
  )

(def b (safe (/ 1 0)))
(println (macroexpand '(safe [s (FileReader. (File. "file.txt"))] (.skip s 55))))

(def v (safe [s (FileReader. (File. "file.txt"))] (. s (skip -55))))
(println v)