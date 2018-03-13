package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s matches any of the keywords given according to the {@code fieldType}.
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
                && this.keywords.equals(((FieldContainsKeywordsPredicate) other).keywords) // state check
                && this.fieldType.equals(((FieldContainsKeywordsPredicate) other).fieldType));
    }

    /**
     * Get relevant field value of person according to fieldType
     *
     */
    private String getFieldValue(Person person) {
        switch (fieldType) {
        case "email":
            return person.getEmail().toString();
        case "phone":
            return person.getPhone().toString();
        case "matric":
            return person.getMatricNumber().toString();
        case "group":
            return person.getGroup().toString();
        case "tag":
            return person.getTags().stream().map(Tag::toString).collect(Collectors.joining(" "));
        default:
            return person.getName().toString();
        }
    }
}
