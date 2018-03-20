package seedu.club.model.member;

import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.club.commons.util.StringUtil;
import seedu.club.logic.parser.Prefix;
import seedu.club.model.tag.Tag;

/**
 * Tests that a {@code member}'s matches any of the keywords given according to the {@code fieldType}.
 */
public class FieldContainsKeywordsPredicate implements Predicate<Member> {

    private final List<String> keywords;
    private final String fieldType;

    public FieldContainsKeywordsPredicate(List<String> keywords, String fieldType) {
        this.keywords = keywords;
        this.fieldType = fieldType;
    }

    @Override
    public boolean test(Member member) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.partiallyContainsWordIgnoreCase(getFieldValue(member), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FieldContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((FieldContainsKeywordsPredicate) other).keywords) // state check
                && this.fieldType.equals(((FieldContainsKeywordsPredicate) other).fieldType));
    }

    /**
     * Get relevant field value of member according to {@code fieldType}
     *  or all field values if {@code fieldType} is null     *
     */
    private String getFieldValue(Member member) {

        String name = member.getName().toString();
        String phone = member.getPhone().toString();
        String email = member.getEmail().toString();
        String matricNumber = member.getMatricNumber().toString();
        String group = member.getGroup().toString();
        String tags = member.getTags().stream().map(Tag::toString).collect(Collectors.joining(" "));

        if (PREFIX_NAME.toString().equals(fieldType)) {
            return name;
        } else if (PREFIX_PHONE.toString().equals(fieldType)) {
            return phone;
        } else if (PREFIX_EMAIL.toString().equals(fieldType)) {
            return email;
        } else if (PREFIX_MATRIC_NUMBER.toString().equals(fieldType)) {
            return matricNumber;
        } else if (PREFIX_GROUP.toString().equals(fieldType)) {
            return group;
        } else if (PREFIX_TAG.toString().equals(fieldType)) {
            return tags;
        } else {
            return fieldType + " " + name + " " + phone + " " + email + " " + matricNumber + " " + group + " " + tags;
        }
    }
}
