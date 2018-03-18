package seedu.club.logic.commands.email;

import static java.util.Objects.requireNonNull;

//@@author yash-chowdhary
/**
 * Refers to the body of an email
 */
public class Body {

    public static final String EMPTY_BODY_STRING = "";
    private String body;

    public Body(String body) {
        requireNonNull(body);
        this.body = body.trim();
    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this        //short circuit if same object
                || (other instanceof Body   //handles nulls
                && this.body.equalsIgnoreCase(((Body) other).body));    //state check
    }

    @Override
    public String toString() {
        return this.body;
    }
}
