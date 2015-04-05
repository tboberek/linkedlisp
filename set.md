[set](set.md) supports setting values in relation to other values. Depending on the "bean" (from Java parlance) type, different contexts are used:

To set a variable in the environment (note, only 2 arguments here):

```
(set foo "bar")
```

To set the rdf:type of a resource, do:

```
(set eg:foo rdf:type eg:ExampleClass)
```

To set a value in a Java map:

```
(set m java:java.util.HashMap)
(set m "key" "value")
```

To set the value of a JavaBean property:

```
(set bean "propertyName" value)
```