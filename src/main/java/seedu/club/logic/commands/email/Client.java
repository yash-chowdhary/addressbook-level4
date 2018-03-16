package seedu.club.logic.commands.email;

import static java.util.Objects.requireNonNull;

/**
 * Refers to the email client chosen by the member to send the email.
 */
public class Client {

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
}
