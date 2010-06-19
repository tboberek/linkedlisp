;; Linked LISP: A language for linking data over the web.
;; Everything is a literal, URI, variable, or List.
;; RDF is a sophisticated entity-attribute-value-based data
;; representation system. RDFS and OWL are ways of describing
;; data that is encoded in RDF. RDFS is simple and fast, and OWL
;; is computationally and semantically valid and sound.

;; Some example URI schemes:
;; java:// URIs refer to java classes in the classpath.
;; prop:// URIs refer to javabean properties.
;; call:// URIs refer to methods on classes.
;; jdbc:// URIs refer to database connections.
;; http:// URIs can dereference RDF and RDFa data if available using (get).

;; Default prefixes (these can be added to):
(prefix
 ((prop <prop://>)
  (call <call://>)
  (java <java://>)
  (owl <http://www.w3.org/2002/07/owl#>)
  (rdfs <http://www.w3.org/2000/01/rdf-schema#>)
  (xsd <http://www.w3.org/2001/XMLSchema#>)
  (rdf <http://www.w3.org/1999/02/22-rdf-syntax-ns#>))
)

;; A typed literal
("2010-05-25T21:42:30+00:00" <http://www.w3.org/2001/XMLSchema#dateTime>)

;; A prefixed typed literal
("2010-05-25T21:42:30+00:00" xsd:dateTime)

;; Another prefixed typed literal
("12345"^^xsd:integer)

;; Biology example that dereferences and prints all information about
;; a gene.
(prefix  
 ((sc <http://bio2rdf.org/ns/sciencecommons:>)
  (geneid <http://bio2rdf.org/page/geneid:>))
 (get geneid:3098)
 (print "statements in " (geneid:3098 sc:ggp_has_primary_symbol) ":\n")
 (loop for p o in (geneid:3098 p o) do (print p " = " o))
)

;; FOAF (Friend Of A Friend) example that dereferences and prints all
;; information about a person.
(prefix 
 ((foaf <http://xmlns.com/foaf/0.1/>)
  (tw <http://tw.rpi.edu/wiki/Special:ExportRDF/>))
 (get tw:James_McCusker)
 (print "Facts about " (tw:James_McCusker foaf:name) ":\n")
 (loop for p o in (tw:James_McCusker p o) do (print p " = " o))
)

;; Example function that uses Java classes to print out the contents
;; of a CSV File. Note the form for calling object's methods are like
;; dereferencing statements about a URI.
(defun printCSV (filename)
 (prefix
  ((opencsv <java://au.com.bytecode.opencsv.>) 
   (io <java://java.io.>))
  (let* ((file (new io:File filename))
	 (reader (new opencsv:CSVReader (new io:FileReader file) ",")))
    (loop for line from (reader call:nextLine) while (! line null) 
	  do ((loop for val in line) 
	      do (print val "\t")
	      (print "\n")
)))))

;; Binding a function to a property based on the type of the entity.
(bind foaf:name  
      ((person rdf:type foaf:Person) (person eg:firstName fn) (person eg:lastName ln))
      (lambda (person fn ln) (concatenate fn " " ln))
)

;; Binding based on available knowledge. Its not bound if the needed
;; triples are missing. Also, memoize the results into the default
;; knowledge base to avoid recomputation.
(prefix
 ((foaf <http://xmlns.com/foaf/0.1/>)
  (eg <http://www.example.com#>)
  (rdf <http://www.w3.org/1999/02/22-rdf-syntax-ns#>))
 (defun foaf:name (person) 
   (when (and (person eg:firstName) (person eg:lastName))
   (memoize (concatenate (person eg:firstName) " " (person eg:lastName))))
 )
)

;; A convenience function to retrieve data from a SPARQL endpoint:
(defun getSparql (endpoint query)
  (get (concatenate endpoint (urlEncode query)))
)

;; A convenience function to URL-encode text, which uses a static
;; method.
(defun urlEncode (s)
 (java:java.net.URLEncoder call:encode s "UTF8")
)


