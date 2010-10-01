(progn
  (prefix eg <http://www.example.com#>)
  (for i in '(a b c d e) (set eg:Foo eg:has_bar i))
  (for i in (find eg:Foo eg:has_bar nil) (write-line (last i)))
)