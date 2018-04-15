//@@author amrut-prabhu
package seedu.club.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_MEMBER_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.club.commons.core.LogsCenter;
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
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.tag.Tag;


/**
 * Helps with reading from and writing to CSV files.
 */
public class CsvUtil {

    private static final String CSV_FIELD_SEPARATOR = ",";
    private static final String CSV_FIELD_SURROUNDER = "\"";
    private static final String CSV_VALUE_SEPARATOR = ",";
    private static final String EMPTY_STRING = "";
    private static final String NEWLINE = System.lineSeparator();
    private static final String SPACE = " ";

    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    // ================ Import CSV data methods ==============================

    /**
     * Returns {@code this} Member's data in the format of a CSV record.
     *
     * @return {@code String} containing the data in CSV format.
     */
    public static String getHeaders() {
        final StringBuilder builder = new StringBuilder();

        addFieldInCsv(builder, "Name");
        addFieldInCsv(builder, "Phone");
        addFieldInCsv(builder, "Email");
        addFieldInCsv(builder, "Matriculation Number");
        addFieldInCsv(builder, "Group");
        addLastCsvField(builder, "Tags");

        builder.append(NEWLINE);

        return builder.toString();
    }

    /**
     * Returns {@code objectToConver}'s data in the format of a CSV record.
     * objectToConvert is expected to be a {@code Member} object.
     *
     *  @return {@code String} containing the data in CSV format.
     * @see Member
     */
    public static String toCsvFormat(Object objectToConvert) {
        requireNonNull(objectToConvert);

        Member memberToConvert;
        if (objectToConvert instanceof Member) {
            memberToConvert = (Member) objectToConvert;
        } else {
            assert false : "Object to convert to CSV is expected to be a Member object";
            return EMPTY_STRING;
        }

        StringBuilder builder = new StringBuilder();

        addFieldInCsv(builder, memberToConvert.getName().toString());
        addFieldInCsv(builder, memberToConvert.getPhone().toString());
        addFieldInCsv(builder, memberToConvert.getEmail().toString());
        addFieldInCsv(builder, memberToConvert.getMatricNumber().toString());
        addFieldInCsv(builder, memberToConvert.getGroup().toString());
        addCsvTags(builder, memberToConvert);

        builder.append(NEWLINE);

        return builder.toString();
    }

    /**
     * Appends (@code builder} with {@code field} in CSV format.
     *
     * @param builder StringBuilder which is to be appended to.
     * @param field Field value that is to be appended.
     */
    private static void addFieldInCsv(StringBuilder builder, String field) {
        assert field != null : "Field cannot be null in Member object";

        builder.append(CSV_FIELD_SURROUNDER)
                .append(field)
                .append(CSV_FIELD_SURROUNDER)
                .append(CSV_FIELD_SEPARATOR);
    }

