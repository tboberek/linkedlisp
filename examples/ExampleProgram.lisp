(progn
 (setf i 1)
 (loop for item in (QUOTE ("This" "Is" "A" "Test"))
 	do (setf i (+ i 1))
	   (if (= 0.0 (% i 2))
		(write-line item)))
 (loop for x from 0 to 10 
  do (write-line x))
)
