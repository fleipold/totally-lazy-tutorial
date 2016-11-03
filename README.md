

This is a starting point for a tutorial for [TotallyLazy](https://totallylazy.com/) which has a all the things that we
are missing in the Java 8 class library.
<p>
<p>
Sequence
========
<p>
The basic abstraction of TotallyLazy is a `Sequence`. A sequence is a bit like
an `Iterable` with a lot of useful methods. Wherever possible these methods are
lazily evaluated, e.g. `map`, `filter`, while some methods like `fold` force
the evaluation the chain so far.
<p>
The typical way to construct a `Sequence` is to use one of the static `sequence` factory methods.

~~~ .java
Sequence<Integer> fromVarargs = sequence(1, 2, 3);

Sequence<Integer> fromArray = sequence(new Integer[]{1, 2, 3});

Sequence<Integer> fromIterable = sequence(asList(1, 2, 3));

Sequence<Integer> fromRange = range(1, 3);

assertEquals(fromVarargs, fromRange);
~~~

Mapping
-------
<p>
The `map` method applies a function to every element of the
sequence and returns a sequence of these results:

~~~ .java
Sequence<Integer> originalSequence = sequence(1, 2, 3);
Sequence<Integer> squares = originalSequence.map(x -> x * x);

assertEquals(sequence(1, 4, 9), squares);
~~~

The `map` method can apply a function that has a different return
type than the original element type:

~~~ .java
Sequence<Integer> originalSequence = sequence(1, 2, 3);
Sequence<String> stringSequence = originalSequence.map(x -> String.format("%d", x));

assertEquals(sequence("1", "2", "3"), stringSequence);
~~~

Accessing Elements and Subranges
--------------------------------
<p>
There are a couple of ways to access elements of a sequence.
Note that an index lookup can be expensive (rather than iterating over indexes
consider map or fold operations):

~~~ .java
Sequence<String> strings = sequence("1", "2", "3", "4");

assertEquals("1", strings.first());

assertEquals("2", strings.second());

assertEquals("4", strings.last());

assertEquals("3", strings.get(2));
~~~

There are a number of ways to get subranges of Sequences.
It is important to know that Sequences are immutable, i.e.
all these operations return new objects.

Here some examples:

~~~ .java
Sequence<String> strings = sequence("1", "2", "3", "A", "B");

assertEquals(sequence("2", "3", "A", "B"), strings.tail());

assertEquals(sequence("1", "2"), strings.take(2));

assertEquals(sequence("1", "2", "3"), strings.takeWhile(s -> Character.isDigit(s.charAt(0))));

assertEquals(sequence("3", "A", "B"), strings.drop(2));

assertEquals(sequence("A", "B"), strings.dropWhile(s -> Character.isDigit(s.charAt(0))));
~~~

toString
 --------

 The toString method can take a separator, that can be used
 to construct strings from the string representations (as per toString)
 of the elements of the sequence.

~~~ .java
Sequence<String> words = sequence("mouse", "dog", "cat");
assertEquals("mouse, dog, cat", words.toString(", "));
~~~

Zipping
-------

Two sequences can be zipped into a single collection of pairs:

~~~ .java
Sequence<String> firstNames = sequence("Marge", "Maude");
Sequence<String> lastNames = sequence("Simpson", "Flanders");

Sequence<Pair<String, String>> namePairs = firstNames.zip(lastNames);
assertEquals("Marge", namePairs.get(0).first());
assertEquals("Simpson", namePairs.get(0).second());

assertEquals("Maude", namePairs.get(1).first());
assertEquals("Flanders", namePairs.get(1).second());

// A more intersting way to use this feature:

Sequence<String> fullNames = namePairs.map(pair -> pair.first() + " " + pair.second());
assertEquals("Marge Simpson", fullNames.first());
assertEquals("Maude Flanders", fullNames.second());
~~~

A very common usecase is that while we iterate (or map!) over a sequence we need
the current element and its index. This is what the `zipWithIndex` method is for:

~~~ .java
Sequence<String> names = sequence("Mark", "Luke", "John", "Matthew");
Sequence<String> namesWithIndex = names
    .zipWithIndex()
    .map(pair -> pair.first() + ". " + pair.second());

assertEquals("0. Mark", namesWithIndex.get(0));
assertEquals("1. Luke", namesWithIndex.get(1));
assertEquals("2. John", namesWithIndex.get(2));
assertEquals("3. Matthew", namesWithIndex.get(3));
~~~

Grouping
--------
<p>
Sequences can be grouped by a key (function), like so:

~~~ .java
Sequence<Person> people = sequence(
    new Person("Homer", "Simpson"),
    new Person("Marge", "Simpson"),
    new Person("Ned", "Flanders"),
    new Person("Maude", "Flanders")
);

Sequence<Group<String, Person>> groups = people.groupBy(person -> person.lastname);

assertEquals("Simpson", groups.get(0).key());

assertEquals("Homer", groups.get(0).get(0).firstname);
assertEquals("Marge", groups.get(0).get(1).firstname);

assertEquals("Flanders", groups.get(1).key());

assertEquals("Ned", groups.get(1).get(0).firstname);
assertEquals("Maude", groups.get(1).get(1).firstname);
~~~

Development Info
================
<p>
This `README.md` is generated from the `IntroductionTest.java` test case.
To regenerate the content run `mvn test`.

~~~ .java

~~~