    /**
     * Appends (@code builder} with last {@code field} in CSV format without suffixing with {@code CSV_FIELD_SEPARATOR}.
     *
     * @param builder StringBuilder which is to be appended to.
     * @param field The final field value that is to be appended.
     */
    private static void addLastCsvField(StringBuilder builder, String field) {
        assert field != null : "Field cannot be null in Member object";

        builder.append(CSV_FIELD_SURROUNDER)
                .append(field)
                .append(CSV_FIELD_SURROUNDER);
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

    // ================ Import CSV data methods ==============================

    /**
     * Returns a {@code Member} created using the given raw {@code rawData}.
     *
     * @param rawData Contains all the rawData of the member extracted from the file.
     * @throws DataConversionException Thrown if the rawData of the member is not in the specified format.
     */
    private static Member getMember(String rawData) throws DataConversionException {
        String remainingData = rawData;
        String memberData = SPACE;
        String memberFieldValue;
        String[] fieldValues;

        try {
            fieldValues = nextValue(remainingData);
            memberFieldValue = fieldValues[0];
            remainingData = fieldValues[1];
            memberData = addMemberData(memberData, PREFIX_NAME.toString(), memberFieldValue);

            fieldValues = nextValue(remainingData);
            memberFieldValue = fieldValues[0];
            remainingData = fieldValues[1];
            memberData = addMemberData(memberData, PREFIX_PHONE.toString(), memberFieldValue);

            fieldValues = nextValue(remainingData);
            memberFieldValue = fieldValues[0];
            remainingData = fieldValues[1];
            memberData = addMemberData(memberData, PREFIX_EMAIL.toString(), memberFieldValue);

            fieldValues = nextValue(remainingData);
            memberFieldValue = fieldValues[0];
            remainingData = fieldValues[1];
            memberData = addMemberData(memberData, PREFIX_MATRIC_NUMBER.toString(), memberFieldValue);

            fieldValues = nextValue(remainingData);
            memberFieldValue = fieldValues[0];
            remainingData = fieldValues[1];
            memberData = addMemberData(memberData, PREFIX_GROUP.toString(), memberFieldValue);

            fieldValues = nextValue(remainingData);
            memberFieldValue = fieldValues[0];
            memberData = addMemberData(memberData, PREFIX_TAG.toString(), memberFieldValue);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            throw new DataConversionException(aioobe);
        }

        try {
            return parseMember(memberData);
        } catch (IllegalValueException ive) {
            throw new DataConversionException(ive);
        }
    }

    /**
     * Returns the next data value of the member.
     * @param data Raw member data.
     * @return {@code String[]} with the next data value at index 0 and remaining data (if it exists) at index 1.
     */
    private static String[] nextValue(String data) {
        String[] values;
        if (data.charAt(0) == '\"') {
            values = data.substring(1).split(CSV_FIELD_SURROUNDER + CSV_FIELD_SEPARATOR, 2);
        } else {
            values = data.split(CSV_FIELD_SEPARATOR, 2);
        }

        return values;
    }

    /**
     * Appends {@code dataToAdd} to {@code memberData} in the required format.
     *
     * @param memberData The current data of the member.
     * @param prefix The prefix needed, depending on the type of {@code dataToAdd}.
     * @param dataToAdd The data that is to be added to {@code memberData}.
     * @return {@code memberData} appended with {@code dataToAdd} in the required format.
     */
    private static String addMemberData(String memberData, String prefix, String dataToAdd) {
        StringBuilder builder = new StringBuilder(memberData);

        if (prefix.equals(PREFIX_TAG.toString())) {
            return addMemberTags(memberData, dataToAdd);
        } else if (dataToAdd.length() != 0) {
            builder.append(prefix).append(removeExcessCharacters(dataToAdd)).append(SPACE);
        }

        return builder.toString();
    }

    /**
     * Appends the tags in {@code dataToAdd} to {@code memberData} in the required format.
     *
     * @param memberData The current data of the member.
     * @param dataToAdd The tags to be added to {@code memberData}.
     * @return {@code memberData} appended with {@code dataToAdd} in the required format.
     */
    private static String addMemberTags(String memberData, String dataToAdd) {
        StringBuilder builder = new StringBuilder(memberData);

        if (dataToAdd.length() != 0) {
            String[] tags = dataToAdd.split(",");

            for (String tag : tags) {
                tag = removeExcessCharacters(tag);
                if (tag.length() > 0) {
                    builder.append(PREFIX_TAG).append(tag).append(SPACE);
                }
            }
        }

        return builder.toString();
    }

    /**
     * Removes leading and trailing whitespaces and double quotes (") from {@code data}.
     */
    private static String removeExcessCharacters(String data) {
        requireNonNull(data);

        //Remove whitespace
        data = data.trim();

        //Remove double quotes(")
        if (data.length() > 0 && data.charAt(0) == '\"') { //First character is "
            data = data.substring(1);
        } else if (data.length() > 0 && data.charAt(data.length() - 1) == '\"') { //Last character is "
            data = data.substring(0, data.length() - 1);
        }

        return data;
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
                        PREFIX_MATRIC_NUMBER, PREFIX_GROUP, PREFIX_TAG);

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


    // ================ CSV File level methods ==============================

    /**
     * Returns true if {@code path} represents the path of a CSV (.csv) file.
     *
     * @param path Path whose validity is to be checked.
     */
    public static boolean isValidCsvFileName(String path) {
        String csvFileExtension = ".csv";

        int length = path.length();
        String fileExtension = path.substring(length - 4);

        return fileExtension.compareToIgnoreCase(csvFileExtension) == 0;
    }

    /**
     * Loads a {@code UniqueMemberList} from the data in the csv file.
     * Assumes file exists.
     * Ignores DataConversionException and DuplicateMemberException.
     *
     * @param file Points to a valid csv file containing data that match the {@code Member}.
     *             Cannot be null.
     * @throws IOException Thrown if there is an error reading from the file.
     */
    public static UniqueMemberList getDataFromFile(File file) throws IOException {
        requireNonNull(file);
        UniqueMemberList importedMembers = new UniqueMemberList();
        String data = FileUtil.readFromFile(file);
        String[] membersData = data.split("\n");

        for (int i = 1; i < membersData.length; i++) { //membersData[0] contains column headers
            try {
                Member member = getMember(membersData[i]);
                importedMembers.add(member);
            } catch (DataConversionException dce) {
                logger.warning("DataConversionException encountered while converting " + membersData[i]);
            } catch (DuplicateMatricNumberException dmne) {
                logger.warning("DuplicateMemberException encountered due to " + membersData[i]);
            }
        }

        return importedMembers;
    }

}
