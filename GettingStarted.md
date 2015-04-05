# Getting Linked LISP #

Download Linked LISP as a binary (no need to compile) or as a source package from http://code.google.com/p/linkedlisp/downloads/list or get the development branch from subversion:

```
svn checkout http://linkedlisp.googlecode.com/svn/trunk/linkedlisp linkedlisp
```

Building Linked LISP requires Maven 2 and >= JDK 6. In the linkedlisp folder, run "./buildall.sh". You can now use the script "linkedlisp" to execute Linked LISP scripts. There are many examples in examples/.

If you've downloaded the binary package, just unarchive it and continue below. If you're on Windows, the easiest way to work is through cygwin, as there aren't .bat files yet.

# Introduction #

Linked LISP is LISP + [Linked Data](http://linkeddata.org) + Java. That was easy. But what do we mean by that? Linked LISP is a LISP implementation that has a "baked-in" understanding of the Semantic Web and Linked Data. At its core is the Java library for the semantic web, [Jena](http://jena.sourceforge.net), and allows users to collect and manipulate data that is published to the Semantic Web.

In basic terms, everything in the semantic web is expressed as a three-tuple, or triple (sometimes it's a quad, but that's not important here). A triple is composed of a subject, predicate, and object. Because of this, you can think of these triples as sentences. To state one of these sentences, for instance, to say "the foaf:name of tw:Jim\_McCusker is 'Jim McCusker'", you use the [set](set.md) command:

```
(set tw:Jim_McCusker foaf:name "Jim McCusker")
```

Pretty simple. On the flip side, to find out the foaf:name of tw:Jim\_McCusker, use [get](get.md):

```
(get tw:Jim_McCusker foaf:name)
```

If the [set](set.md) command from above has been called, then the return value will be "Jim McCusker".

# Example #

Let's put a slightly more sophisticated example together and then take it apart to understand what's happening. We would like to be able to load a URI from the web and then print out everything that we've retrieved from it. An example function for this would look like:

```
(defun loadAndPrint (uri)
  (progn
    (load uri)
    (for statement in (find uri nil nil)
      (write-line (concatenate (get statement 1) "\t" (last statement)))
    )
  )
)
```

If we've [defun](defun.md)ed this function, then we can then call it this way:

```
(progn
  (prefix tw <http://tw.rpi.edu/wiki/Special:URIResolver/>)
  (loadAndPrint tw:James_McCusker)
)
```

Let's look at the call first, then we can unpack what the function is doing. [progn](progn.md) is the function that calls each of its arguments in turn, and returns the value of the last call. The return value isn't important here, but is good to know. [progn](progn.md) is simply used here to let us set up a [prefix](prefix.md) for our URIs. Rather than having to print <http://tw.rpi.edu/wiki/Special:URIResolver/James_McCusker> every time we want to refer to it, we can simply refer to it as tw:James\_McCusker. It simply replaces the prefix plus colon with the URI it refers to, and tacks on everything after the colon. The rest of this call is straightforward LISP: tw:James\_McCusker is the first and only argument to the function loadAndPrint, which we defined above.

Looking back at the function we defined, we used [defun](defun.md) to create it. It takes the function name, the list of parameter names, and what to execute. We again use [progn](progn.md) to let us perform multiple execution steps.

The first step that is performed is ([load](load.md) tw:James\_McCusker). This function dereferences the URI and loads any RDF available in it into the current model. The model is where we store our statements, and serves like a database that can be queried and modified. As we discussed above, when we have a URI as the first argument, [get](get.md) retrieves statements or values from the model, and [set](set.md) asserts those statements within the model. Another function we use in loadAndPrint is [find](find.md), which always queries against the model, but allows a pattern to be defined, where any non-nil value in the pattern must match in the statements that are returned. Here, we look for any statements where the URI that is passed to the 'uri' parameter is a subject. This is effectively a way to describe that URI, as any statements that have a given URI as its subject can be said to be 'about' that URI.

The [for](for.md) function is used to iterate over the results returned from our [find](find.md) call, and we call [write-line](write_line.md) each time, which prints its first argument to stdout. To build up the output string we use [concatenate](concatenate.md), which converts each of its arguments to strings and joins them together. We use [get](get.md) in a different context here, using it to get the value from the list 'statement' from position 1. This is the predicate, as the return from [find](find.md) is always a list of lists that look like {subject predicate object}. The [last](last.md) function is used to retrieve the object from the statement list.

To put this function together into a full runnable program, you can do something like this:

```
(progn
  (defun loadAndPrint (uri)
    (progn
      (load uri)
      (for statement in (find uri nil nil)
        (write-line (concatenate (get statement 1) "\t" (last statement)))
      )
    )
  )
  (loadAndPrint (first args))
)
```

If you save this into a file called 'describe.lisp', you can then call it like this:

```
./linkedlisp describe.lisp '<http://tw.rpi.edu/wiki/Special:URIResolver/James_McCusker>'
```

Notice we've snuck in a few new concepts to this script. 'args' is a system variable that contains the arguments passed to the command line. The function [first](first.md) is symmetric to [last](last.md), and gets the first value from a list. Congratulations, you've run your first Linked LISP script.