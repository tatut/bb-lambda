(ns hello)

(defn check-name [event]
  (when-not (contains? event :name)
    (throw (ex-info "You must specify who to address"
                    {:missing-keys [:name]}))))

(defn hello [{:keys [name] :as event} context]
  (check-name event)
  {:greeting (str "Hello " name "!")})

(defn goodbye [{:keys [name day-phase] :as event} context]
  (check-name event)
  {:farewell (str "Goodbye " name
                  (when day-phase
                    (str ", have a pleasant " day-phase)))})
