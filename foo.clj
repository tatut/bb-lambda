(ns foo)

(defn bar [event ctx]
  (if (:virhe event)
    (throw (RuntimeException. "ei onnistu"))
    (merge event
           {:success true
            :jotain "ihan muuta"})))
