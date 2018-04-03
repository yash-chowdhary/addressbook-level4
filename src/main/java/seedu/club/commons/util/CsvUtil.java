//@@author amrut-prabhu
package seedu.club.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_MEMBER_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.club.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;

import seedu.club.commons.exceptions.DataConversionException;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.parser.ArgumentMultimap;
import seedu.club.logic.parser.ArgumentTokenizer;
import seedu.club.logic.parser.ParserUtil;
import seedu.club.logic.parser.Prefix;
import seedu.club.model.group.Group;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
import seedu.club.model.member.UniqueMemberList;
import seedu.club.model.member.exceptions.DuplicateMemberException;
import seedu.club.model.tag.Tag;


/**
 * Helps with reading from and writing to CSV files.
 */
public class CsvUtil {

    private static final String CSV_FIELD_SEPARATOR = ",";
    private static final String CSV_FIELD_SURROUNDER = "\"";
    private static final String CSV_VALUE_SEPARATOR = ",";
    private static final String NEWLINE = System.lineSeparator();
    private static final String EMPTY_STRING = "";

    /**
     * Returns {@code this} Member's data in the format of a CSV record.
     *
     * @return {@code String} containing the data in CSV format.
     */
    public static String getHeaders() {
        final StringBuilder builder = new StringBuilder();

        addCsvField(builder, "Name");
        addCsvField(builder, "Phone");
        addCsvField(builder, "Email");
        addCsvField(builder, "Matriculation Number");
        addCsvField(builder, "Group");
        addLastCsvField(builder, "Tags");

        builder.append(NEWLINE);

        return builder.toString();
    }

    /**
     * Returns {@code this} Member's data in the format of a CSV record.
     *
     * @return {@code String} containing the data in CSV format.
     */
    public static String toCsvFormat(Object objectToConvert) {
        requireNonNull(objectToConvert);

        Member memberToConvert;
        if (objectToConvert instanceof Member) {
            memberToConvert = (Member) objectToConvert;
        } else {
            return EMPTY_STRING;
        }

        final StringBuilder builder = new StringBuilder();

        addCsvField(builder, memberToConvert.getName().toString());
        addCsvField(builder, memberToConvert.getPhone().toString());
        addCsvField(builder, memberToConvert.getEmail().toString());
        addCsvField(builder, memberToConvert.getMatricNumber().toString());
        addCsvField(builder, memberToConvert.getGroup().toString());
        addCsvTags(builder, memberToConvert);

        builder.append(NEWLINE);

        return builder.toString();
    }

    /**
     * Appends (@code builder} with all tags of {@code member} in CSV format.
     *
     * @param builder StringBuilder which is to be appended.
     * @param member Member whose tags are to be appended.
     */
    private static void addCsvTags(StringBuilder builder, Member member) {
        builder.append(CSV_FIELD_SURROUNDER);
        member.getTags().forEach(tag -> builder.append(tag.getTagName())
                .append(CSV_VALUE_SEPARATOR)); //Results in an extra "," at end of tag list.
        builder.append(CSV_FIELD_SURROUNDER); //No CSV_FIELD_SEPARATOR as this is the last field.
    }

    /**
     * Appends (@code builder} with {@code field} in CSV format.
     *
     * @param builder StringBuilder which is to be appended.
     * @param field Field value that is to be appended.
     */
    private static void addCsvField(StringBuilder builder, String field) {
        assert field != null : "Field cannot be null in Member object";

        builder.append(CSV_FIELD_SURROUNDER)
                .append(field)
                .append(CSV_FIELD_SURROUNDER)
                .append(CSV_FIELD_SEPARATOR);
    }

    /**
     * Appends (@code builder} with last {@code field} in CSV format without suffixing with{@code CSV_FIELD_SEPARATOR}.
     *
     * @param builder StringBuilder which is to be appended.
     * @param field The final field value that is to be appended.
     */
    private static void addLastCsvField(StringBuilder builder, String field) {
        assert field != null : "Field cannot be null in Member object";

        builder.append(CSV_FIELD_SURROUNDER)
                .append(field)
                .append(CSV_FIELD_SURROUNDER);
    }

    /**
     * Saves the data in the file in csv format.
     * Assumes file exists.
     *
     * @param file Points to a valid csv file.
     *             Cannot be null.
     * @throws IOException Thrown if there is an error writing to the file.
     */
    public static void saveDataToFile(File file, String data) throws IOException {
        requireNonNull(file);
        requireNonNull(data);

        FileUtil.appendToFile(file, data);
    }

    /**
     * Loads a {@code UniqueMemberList} from the data in the csv file.
     * Assumes file exists.
     *
     * @param file Points to a valid csv file containing data that match the {@code Member}.
     *             Cannot be null.
     * @throws DataConversionException Thrown if the data in file is not in the specified format.
     * @throws DuplicateMemberException Thhrown if there is a duplicate member in the file.
     * @throws IOException Thrown if there is an error reading from the file.
     */
    public static UniqueMemberList getDataFromFile(File file)
            throws DataConversionException, DuplicateMemberException, IOException {

        UniqueMemberList importedMembers = new UniqueMemberList();
        String data = FileUtil.readFromFile(file);
        String[] membersData = data.split("\n");

        for (String memberData: membersData) {
            Member member = getMember(memberData);
            importedMembers.add(member);
        }

        return new UniqueMemberList();
    }

    /**
     * Returns a {@code Member} created using the given raw {@code data}.
     *
     * @param data Contains all the data of the member extracted from the file.
     * @throws DataConversionException Thrown if the data of the member is not in the specified format.
     */
    private static Member getMember(String data) throws DataConversionException {
        StringBuilder memberData = new StringBuilder();

        try {
            return parseMember(memberData.toString());
        } catch (IllegalValueException ive) {
            throw new DataConversionException(ive);
        }
    }

    /**
     * Returns a member created by parsing {@code memberData}.
     *
     * @param memberData Data of the member with cli prefixes.
     * @throws IllegalValueException Thrown if the data does not conform the expected format.
     */
    private static Member parseMember(String memberData) throws IllegalValueException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(memberData, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_MATRIC_NUMBER, PREFIX_GROUP, PREFIX_TAG, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MATRIC_NUMBER, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_MEMBER_FORMAT, memberData));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
        MatricNumber matricNumber = ParserUtil.parseMatricNumber(argMultimap.getValue(PREFIX_MATRIC_NUMBER)).get();
        Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP))
                .orElse(new Group(Group.DEFAULT_GROUP));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Member member = new Member(name, phone, email, matricNumber, group, tagList);

        return member;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
