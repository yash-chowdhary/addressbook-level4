package seedu.club.model.task;

//@@author yash-chowdhary

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

/**
 * Refers to the Description of a Task
 */
public class Description {

    public static final String DESCRIPTION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Description should a non-empty alphanumeric string";
    public final String description;

    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_DESCRIPTION_CONSTRAINTS);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns true if the given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Description   //handles nulls
                && this.description.equalsIgnoreCase(((Description) other).description));    //state check
    }
}
