//@@author amrut-prabhu
package seedu.club.commons.util;

import static java.util.Objects.requireNonNull;

import seedu.club.model.member.Member;


/**
 * Helps with reading from and writing to CSV files.
 */
public class CsvUtil {

    private static final String CSV_FIELD_SEPARATOR = ",";
    private static final String CSV_FIELD_SURROUNDER = "\"";
    private static final String CSV_VALUE_SEPARATOR = ",";
    private static final String LINE_BREAK = "\n";
    private static final String EMPTY_STRING = "";

    /**
     * Returns {@code this} Member's data in the format of a CSV record.
     *
     * @return {@code String} containing the data in CSV format.
     */
    public static String toCsvFormat(Object objectToConvert) {
        requireNonNull(objectToConvert);

        Member memberToConvert = null;
        if (objectToConvert instanceof Member) {
            memberToConvert = (Member) objectToConvert;
        }
        else {
            return EMPTY_STRING;
        }

        final StringBuilder builder = new StringBuilder();

        addCsvField(builder, memberToConvert.getName().toString());
        addCsvField(builder, memberToConvert.getPhone().toString());
        addCsvField(builder, memberToConvert.getEmail().toString());
        addCsvField(builder, memberToConvert.getMatricNumber().toString());
        addCsvField(builder, memberToConvert.getGroup().toString());
        addCsvField(builder, memberToConvert.getProfilePhoto().toString());
        addCsvField(builder, memberToConvert.getUsername().toString());
        addCsvField(builder, memberToConvert.getPassword().toString());

        builder.append(CSV_FIELD_SURROUNDER);
        memberToConvert.getTags().forEach(tag -> builder.append(tag)
                .append(CSV_VALUE_SEPARATOR)); //Results in an extra "," at end of tag list.
        builder.append(CSV_FIELD_SURROUNDER);

        builder.append(LINE_BREAK);

        return builder.toString();
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

}
