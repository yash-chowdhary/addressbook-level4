package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s field value matches any of the keywords given.
 */
public class FieldContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final String fieldType;

    public FieldContainsKeywordsPredicate(List<String> keywords, String fieldType) {
        this.keywords = keywords;
        this.fieldType = fieldType;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.partiallyContainsWordIgnoreCase(getFieldValue(person), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FieldContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((FieldContainsKeywordsPredicate) other).keywords)); // state check
    }

    private String getFieldValue(Person person) {
        switch (fieldType) {
        case "name":
            return person.getName().toString();
        case "email":
            return person.getEmail().toString();
        case "phone":
            return person.getPhone().toString();
        case "matric":
            return person.getMatricNumber().toString();
        case "tag":
            return person.getTags().stream().map(tag->tag.toString()).collect(Collectors.joining(" "));
        default:
            //TODO
            return null;
        }
    }
}
