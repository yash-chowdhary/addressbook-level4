package seedu.club.model.task;

//@@author yash-chowdhary
/**
 * Refers to the Description of a Task
 */
public class Description {

    public static final String DESCRIPTION_VALIDATION_REGEX = "^\\\\W*(?:\\\\w+\\\\b\\\\W*){1,6}$";
    private String description;

    public Description(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns true if the given string is a valid description.
     */
    public boolean isValidDescription(String test) {
        return description.matches(DESCRIPTION_VALIDATION_REGEX);
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
