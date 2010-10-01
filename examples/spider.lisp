(progn 
  (write-line "Load GO annotations.")
  (load (get args 0))
  (write-line "Make HashSet.")
  (set subjects (new java:java.util.HashSet))

  ;; Collect the matching subjects into a set so that we don't load
  ;; them multiple times.
  (write-line "Collecting subjects.")
  (for stmt in (find nil <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://bio2rdf.org/ncbi#Gene>)
       (progn 
	 (append subjects (first stmt))
       )
  )

  (write-line (concatenate "Loaded " (length subjects) " subjects."))
  (write-line (first subjects))
  ;; Dereference each resource in x to get additional info.
  (for s in subjects
       (progn 
	 (write-line s)
	 (load s)
       )
  )
  (save (get args 1) "N-TRIPLE")
)