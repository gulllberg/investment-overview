(ns investment-overview.core
  (:require [ysera.test :refer [is= is is-not]]))

;; TODO Load events from file and sort in order by date
(def events [{:date 20171212 :ticker "Hej" :type :buy :amount 100 :price 50}
             {:date 20171213 :ticker "Hej" :type :sell :amount 50 :price 60}
             {:date 20171214 :ticker "Hej" :type :split :parts 5}
             {:date 20171215 :ticker "Hej" :type :dividend :amount 100}])

;; TODO Needs to be called with events for only one ticker,
;; so filter by ticker and date beforehand
(defn calculate-amount
  {:test (fn []
           (is= (calculate-amount events) 250))}
  [events]
  (reduce (fn [amount event]
            (condp = (:type event)
              :buy (+ amount (:amount event))
              :sell (- amount (:amount event))
              :split (* amount (:parts event))
              amount))
          0
          events))

;; Dividend needs to be per share? Talar för att bygga upp state steg för steg?
(defn calculate-value
  {:test (fn []
           (is= (calculate-value events 1) 350))}
  [events current-price]
  (+ (reduce (fn [dividend event]
               (if (= (:type event) :dividend)
                 (+ dividend (:amount event))
                 dividend))
             0
             events)
     (* (calculate-amount events) current-price)))

(defn calculate-gav
  [events]
  )

;; Alternative strategy: step through the events and build up a state as you go
;; At the end check with current prices
;; Finding utveckling for one stock is easy, getting a fair utveckling
;; for entire portfolio is more difficult

;; Ska allting alltid vara per aktie?
