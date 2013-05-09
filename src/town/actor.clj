(ns town.actor)

(def actors (atom []))

(defn make! [state handler]
  (let [actor (agent {:state state :handler handler})]
    (swap! actors conj actor)
    actor))

(defn send! [receiver message]
  (let [handler (:handler @receiver)]
    (send-off receiver handler message)
    nil))

(defn spread! [message]
  (send! (rand-nth @actors) message))