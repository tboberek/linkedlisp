(progn
(write-line (call java:java.net.URLEncoder encode "test" "UTF8"))
(write-line (call (new java:java.lang.String "TestString") toLowerCase))
)