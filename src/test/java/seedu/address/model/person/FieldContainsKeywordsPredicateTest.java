package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class FieldContainsKeywordsPredicateTest {

    @Test
    public void equals_nameFieldType() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "name");
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(secondPredicateKeywordList, "name");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "name");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_phoneFieldType() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "phone");
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(secondPredicateKeywordList, "phone");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "phone");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_emailFieldType() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "email");
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(secondPredicateKeywordList, "email");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "email");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_matricNumberFieldType() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "matric");
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(secondPredicateKeywordList, "matric");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "matric");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_groupFieldType() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "group");
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(secondPredicateKeywordList, "group");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "group");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_tagFieldType() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "tag");
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(secondPredicateKeywordList, "tag");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, "tag");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("Alice"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("123"), "phone");
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("123", "456"), "phone");
        assertTrue(predicate.test(new PersonBuilder().withPhone("123456").build()));

        // Only one matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("123", "456"), "phone");
        assertTrue(predicate.test(new PersonBuilder().withPhone("5431236647").build()));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("Alice"), "email");
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alice@hotmail").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "Bob@"), "email");
        assertTrue(predicate.test(new PersonBuilder().withEmail("AliceANDBob@outlook.com").build()));

        // Only one matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"), "email");
        assertTrue(predicate.test(new PersonBuilder().withEmail("Carol@chacha.com").build()));

        // Mixed-case keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("aLIce"), "email");
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alice@hohoho.com").build()));
    }

    @Test
    public void test_matricNumberContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("1234567"), "matric");
        assertTrue(predicate.test(new PersonBuilder().withMatricNumber("A1234567H").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("A789", "541"), "matric");
        assertTrue(predicate.test(new PersonBuilder().withMatricNumber("A7896541H").build()));

        // Only one matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("594", "654"), "matric");
        assertTrue(predicate.test(new PersonBuilder().withMatricNumber("A7531594J").build()));

        // Small letters
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("a1234567h"), "matric");
        assertTrue(predicate.test(new PersonBuilder().withMatricNumber("A1234567H").build()));
    }

    @Test
    public void test_groupContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("logis"), "group");
        assertTrue(predicate.test(new PersonBuilder().withGroup("logistics").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("log", "tics"), "group");
        assertTrue(predicate.test(new PersonBuilder().withGroup("logistics").build()));

        // Only one matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("KLAN", "654"), "group");
        assertTrue(predicate.test(new PersonBuilder().withGroup("KLANIBAL").build()));

        // Mixed letters
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("wumBoLoGY"), "group");
        assertTrue(predicate.test(new PersonBuilder().withGroup("Wumbology").build()));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("Logi"), "tag");
        assertTrue(predicate.test(new PersonBuilder().withTags("Logistics").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("sai", "warrior"), "tag");
        assertTrue(predicate.test(new PersonBuilder().withTags("saikangWarriors").build()));

        // Only one matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("book", "nah"), "tag");
        assertTrue(predicate.test(new PersonBuilder().withTags("bookkeepers", "YOLO").build()));

        // Mixed letters
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("reSearChers"), "tag");
        assertTrue(predicate.test(new PersonBuilder().withTags("RESearchers").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(Collections.emptyList(), "name");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Carol"), "name");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and matric number, but does not match name
        predicate =
                new FieldContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"), "name");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withMatricNumber("A1152241G").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(Collections.emptyList(), "phone");
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("123456"), "phone");
        assertFalse(predicate.test(new PersonBuilder().withPhone("654321").build()));

        // Keywords match name, email and matric number, but does not match phone
        predicate =
                new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "alice@email.com", "A1152241G"), "phone");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withMatricNumber("A1152241G").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(Collections.emptyList(), "email");
        assertFalse(predicate.test(new PersonBuilder().withEmail("Alice@jojojoget.com").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Carol@caroling.com"), "email");
        assertFalse(predicate.test(new PersonBuilder().withEmail("AliceBob@Fett.com").build()));

        // Keywords match name, phone and matric number, but does not match email
        predicate =
                new FieldContainsKeywordsPredicate(Arrays.asList("12345", "Alice", "A1152241G"), "email");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("Wonderland@lolo.com").withMatricNumber("A1152241G").build()));
    }

    @Test
    public void test_matricNumberDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.emptyList(), "matric");
        assertFalse(predicate.test(new PersonBuilder().withMatricNumber("A1234567H").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("987"), "matric");
        assertFalse(predicate.test(new PersonBuilder().withMatricNumber("A1234567A").build()));

        // Keywords match name, phone and email, but does not match matric number
        predicate =
                new FieldContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice"), "matric");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withMatricNumber("A1152241G").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(Collections.emptyList(), "tag");
        assertFalse(predicate.test(new PersonBuilder().withTags("Saikanger").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("nerds"), "tag");
        assertFalse(predicate.test(new PersonBuilder().withTags("geeks", "jocks").build()));

        // Keywords match name, phone, email and matric number, but does not match tag
        predicate =
                new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com", "A1152241G"),
                        "tag");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withMatricNumber("A1152241G").withTags("Lightbringer").build()));
    }

    @Test
    public void test_groupDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(Collections.emptyList(), "group");
        assertFalse(predicate.test(new PersonBuilder().withGroup("SaikangRangers").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Relativist"), "group");
        assertFalse(predicate.test(new PersonBuilder().withGroup("QuantumMechanics").build()));

        // Keywords match name, phone, email, matric number and tag, but does not match group
        predicate =
                new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com",
                        "A1152241G", "Lightbringer"), "group");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withMatricNumber("A1152241G").withTags("Lightbringer")
                .withGroup("Garoupa").build()));
    }
}
