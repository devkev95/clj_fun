(def project 'clj-fun)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[org.clojure/clojure "1.9.0"]
                            [ring "1.6.3"]
                            [compojure "1.6.0"]
                            [ring/ring-defaults "0.3.1"]
                            [ring/ring-json "0.4.0"]
                            [adzerk/boot-test "RELEASE" :scope "test"]])

(task-options!
 aot {:namespace   #{'clj-fun.core}}
 pom {:project     project
      :version     version
      :description "FIXME: write description"
      :url         "http://example/FIXME"
      :scm         {:url "https://github.com/yourname/clj-fun"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}}
 uber {:exclude #{#"(?i)^META-INF/[^/]*\.(MF|SF|RSA|DSA)$"
                  #"(?i)^META-INF\\[^/]*\.(MF|SF|RSA|DSA)$"
                  #"(?i)^META-INF/INDEX.LIST$"
                  #"(?i)^META-INF\\INDEX.LIST$"}}
 jar {:main        'clj-fun.core
      :file        (str "clj-fun-" version "-standalone.jar")})

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask run
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (require '[clj-fun.core :as app])
  (apply (resolve 'app/-main) args))

(require '[adzerk.boot-test :refer [test]])
