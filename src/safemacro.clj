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
                    e#))))
         (catch Exception e#
           e#)))
  )

(defn safeFn ([vec expr]
              (def ret `(try (let ~(vector (nth vec 0) (nth vec 1))
                               (try ~expr
                                    (catch Exception e#
                                      (do
                                        (if (instance? Closeable ~(nth vec 0))
                                          (.close ~(nth vec 0)))
                                        e#))
                                    (catch Exception e#
                                      e#)))))
              (eval ret))
  ([expr]
   (def ret2 `(try ~expr
        (catch Exception e#
          e#)))
    ret2))
