package seedu.club.model.task;

//@@author yash-chowdhary

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

/**
 * Refers to the time (deadline) the Task should be completed by.
 */
public class Time {

    public static final String TIME_MESSAGE_CONSTRAINTS = "Time must be in the format HH:MM and can be separated "
            + "by ':' or '-'";
    /**
     * Adapted from {@linktourl http://www.mkyong.com/regular-expressions/how-to-validate-time-in-24-hours-format-
     * with-regular-expression/}
     */
    public static final String TIME_VALIDATION_REGEX = "([01]?[0-9]|2[0-3])[:-][0-5][0-9]";
    public static final String TIME_SPLITTER = "[-:]";
    public static final String TIME_SEPARATOR = ":";

    private String time;
    private int hour;
    private int minute;

    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), TIME_MESSAGE_CONSTRAINTS);
        this.time = time;
    }

    /**
     * Returns true if the given string {@code time} is a valid time.
     */
    private Boolean isValidTime(String time) {
        if (!time.matches(TIME_VALIDATION_REGEX)) {
            return false;
        }
        String[] timeFields = time.split(TIME_SPLITTER);

        hour = Integer.parseInt(timeFields[0]);
        minute = Integer.parseInt(timeFields[1]);
        return true;
    }

    /**
     * Returns the time in a standard ':'-separated, 'HH:MM' format.
     */
    public String standardizeTime(String time) {
        StringBuilder sb = new StringBuilder();
        String[] timeFields = time.split(TIME_SPLITTER);

        hour = Integer.parseInt(timeFields[0]);
        sb.append(hour);
        sb.append(TIME_SEPARATOR);
        minute = Integer.parseInt(timeFields[1]);
        sb.append(minute);

        return sb.toString();
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
