package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.GROUP_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.club.logic.commands.CommandTestUtil.MATRIC_NUMBER_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.club.logic.commands.CommandTestUtil.VALID_CLIENT;
import static seedu.club.logic.commands.CommandTestUtil.VALID_CLIENT_DESC;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.club.logic.commands.EmailCommand;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.tag.Tag;

public class EmailCommandParserTest {
    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_fieldNotPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE);

        //blank space
        assertParseFailure(parser, " ", expectedMessage);

        //newline character
        assertParseFailure(parser, "\n", expectedMessage);

        //group should be preceded with 'g/' prefix
        assertParseFailure(parser, VALID_GROUP_AMY, expectedMessage);
        assertParseFailure(parser, VALID_GROUP_BOB, expectedMessage);

        //tag should be preceded with 't/' prefix
        assertParseFailure(parser, VALID_TAG_FRIEND, expectedMessage);
    }

    @Test
    public void parse_incorrectField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE);
        assertParseFailure(parser, NAME_DESC_AMY, expectedMessage);
        assertParseFailure(parser, MATRIC_NUMBER_DESC_AMY, expectedMessage);
    }

    @Test
    public void parse_invalidGroupFormat_failure() {
        String expectedMessage = Group.MESSAGE_GROUP_CONSTRAINTS;
        String invalidCommandOne = INVALID_GROUP_DESC + VALID_CLIENT_DESC;
        String invalidCommandTwo = " " + PREFIX_GROUP.toString() + " " + VALID_CLIENT_DESC;
        assertParseFailure(parser, invalidCommandOne, expectedMessage);
        assertParseFailure(parser, invalidCommandTwo, expectedMessage);
    }

    @Test
    public void parse_invalidTagFormat_failure() {
        String expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;
        String invalidCommandOne = INVALID_TAG_DESC + VALID_CLIENT_DESC;
        String invalidCommandTwo = " " + PREFIX_TAG.toString() + " " + VALID_CLIENT_DESC;
        assertParseFailure(parser, invalidCommandOne, expectedMessage);
        assertParseFailure(parser, invalidCommandTwo, expectedMessage);
    }

    @Test
    public void parse_bothGroupAndTagPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE);
        String invalidCommand = GROUP_DESC_AMY + TAG_DESC_FRIEND;
        assertParseFailure(parser, invalidCommand, expectedMessage);
    }

    @Test
    public void parse_allFieldPresent_success() {
        String command = VALID_CLIENT_DESC + GROUP_DESC_AMY;
        Tag tagToEmail = null;
        assertParseSuccess(parser, command, new EmailCommand(new Group(VALID_GROUP_AMY),
                tagToEmail, new Client(VALID_CLIENT), new Subject(Subject.TEST_SUBJECT_STRING),
                new Body(Body.TEST_BODY_STRING)));
    }

    @Test
    public void parse_clientMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE);
        String invalidCommand = GROUP_DESC_AMY;
        assertParseFailure(parser, invalidCommand, expectedMessage);
    }

    @Test
    public void parse_emptyCommand_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.COMMAND_USAGE);
        String invalidCommand = " ";
        assertParseFailure(parser, invalidCommand, expectedMessage);
    }
}
