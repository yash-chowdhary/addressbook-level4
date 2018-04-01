package seedu.club.model.email;
//@@author yash-chowdhary

import static java.util.Objects.requireNonNull;

/**
 * Refers to the email client chosen by the member to send the email.
 */
public class Client {

    public static final String VALID_CLIENT_GMAIL = "gmail";
    public static final String VALID_CLIENT_OUTLOOK = "outlook";
    public static final String MESSAGE_CLIENT_CONSTRAINTS = "Only GMail and Outlook email clients are supported";

    private String client;

    public Client(String client) {
        requireNonNull(client);
        this.client = client.trim();
    }

    @Override
    public String toString() {
        return this.client;
    }

    @Override
    public int hashCode() {
        return client.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this    //short circuit if same object
                || (other instanceof Client     //instanceof handles nulls
                && this.client.equalsIgnoreCase(((Client) other).client));  //state check
    }

    public static boolean isValidClient(String test) {
        return (test.equalsIgnoreCase(VALID_CLIENT_GMAIL) || test.equalsIgnoreCase(VALID_CLIENT_OUTLOOK));
    }
}
