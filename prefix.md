Add a namespace prefix to the current model. URIs can be shortened through use of namespace prefixes. For instance:

```
(prefix eg <http://www.example.com#>)
(write-line eg:Foo)
```

Will print out "http://www.example.com#Foo".