(ns sqlmacro)

(def persons '({:id 1 :name "olle"} {:id 2 :name "anna"} {:id 3 :name
                                                              "isak"} {:id 4 :name "beatrice"}))




(println persons)


(def idgreater (filter (fn[x]
          (> (get (select-keys x [:id]) :id) 2)
               ) persons))
(println idgreater)

(def sorted (sort-by :name idgreater))

(println sorted)

(def selected (map (fn[x]
                     (select-keys x [:name])) sorted))

(println selected)



(defmacro select [cols _ table _ colarg _ colorder]
  `(map (fn[x#]
          (select-keys x# ~cols))
        (sort-by ~colorder (filter
                             (fn[y#]
                               (~(nth colarg 1) (get (select-keys y# [~(nth colarg 0)]) ~(nth colarg 0)) ~(nth colarg 2)))
                             ~table))))

(println (macroexpand '(select [:id :name] from persons where [:id > 2] orderby :name)))

(def o (select [:id] from persons where [:id > 2] orderby :name))

(println o)

