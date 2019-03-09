(defproject test-compojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring-cors "0.1.13"]
                 [ring/ring-json "0.4.0"]
                 [yogthos/config "1.1.1"]
                 [hiccup "1.0.5"]]

  ; https://github.com/technomancy/leiningen/blob/stable/doc/PROFILES.md  
  ; https://github.com/technomancy/leiningen/tree/stable/lein-pprint
  ; Print a profile: lein with-profile [profile] pprint
  :plugins [[lein-ring "0.12.4"]
            [lein-uberwar "0.2.0"]
            [lein-pprint "1.2.0"]]

  ; When running locally in dev with ring, this specifies the main handler class
  ; and the listen port (3000)
  :ring {:handler test-compojure.handler/app
         :port 3000}

  ; https://github.com/luminus-framework/lein-uberwar
  :uberwar {:handler test-compojure.handler/app
            :url-pattern "/*"
            :name "test-compojure.war"}

  ; Includes the config files in the war
  ;:resources-path "config"

  ; Build the war for deployment with:
  ;  lein with-profile [dev | prod] uberwar
  ;
  ; The yothgos/config config.edn file is pulled from the config/[profile] folder
  ; and copied to the WEB-INF/classes folder so it can be loaded at run time.
  ; https://github.com/yogthos/config
  ;
  :profiles
  {:dev
   {:resource-paths ["config/dev"]
    :dependencies [[javax.servlet/servlet-api "2.5"]
                   [ring/ring-mock "0.3.2"]]}
   :prod
   {:resource-paths ["config/prod"]
    :dependencies [[javax.servlet/servlet-api "2.5"]
                   [ring/ring-mock "0.3.2"]]}})
