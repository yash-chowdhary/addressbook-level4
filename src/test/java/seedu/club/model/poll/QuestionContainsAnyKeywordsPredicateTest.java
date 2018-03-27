package seedu.club.model.poll;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.club.testutil.PollBuilder;

public class QuestionContainsAnyKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        QuestionContainsAnyKeywordsPredicate firstPredicate =
                new QuestionContainsAnyKeywordsPredicate(firstPredicateKeywordList);
        QuestionContainsAnyKeywordsPredicate secondPredicate =
                new QuestionContainsAnyKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        QuestionContainsAnyKeywordsPredicate firstPredicateCopy =
                new QuestionContainsAnyKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_questionContainsExactKeywords_returnsTrue() {
        // One keyword
        QuestionContainsAnyKeywordsPredicate predicate =
                new QuestionContainsAnyKeywordsPredicate(Collections.singletonList("Father?"));
        assertTrue(predicate.test(new PollBuilder().withQuestion("Father?").build()));

        // Multiple keywords
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("Is", "the", "weather", "nice?"));
        assertTrue(predicate.test(new PollBuilder().withQuestion("Is the weather nice?").build()));

        // Mixed-case keywords
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("cAn", "i", "EAT", "HiM?"));
        assertTrue(predicate.test(new PollBuilder().withQuestion("Can I eat him?").build()));

        // Match partially
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("Did", "you"));
        assertTrue(predicate.test(new PollBuilder().withQuestion("Did you watch the show last night?").build()));
    }

    @Test
    public void test_questionDoesNotContainExactKeywords_returnsFalse() {
        // Zero keywords
        QuestionContainsAnyKeywordsPredicate predicate =
                new QuestionContainsAnyKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PollBuilder().withQuestion("Did you watch the show last night?").build()));

        // Non-matching keyword
        predicate = new QuestionContainsAnyKeywordsPredicate(Arrays.asList("Who", "reads", "this?"));
        assertFalse(predicate.test(new PollBuilder().withQuestion("Did you watch the show last night?").build()));
    }
}
