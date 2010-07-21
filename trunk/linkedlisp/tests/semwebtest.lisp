(progn 

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
    (for stmt in (find geneid:3098 nil nil)
	 (write-line (concatenate (get stmt 1) "\t" (last stmt)))
    )
)