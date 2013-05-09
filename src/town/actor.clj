(ns town.actor)

(def actors (atom []))

(defn make!
  "Creates a new actor with an initial state and a handler function.
  The handler function takes an actor and a message and must return an actor,
  either the same or a new one. For example:

    (make!
      {:foo :bar} ; initial state
      (fn [actor message]
        (println message)
        (assoc actor :state {:baz :quux}))) ; we return an extended actor

  The handler function may have side effects."
  [initial-state handler]
  (let [actor (agent {:state initial-state :handler handler})]
    (swap! actors conj actor)
    actor))

(defn send!
  "Asynchronously sends a message to a specific actor."
  [actor message]
  (let [handler (:handler @actor)]
    (send-off actor handler message)
    nil))

(defn spread! [message]
  "Asynchronously sends a message to a random actor."
  (send! (rand-nth @actors) message))