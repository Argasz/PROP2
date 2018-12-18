(ns sqlmacro)



(defmacro select [cols _ table _ colarg _ colorder]
  `(map (fn[x#]
          (select-keys x# ~cols))
        (sort-by ~colorder (filter
                             (fn[y#]
                               (~(nth colarg 1) (get (select-keys y# [~(nth colarg 0)]) ~(nth colarg 0)) ~(nth colarg 2)))
                             ~table))))

(defn selectFn [cols _ table _ colarg _ colorder ]
  (map (fn[a]
         (select-keys a cols))
       (sort-by colorder (filter
                           (fn[b]
                             ((nth colarg 1) (get (select-keys b (list (nth colarg 0))) (nth colarg 0)) (nth colarg 2))) table))))

