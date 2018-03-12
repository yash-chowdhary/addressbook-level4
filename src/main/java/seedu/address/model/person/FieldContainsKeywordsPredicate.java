package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s field value matches any of the keywords given.
 */
public class FieldContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final String fieldValue;

    public FieldContainsKeywordsPredicate(List<String> keywords, String fieldValue) {
        this.fieldValue = fieldValue;
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(fieldValue, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FieldContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((FieldContainsKeywordsPredicate) other).keywords)); // state check
    }
}
