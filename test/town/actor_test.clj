(ns town.actor-test
  (:use clojure.test
        town.actor))

(defn before-each [test]
  (reset! actors []) ;reset actors before each test run
  (test))

(use-fixtures :each before-each)

(deftest make-test
  (testing "Creates an actor"
    (let [actor (make! {:type :a} #())]
      (is (= @actors [actor])))))

(deftest send-test
  (testing "Sends a message to a specific actor"
    (let [world (atom {})
          actor (make! {:type :a} (fn [receiver new-type]
                                    (assoc receiver :state {:type new-type})))]
      (send! actor :b)
      (await actor)
      (is (= :b (:type (:state @actor)))))))
