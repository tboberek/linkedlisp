Returns a list of triples from the current model that match the pattern in the argument:

```
(find subject predicate object)
```

Any of subject, predicate, or object can be nil, allowing for complete matches. For instance,

```
(find nil nil nil)
```

will return all triples in the model. To find all triples for a particular subject, try:

```
(find eg:subject nil nil)
```

To return all triples about eg:subject.