package seedu.club.logic.commands.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.club.model.email.Client;

public class ClientTest {

    @Test
    public void equals() {
        Client firstClient = new Client(Client.VALID_CLIENT_GMAIL);
        Client secondClient = new Client(Client.VALID_CLIENT_OUTLOOK);

        assertTrue(firstClient.equals(firstClient));

        Client firstClientCopy = new Client(Client.VALID_CLIENT_GMAIL);
        assertTrue(firstClient.equals(firstClientCopy));

        assertFalse(secondClient.equals(null));
    }

    @Test
    public void test_hashCode() {
        Client client = new Client(Client.VALID_CLIENT_OUTLOOK);
        assertEquals(client.hashCode(), Client.VALID_CLIENT_OUTLOOK.hashCode());
    }

    @Test
    public void test_isValidClient_success() {
        Client gmailClient = new Client(Client.VALID_CLIENT_GMAIL);
        Client outlookClient = new Client(Client.VALID_CLIENT_OUTLOOK);

        assertTrue(Client.isValidClient(gmailClient.toString()));
        assertTrue(Client.isValidClient(outlookClient.toString()));
    }

    @Test
    public void test_isValidClient_failure() {
        Client firstInvalidClient = new Client("yahoo");
        Client secondInvalidClient = new Client("hotmail");

        assertFalse(Client.isValidClient(firstInvalidClient.toString()));
        assertFalse(Client.isValidClient(secondInvalidClient.toString()));
    }
}
