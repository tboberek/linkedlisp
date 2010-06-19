(progn 

    (defun label (x) (get rdfs:label x) )

    (bind <http://www.w3.org/2000/01/rdf-schema#label> 
;;          ( (noValue (x <http://www.w3.org/2000/01/rdf-schema#label>))) 
          ( (print (x))) 
          (lambda (x) 
            (concatenate x)
;;            (replace (replace (last (split x "[:#]")) 
;;                            "_" " " ) 
;;                   "(\\w)([A-Z][a-z0-9])" "\\1 \\2")
          )
    )

;;     (defun label (x) 
;;       (if (get rdfs:label x) 
;;           (get rdfs:label x) 
;;           (replace (replace (last (split x "[:#]")) 
;;                             "_" " " ) 
;;                    "(\\w)([A-Z][a-z0-9])" "\\1 \\2")
;;       )
;;     )

    (defun printAll (x)
     (progn (write-line (concatenate (label (last (get (car x) 1)))
                                     "    "
                                     (get (car x) 2)
                        )
            )
            (if (cdr x) (printAll (cdr x)))
     )
    )
    
    (write-line rdfs:label)

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
    (printAll (find geneid:3098 nil nil))
    
    ;; FOAF (Friend Of A Friend) example that dereferences and prints all
    ;; information about a person.
;;    (prefix foaf <http://xmlns.com/foaf/0.1/>)
;;    (prefix tw <http://tw.rpi.edu/wiki/Special:URIResolver/>)
;;    (load <http://tw.rpi.edu/wiki/Special:ExportRDF/James_McCusker>)
;;    (write-line (concatenate "Facts about " (get tw:James_McCusker foaf:name) ":"))
;;    (printAll (get tw:James_McCusker))
)