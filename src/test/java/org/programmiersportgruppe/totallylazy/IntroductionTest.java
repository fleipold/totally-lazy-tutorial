package org.programmiersportgruppe.totallylazy;

import com.googlecode.totallylazy.Group;
import com.googlecode.totallylazy.Sequence;
import org.junit.Test;

import static com.googlecode.totallylazy.Sequences.sequence;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

/**
 * [DOC file=README.md]
 */
public class IntroductionTest {

    /**
     *
     * This is a starting point for a tutorial for [TotallyLazy](https://totallylazy.com/) which has a all the things that we
     * are missing in the Java 8 class library.
     *
     *
     * Sequence
     * ========
     *
     * The basic abstraction of TotallyLazy is a `Sequence`. A sequence is a bit like
     * an `Iterable` with a lot of useful methods. Wherever possible these methods are
     * lazily evaluated, e.g. `map`, `filter`, while some methods like `fold` force
     * the evaluation the chain so far.
     *
     * The typical way to construct a `Sequence` is to use one of the static `sequence` factory methods.
     */
    @Test
    public void showConstructionOfSequences() {
        Sequence<Integer> fromVarargs = sequence(1, 2, 3);

        Sequence<Integer> fromArray = sequence(new Integer[]{1, 2, 3});

        Sequence<Integer> fromIterable = sequence(asList(1, 2, 3));
    }

    private static class Person {
        public Person(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }

        String firstname;
        String lastname;

        @Override
        public String toString() {
            return "Person{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
        }
    }

    /**
     * Grouping
     * --------
     *
     * Sequences can be grouped by a key (function), like so:
     *
     * */
    @Test
    public void showGroupBy() {

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
    }

    /**
     * Development Info
     * ================
     *
     * This `README.md` is generated from the `IntroductionTest.java` test case.
     * To regenerate the content run `mvn test`.
     * */
    @Test
    public void notATest() {
    }
}
