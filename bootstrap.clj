;; AWS Lambda runtime using babashka.
;;
;;  The bootstrap shell script will run this
;;  and set the classpath to the $LAMBDA_TASK_ROOT.

(require '[org.httpkit.client :as http]
         '[clojure.string :as str]
         '[cheshire.core :as cheshire])

(def handler-name (System/getenv "_HANDLER"))
(println "Loading babashka lambda handler: " handler-name)

;; load handler
(def handler
  (let [[_ handler-ns handler-fn] (re-find #"(.*)\.(.*)$" handler-name)]
    (require (symbol handler-ns))
    (resolve (symbol handler-ns handler-fn))))

(def runtime-api-url (str "http://" (System/getenv "AWS_LAMBDA_RUNTIME_API") "/2018-06-01/runtime/"))

(defn next-invocation
  "Get the next invocation, returns payload and fn to respond."
  []
  (let [{:keys [headers body] :as invocation}
        @(http/get (str runtime-api-url "invocation/next")
                   {:as :text})
        id (get headers "Lambda-Runtime-Aws-Request-Id")]
    (println "GOT invocation " (pr-str invocation))
    [(cheshire/decode body)
     (fn [response]
       (http/post (str runtime-api-url "invocation/" id "/response")
                  {:body (cheshire/encode response)}))]))

(println "Starting babashka lambda event loop")
(loop [[payload send-response!] (next-invocation)]
  (try
    (let [response (handler payload nil)]  ; PENDING: pass in some context as well?
      (send-response! response))
    (catch Throwable t
      ;; PENDING: send some error response? otherwise it will timeout
      (println "Error in executing handler" t)))
  (recur (next-invocation)))
