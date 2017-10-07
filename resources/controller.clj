(ns {{project}}.controllers.{{table}}-controller
  (:require [coast.core :as coast]
            [{{project}}.models.{{table}} :as {{table}}]
            [{{project}}.views.{{table}} :as views.{{table}}])
  (:refer-clojure :exclude [update]))

(defn index [request]
  (let [{{table}} ({{table}}/all)]
    (coast/ok
      (views.{{table}}/index {{table}}))))

(defn show [request]
  (let [id (-> request :params :id)
        {{singular}} ({{table}}/find-by-id id)]
    (coast/ok
      (views.{{table}}/show {{singular}}))))

(defn new-form [request]
  (let [{:keys [params error]} request]
    (coast/ok
      (views.{{table}}/new-form {:{{singular}} params :error error}))))

(defn create [request]
  (let [params (get request :params)
        [{{singular}} error] (coast/try! ({{table}}/insert params))]
    (if (nil? error)
      (coast/redirect "/{{table}}")
      (new-form (assoc request :error error)))))

(defn edit-form [request]
  (let [{:keys [params error]} request]
    (coast/ok
      (views.{{table}}/edit-form {:{{singular}} params :error error}))))

(defn update [request]
  (let [params (get request :params)
        id (get params :id)
        [{{singular}} error] (coast/try! ({{table}}/update id params))]
    (if (nil? error)
      (coast/redirect "/{{table}}")
      (edit-form (assoc request :error error)))))

(defn delete [request]
  (let [id (-> request :params :id)
        [{{singular}} error] (coast/try! ({{table}}/delete id))]
    (coast/redirect "/" error)))