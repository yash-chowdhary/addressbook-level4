package seedu.club.model.task;

//@@author yash-chowdhary

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

/**
 * Refers to the time (deadline) the Task should be completed by.
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time must be in the format HH:MM and can be separated "
            + "by ':'";
    /**
     * Adapted from {@linktourl http://www.mkyong.com/regular-expressions/how-to-validate-time-in-24-hours-format-
     * with-regular-expression/}
     */
    public static final String TIME_VALIDATION_REGEX = "(0[0-9]|1[0-9]|2[0-3]):"
            + "(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])";
    public static final String TIME_SPLITTER = ":";

    public final String time;

    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        this.time = time;
    }

    /**
     * Returns true if the given string {@code test} is a valid test.
     */
    public static boolean isValidTime(String test) {
        if (!test.matches(TIME_VALIDATION_REGEX)) {
            return false;
        }
        String[] timeFields = test.split(TIME_SPLITTER);

        final int hour = Integer.parseInt(timeFields[0]);
        final int minute = Integer.parseInt(timeFields[1]);
        return true;
    }

    /**
     * Returns the time in a standard ':'-separated, 'HH:MM' format.
     */
    public String standardizeTime(String time) {
        StringBuilder sb = new StringBuilder();
        String[] timeFields = time.split(TIME_SPLITTER);

        final int hour = Integer.parseInt(timeFields[0]);
        sb.append(hour);
        sb.append(TIME_SPLITTER);
        final int minute = Integer.parseInt(timeFields[1]);
        if (minute < 10) {
            sb.append(0);
        }
        sb.append(minute);

        return sb.toString();
    }

    public String getTime() {
        return standardizeTime(time);
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Time   //handles nulls
                && this.time.equalsIgnoreCase(((Time) other).time));    //state check
    }
}
