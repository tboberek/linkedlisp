(progn
	(set testBean (new java:com.googlecode.linkedlisp.TestBean))
	(set testBean "firstName" "Joe")
	(set testBean "lastName" "Smith")
	(write-line (get testBean "name"))
)