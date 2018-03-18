package seedu.club.logic.commands;

import static seedu.club.logic.commands.CommandTestUtil.NON_EXISTENT_GROUP;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.club.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_TAG;
import static seedu.club.testutil.TypicalMembers.getTypicalClubBook;

import org.junit.Test;

import seedu.club.logic.CommandHistory;
import seedu.club.logic.UndoRedoStack;
import seedu.club.logic.commands.email.Body;
import seedu.club.logic.commands.email.Client;
import seedu.club.logic.commands.email.Subject;
import seedu.club.model.Model;
import seedu.club.model.ModelManager;
import seedu.club.model.UserPrefs;
import seedu.club.model.group.Group;
import seedu.club.model.tag.Tag;

public class EmailCommandTest {

    private Model model = new ModelManager(getTypicalClubBook(), new UserPrefs());
    private Client gmailClient = new Client(Client.VALID_CLIENT_GMAIL);
    private Client outlookClient = new Client(Client.VALID_CLIENT_OUTLOOK);
    private Subject testSubject = new Subject(Subject.TEST_SUBJECT_STRING);
    private Subject emptySubject = new Subject(Subject.EMPTY_SUBJECT_STRING);
    private Body testBody = new Body(Body.TEST_BODY_STRING);
    private Body emptyBody = new Body(Body.EMPTY_BODY_STRING);
    private Group groupToEmail;
    private Tag tagToEmail;

    @Test
    public void execute_validCommandToEmailGroupGmail_success() throws Exception {
        groupToEmail = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased()).getGroup();
        tagToEmail = null;

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, gmailClient,
                testSubject, testBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        Model expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, gmailClient, testSubject, testBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCommandToEmailGroupOutlook_success() throws Exception {
        groupToEmail = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased()).getGroup();
        tagToEmail = null;

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, outlookClient,
                testSubject, testBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        Model expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, gmailClient, testSubject, testBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCommandToEmailTagGmail_success() throws Exception {
        groupToEmail = null;
        tagToEmail = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, gmailClient,
                testSubject, testBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        Model expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, outlookClient, testSubject, testBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCommandToEmailTagOutlook_success() throws Exception {
        groupToEmail = null;
        tagToEmail = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, outlookClient,
                testSubject, testBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        Model expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, outlookClient, testSubject, testBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentGroup_throwCommandException() {
        Group nonExistentGroup = new Group(NON_EXISTENT_GROUP);
        tagToEmail = null;

        EmailCommand emailCommand = prepareCommand(nonExistentGroup, tagToEmail, gmailClient,
                testSubject, testBody);
        String expectedMessage = RemoveGroupCommand.MESSAGE_NON_EXISTENT_GROUP;

        assertCommandFailure(emailCommand, model, expectedMessage);
    }

    @Test
    public void execute_nonExistentTag_throwCommandException() {
        Tag nonExistentTag = new Tag(VALID_TAG_UNUSED);
        groupToEmail = null;

        EmailCommand emailCommand = prepareCommand(groupToEmail, nonExistentTag, outlookClient,
                testSubject, testBody);
        String expectedMessage = DeleteTagCommand.MESSAGE_NON_EXISTENT_TAG;

        assertCommandFailure(emailCommand, model, expectedMessage);
    }

    @Test
    public void execute_optionalSubject_success() throws Exception {
        groupToEmail = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased()).getGroup();
        tagToEmail = null;

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, gmailClient, emptySubject,
                testBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        Model expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, gmailClient, emptySubject, testBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_optionalBody_success() throws Exception {
        groupToEmail = null;
        tagToEmail = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, outlookClient, testSubject,
                emptyBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        Model expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, gmailClient, testSubject, emptyBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_optionalSubjectAndBody_success() throws Exception {
        groupToEmail = null;
        tagToEmail = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());

        EmailCommand emailCommand = prepareCommand(groupToEmail, tagToEmail, outlookClient, emptySubject,
                emptyBody);

        String expectedMessage = EmailCommand.EMAIL_CLIENT_OPENED;
        Model expectedModel = new ModelManager(model.getClubBook(), new UserPrefs());
        String expectedRecipients = expectedModel.generateEmailRecipients(groupToEmail, tagToEmail);
        expectedModel.sendEmail(expectedRecipients, gmailClient, emptySubject, emptyBody);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code EmailCommand} object with the parameters {@code group}, {@code tag},
     * {@code client}, {@code subject}, and {@code body}
     */
    private EmailCommand prepareCommand(Group group, Tag tag, Client client, Subject subject, Body body) {
        EmailCommand emailCommand = new EmailCommand(group, tag, client, subject, body);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return emailCommand;
    }
}