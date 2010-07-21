(progn 
    (prefix foaf <http://xmlns.com/foaf/0.1/>)
    (prefix eg <http://www.example.com#>)
    (prefix tw <http://tw.rpi.edu/wiki/Special:URIResolver/>)

    ;; Binding based on available knowledge. Its not bound if the needed
    ;; triples are missing. Also, memoize the results into the default
    ;; knowledge base to avoid recomputation.
    (bind eg:name ((person foaf:name name))
          (lambda (person name) (concatenate "This is their name: " name))
    )
    (rule testrule ((person eg:test val)) "<-" ((person foaf:name val)))

    ;; FOAF (Friend Of A Friend) example that dereferences and prints all
    ;; information about a person.
    (load <http://tw.rpi.edu/wiki/Special:ExportRDF/James_McCusker>)
    (write-line (get tw:James_McCusker foaf:name))
    (write-line (get tw:James_McCusker eg:name))
    (write-line (get tw:James_McCusker eg:test))
)