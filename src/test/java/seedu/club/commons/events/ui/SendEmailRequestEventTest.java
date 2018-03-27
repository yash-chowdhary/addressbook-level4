package seedu.club.commons.events.ui;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;

//@@author yash-chowdhary
public class SendEmailRequestEventTest {

    @Test
    public void testSendEmailRequestEvent() throws Exception {
        String expectedRecipients = "abc@example.com,xyz@test.com,pqr@gmail.com";
        Subject expectedSubject = new Subject(Subject.TEST_SUBJECT_STRING);
        Body expectedBody = new Body(Body.TEST_BODY_STRING);
        Client expectedClient = new Client(Client.VALID_CLIENT_GMAIL);

        SendEmailRequestEvent sendEmailRequestEvent = new SendEmailRequestEvent(expectedRecipients,
                expectedSubject, expectedBody, expectedClient);
        assertTrue(sendEmailRequestEvent.getRecipients().equals(expectedRecipients));
        assertTrue(sendEmailRequestEvent.getSubject().equals(expectedSubject));
        assertTrue(sendEmailRequestEvent.getBody().equals(expectedBody));
        assertTrue(sendEmailRequestEvent.getClient().equals(expectedClient));
    }
}
