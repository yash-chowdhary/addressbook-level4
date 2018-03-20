package seedu.club.model.member;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.club.testutil.MemberBuilder;

public class FieldContainsKeywordsPredicateTest {

    @Test
    public void equals_namePrefix() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, PREFIX_NAME);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(secondPredicateKeywordList, PREFIX_NAME);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, PREFIX_NAME);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different member -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_phonePrefix() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, PREFIX_PHONE);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(secondPredicateKeywordList, PREFIX_PHONE);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, PREFIX_PHONE);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different member -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_emailPrefix() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, PREFIX_EMAIL);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(secondPredicateKeywordList, PREFIX_EMAIL);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, PREFIX_EMAIL);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different member -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_matricNumberPrefix() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, PREFIX_MATRIC_NUMBER);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(secondPredicateKeywordList, PREFIX_MATRIC_NUMBER);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, PREFIX_MATRIC_NUMBER);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different member -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_groupPrefix() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, PREFIX_GROUP);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(secondPredicateKeywordList, PREFIX_GROUP);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, PREFIX_GROUP);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different member -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_tagPrefix() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, PREFIX_TAG);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(secondPredicateKeywordList, PREFIX_TAG);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, PREFIX_TAG);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different member -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_noPrefix() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, null);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(secondPredicateKeywordList, null);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldContainsKeywordsPredicate firstPredicateCopy =
                new FieldContainsKeywordsPredicate(firstPredicateKeywordList, null);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different member -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("Alice"), PREFIX_NAME);
        assertTrue(predicate.test(new MemberBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), PREFIX_NAME);
        assertTrue(predicate.test(new MemberBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"), PREFIX_NAME);
        assertTrue(predicate.test(new MemberBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"), PREFIX_NAME);
        assertTrue(predicate.test(new MemberBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("123"), PREFIX_PHONE);
        assertTrue(predicate.test(new MemberBuilder().withPhone("12345678").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("123", "456"), PREFIX_PHONE);
        assertTrue(predicate.test(new MemberBuilder().withPhone("123456").build()));

        // Only one matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("123", "456"), PREFIX_PHONE);
        assertTrue(predicate.test(new MemberBuilder().withPhone("5431236647").build()));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("Alice"), PREFIX_EMAIL);
        assertTrue(predicate.test(new MemberBuilder().withEmail("Alice@hotmail").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "Bob@"), PREFIX_EMAIL);
        assertTrue(predicate.test(new MemberBuilder().withEmail("AliceANDBob@outlook.com").build()));

        // Only one matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"), PREFIX_EMAIL);
        assertTrue(predicate.test(new MemberBuilder().withEmail("Carol@chacha.com").build()));

        // Mixed-case keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("aLIce"), PREFIX_EMAIL);
        assertTrue(predicate.test(new MemberBuilder().withEmail("Alice@hohoho.com").build()));
    }

    @Test
    public void test_matricNumberContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("1234567"), PREFIX_MATRIC_NUMBER);
        assertTrue(predicate.test(new MemberBuilder().withMatricNumber("A1234567H").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("A789", "541"), PREFIX_MATRIC_NUMBER);
        assertTrue(predicate.test(new MemberBuilder().withMatricNumber("A7896541H").build()));

        // Only one matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("594", "654"), PREFIX_MATRIC_NUMBER);
        assertTrue(predicate.test(new MemberBuilder().withMatricNumber("A7531594J").build()));

        // Small letters
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("a1234567h"), PREFIX_MATRIC_NUMBER);
        assertTrue(predicate.test(new MemberBuilder().withMatricNumber("A1234567H").build()));
    }

    @Test
    public void test_groupContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("logis"), PREFIX_GROUP);
        assertTrue(predicate.test(new MemberBuilder().withGroup("logistics").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("log", "tics"), PREFIX_GROUP);
        assertTrue(predicate.test(new MemberBuilder().withGroup("logistics").build()));

        // Only one matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("KLAN", "654"), PREFIX_GROUP);
        assertTrue(predicate.test(new MemberBuilder().withGroup("KLANIBAL").build()));

        // Mixed letters
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("wumBoLoGY"), PREFIX_GROUP);
        assertTrue(predicate.test(new MemberBuilder().withGroup("Wumbology").build()));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("Logi"), PREFIX_TAG);
        assertTrue(predicate.test(new MemberBuilder().withTags("Logistics").build()));

        // Multiple keywords
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("sai", "warrior"), PREFIX_TAG);
        assertTrue(predicate.test(new MemberBuilder().withTags("saikangWarriors").build()));

        // Only one matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("book", "nah"), PREFIX_TAG);
        assertTrue(predicate.test(new MemberBuilder().withTags("bookkeepers", "YOLO").build()));

        // Mixed letters
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("reSearChers"), PREFIX_TAG);
        assertTrue(predicate.test(new MemberBuilder().withTags("RESearchers").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.emptyList(), PREFIX_NAME);
        assertFalse(predicate.test(new MemberBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Carol"), PREFIX_NAME);
        assertFalse(predicate.test(new MemberBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and matric number, but does not match name
        predicate =
                new FieldContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"),
                        PREFIX_NAME);
        assertFalse(predicate.test(new MemberBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withMatricNumber("A1152241G").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.emptyList(), PREFIX_PHONE);
        assertFalse(predicate.test(new MemberBuilder().withPhone("12345678").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("123456"), PREFIX_PHONE);
        assertFalse(predicate.test(new MemberBuilder().withPhone("654321").build()));

        // Keywords match name, email and matric number, but does not match phone
        predicate =
                new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "alice@email.com", "A1152241G"),
                        PREFIX_PHONE);
        assertFalse(predicate.test(new MemberBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withMatricNumber("A1152241G").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(Collections.emptyList(),
                PREFIX_EMAIL);
        assertFalse(predicate.test(new MemberBuilder().withEmail("Alice@jojojoget.com").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Carol@caroling.com"), PREFIX_EMAIL);
        assertFalse(predicate.test(new MemberBuilder().withEmail("AliceBob@Fett.com").build()));

        // Keywords match name, phone and matric number, but does not match email
        predicate =
                new FieldContainsKeywordsPredicate(Arrays.asList("12345", "Alice", "A1152241G"), PREFIX_EMAIL);
        assertFalse(predicate.test(new MemberBuilder().withName("Alice").withPhone("12345")
                .withEmail("Wonderland@lolo.com").withMatricNumber("A1152241G").build()));
    }

    @Test
    public void test_matricNumberDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate =
                new FieldContainsKeywordsPredicate(Collections.emptyList(), PREFIX_MATRIC_NUMBER);
        assertFalse(predicate.test(new MemberBuilder().withMatricNumber("A1234567H").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("987"), PREFIX_MATRIC_NUMBER);
        assertFalse(predicate.test(new MemberBuilder().withMatricNumber("A1234567A").build()));

        // Keywords match name, phone and email, but does not match matric number
        predicate =
                new FieldContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Alice"),
                        PREFIX_MATRIC_NUMBER);
        assertFalse(predicate.test(new MemberBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withMatricNumber("A1152241G").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(Collections.emptyList(),
                PREFIX_TAG);
        assertFalse(predicate.test(new MemberBuilder().withTags("Saikanger").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("nerds"), PREFIX_TAG);
        assertFalse(predicate.test(new MemberBuilder().withTags("geeks", "jocks").build()));

        // Keywords match name, phone, email and matric number, but does not match tag
        predicate =
                new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com", "A1152241G"),
                        PREFIX_TAG);
        assertFalse(predicate.test(new MemberBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withMatricNumber("A1152241G").withTags("Lightbringer").build()));
    }

    @Test
    public void test_groupDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FieldContainsKeywordsPredicate predicate = new FieldContainsKeywordsPredicate(Collections.emptyList(),
                PREFIX_GROUP);
        assertFalse(predicate.test(new MemberBuilder().withGroup("SaikangRangers").build()));

        // Non-matching keyword
        predicate = new FieldContainsKeywordsPredicate(Arrays.asList("Relativist"), PREFIX_GROUP);
        assertFalse(predicate.test(new MemberBuilder().withGroup("QuantumMechanics").build()));

        // Keywords match name, phone, email, matric number and tag, but does not match group
        predicate =
                new FieldContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com",
                        "A1152241G", "Lightbringer"), PREFIX_GROUP);
        assertFalse(predicate.test(new MemberBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withMatricNumber("A1152241G").withTags("Lightbringer")
                .withGroup("Garoupa").build()));
    }
}
