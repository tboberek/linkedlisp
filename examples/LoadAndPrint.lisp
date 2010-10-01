(progn
  (defun loadAndPrint (uri)
    (progn
      (write-line uri)
      (load uri)
      (for statement in (find uri nil nil)
        (write-line (concatenate (get statement 1) "\t" (last statement)) )
      )
    )
  )
  (loadAndPrint (first args))
)
