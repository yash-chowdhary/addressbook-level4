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
 * Tests that a {@code member}'s matches any of the keywords given according to the fieldType given by {@code prefix}.
 */
public class FieldContainsKeywordsPredicate implements Predicate<Member> {

    private final List<String> keywords;
    private final Prefix prefix;

    public FieldContainsKeywordsPredicate(List<String> keywords, Prefix prefix) {
        this.keywords = keywords;
        this.prefix = prefix;
    }

    @Override
    public boolean test(Member member) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.partiallyContainsWordIgnoreCase(getFieldValue(member), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof  FieldContainsKeywordsPredicate)) {
            return false;
        }
        if (!(this.keywords.equals(((FieldContainsKeywordsPredicate) other).keywords))) {
            return false;
        }
        if (this.prefix == null && ((FieldContainsKeywordsPredicate) other).prefix == null) {
            return true;
        }
        if (this.prefix == null || ((FieldContainsKeywordsPredicate) other).prefix == null) {
            return false;
        }
        return this.prefix.equals(((FieldContainsKeywordsPredicate) other).prefix);
    }

    /**
     * Get relevant field value of member according to {@code prefix}
     *  or all field values if {@code prefix} is null     *
     */
    private String getFieldValue(Member member) {

        String name = member.getName().toString();
        String phone = member.getPhone().toString();
        String email = member.getEmail().toString();
        String matricNumber = member.getMatricNumber().toString();
        String group = member.getGroup().toString();
        String tags = member.getTags().stream().map(Tag::toString).collect(Collectors.joining(" "));

        if (PREFIX_NAME.equals(prefix)) {
            return name;
        } else if (PREFIX_PHONE.equals(prefix)) {
            return phone;
        } else if (PREFIX_EMAIL.equals(prefix)) {
            return email;
        } else if (PREFIX_MATRIC_NUMBER.equals(prefix)) {
            return matricNumber;
        } else if (PREFIX_GROUP.equals(prefix)) {
            return group;
        } else if (PREFIX_TAG.equals(prefix)) {
            return tags;
        } else {
            return name + " " + phone + " " + email + " " + matricNumber + " " + group + " " + tags;
        }
    }
}
