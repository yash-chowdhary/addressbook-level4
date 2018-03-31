package seedu.club.model.task;
//@@author yash-chowdhary

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Refers to the Due Date of a Task
 */
public class Date {

    public static final String DATE_SPLITTER = "[///./-]";
    public static final String DATE_SEPARATOR = "/";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be a string separated by '.', '/', or "
            + "'-' in the format DD-MM-YYYY";
    /**
     * Adapted from {@linktourl http://www.mkyong.com/regular-expressions/how-to-validate-date-with-regular-expression/}
     */
    public static final String DATE_VALIDATION_REGEX = "(0[1-9]|[1-9]|1[0-9]|2[0-9]|3[01])[///./-]"
            + "(0[1-9]|1[0-2]|[1-9])[///./-](19|20)[0-9][0-9]";

    public final String date;

    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Returns true if the given string {@code date} follows the appropriate format and is a valid date.
     * Adapted from {@linktourl https://stackoverflow.com/questions/32204953/validating-java-8-dates?utm_medium=organic
     * &utm_source=* google_rich_qa&utm_campaign=google_rich_qa}
     */
    public static boolean isValidDate(String date) {
        if (!date.matches(DATE_VALIDATION_REGEX)) {
            return false;
        }
        String[] dateFields = date.split(DATE_SPLITTER);

        final int day = Integer.parseInt(dateFields[0]);
        final int month = Integer.parseInt(dateFields[1]);
        final int year = Integer.parseInt(dateFields[2]);

        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Returns the date in a standard '/'-separated, 'DD-MM-YYYY' format.
     */
    public String standardizeDate(String date) {
        String[] dateFields = date.split(DATE_SPLITTER);
        StringBuilder sb = new StringBuilder();

        final int day = Integer.parseInt(dateFields[0]);
        if (day < 10) {
            sb.append(0);
        }
        sb.append(day);
        sb.append(DATE_SEPARATOR);
        final int month = Integer.parseInt(dateFields[1]);
        if (month < 10) {
            sb.append(0);
        }
        sb.append(month);
        sb.append(DATE_SEPARATOR);
        final int year = Integer.parseInt(dateFields[2]);
        sb.append(year);

        return sb.toString();
    }

    public String getDate() {
        return standardizeDate(date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Date   //handles nulls
                && this.date.equalsIgnoreCase(((Date) other).date));    //state check
    }
}
