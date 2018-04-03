//@@author amrut-prabhu
package seedu.club.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import seedu.club.model.ClubBook;
import seedu.club.model.ReadOnlyClubBook;
import seedu.club.model.member.Member;


/**
 * Helps with reading from and writing to CSV files.
 */
public class CsvUtil {

    private static final String csvFieldSeparator = ",";
    private static final String csvFieldSurrounder = "\"";
    private static final String csvValueSeparator = ",";
    private static final String newline = System.lineSeparator();
    private static final String emptyString = "";

    /*private final String CSV_FIELD_SEPARATOR = ",";
    private final String CSV_FIELD_SURROUNDER = "\"";
    private final String CSV_VALUE_SEPARATOR = ",";
    private final String newline = System.lineSeparator();
    private final String EMPTY_STRING = "";*/

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
        addCsvField(builder, "Tags");

        builder.append(newline);

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
            return emptyString;
        }

        final StringBuilder builder = new StringBuilder();

        addCsvField(builder, memberToConvert.getName().toString());
        addCsvField(builder, memberToConvert.getPhone().toString());
        addCsvField(builder, memberToConvert.getEmail().toString());
        addCsvField(builder, memberToConvert.getMatricNumber().toString());
        addCsvField(builder, memberToConvert.getGroup().toString());
        addCsvTags(builder, memberToConvert);

        builder.append(newline);

        return builder.toString();
    }

    /**
     * Appends (@code builder} with all tags of {@code member} in CSV format.
     *
     * @param builder StringBuilder which is to be appended.
     * @param member Member whose tags are to be appended.
     */
    private static void addCsvTags(StringBuilder builder, Member member) {
        builder.append(csvFieldSurrounder);
        member.getTags().forEach(tag -> builder.append(tag.getTagName())
                .append(csvValueSeparator)); //Results in an extra "," at end of tag list.
        builder.append(csvFieldSurrounder)
                .append(csvFieldSeparator);
    }

    /**
     * Appends (@code builder} with {@code field} in CSV format.
     *
     * @param builder StringBuilder which is to be appended.
     * @param field Field value that is to be appended.
     */
    private static void addCsvField(StringBuilder builder, String field) {
        assert field != null : "Field cannot be null in Member object";

        builder.append(csvFieldSurrounder)
                .append(field)
                .append(csvFieldSurrounder)
                .append(csvFieldSeparator);
    }

    /**
     * Appends (@code builder} with last {@code field} in CSV format. {@code csvFieldSeparator} is not appended.
     *
     * @param builder StringBuilder which is to be appended.
     * @param field The final field value that is to be appended.
     */
    private static void addFinalCsvField(StringBuilder builder, String field) {
        assert field != null : "Field cannot be null in Member object";

        builder.append(csvFieldSurrounder)
                .append(field)
                .append(csvFieldSurrounder);
    }

    /**
     * Saves the data in the file in csv format.
     *
     * @param file Points to a valid csv file containing data that match the {@code classToConvert}.
     *             Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     */
    public static void saveDataToFile(File file, String data) throws IOException {
        requireNonNull(file);
        requireNonNull(data);

        if (!file.exists()) {
            throw new FileNotFoundException("File not found : " + file.getAbsolutePath());
        }

        FileUtil.appendToFile(file, data);
    }

    public static ReadOnlyClubBook getDataFromFile(File file) {
        return new ClubBook();
    }

}
