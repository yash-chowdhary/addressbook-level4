package seedu.club.commons.events.ui;

import seedu.club.commons.events.BaseEvent;
import seedu.club.logic.commands.email.Body;
import seedu.club.logic.commands.email.Client;
import seedu.club.logic.commands.email.Subject;

//@@author yash-chowdhary
/**
 *
 */
public class SendEmailRequestEvent extends BaseEvent {

    private String recipients;
    private Subject subject;
    private Body body;
    private Client client;

    public SendEmailRequestEvent(String recipients, Subject subject, Body body, Client client) {
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
        this.client = client;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getRecipients() {
        return recipients;
    }

    public Subject getSubject() {
        return subject;
    }

    public Body getBody() {
        return body;
    }

    public Client getClient() {
        return client;
    }
}
