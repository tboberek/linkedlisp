(progn
 (prefix foaf <http://xmlns.com/foaf/0.1/>)
 (prefix eg <http://www.example.com#>)
 ;; Binding a function to a property based on the type of the entity.
 (bind foaf:name  
       ((person rdf:type foaf:Person) 
        (person eg:firstName fn) 
        (person eg:lastName ln))
       (lambda (person fn ln) (concatenate fn " " ln))
)
 (set eg:Jim eg:firstName "Jim")
 (set eg:Jim eg:lastName "McCusker")
 (set eg:Jim rdf:type foaf:Person)

 (write-line (concatenate (get eg:Jim eg:firstName) '" " 
                          (get eg:Jim eg:lastName)))
 (write-line (concatenate "The name is " (get eg:Jim foaf:name)))
)
