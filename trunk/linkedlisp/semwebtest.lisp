(progn 

    (defun printAll (x)
     (progn (write-line (concatenate (car (car x))
                                     ":\t\t"
                                     (cdr (car (car x)))
                        )
            )
            (if (cdr x) (printAll (cdr x)))
     )
    )

    ;; Biology example that dereferences and prints all information about
    ;; a gene.
    (prefix sc <http://bio2rdf.org/sciencecommons_ontology:>)
    (prefix geneid <http://bio2rdf.org/geneid:>)
    (load geneid:3098)
    (write-line (concatenate "geneid:3098 is " 
                             (get geneid:3098 sc:ggp_has_primary_symbol) 
                             ":"
                )
    )
    (printAll (get geneid:3098))
    
    ;; FOAF (Friend Of A Friend) example that dereferences and prints all
    ;; information about a person.
;;    (prefix foaf <http://xmlns.com/foaf/0.1/>)
;;    (prefix tw <http://tw.rpi.edu/wiki/Special:URIResolver/>)
;;    (load <http://tw.rpi.edu/wiki/Special:ExportRDF/James_McCusker>)
;;    (write-line (concatenate "Facts about " (get tw:James_McCusker foaf:name) ":"))
;;    (printAll (get tw:James_McCusker))
)