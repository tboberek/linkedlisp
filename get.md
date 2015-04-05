[set](set.md) supports setting values in relation to other values. Depending on the "bean" (from Java parlance) type, different contexts are used:

To get the rdf:type of a resource, do:

```
(get eg:foo rdf:type)
```

If there are multiple values for the property, a list is returned. Otherwise, the single value is returned. If you always just want the first value, you can wrap the call in [first](first.md), which will return the value at hand if there's only one, or the first value from the list:

```
(first (get eg:foo rdf:type))
```


To get the values for all statements relating to eg:foo, do the following:

```
(get eg:foo)
```

This will return a list of triples (subject predicate object) where eg:foo is the subject. For more control over triple matching, see [find](find.md).

To get a value from a Java map:

```
(get m "key")
```

To get the value of a JavaBean property:

```
(get bean "propertyName")
```