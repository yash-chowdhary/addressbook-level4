package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.GROUP_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.GROUP_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.club.logic.commands.CommandTestUtil.MATRIC_NUMBER_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.club.logic.commands.RemoveGroupCommand;
import seedu.club.model.group.Group;

//@@author yash-chowdhary
public class RemoveGroupCommandParserTest {
    private RemoveGroupCommandParser parser = new RemoveGroupCommandParser();

    @Test
    public void parse_fieldPresent_success() {
        assertParseSuccess(parser, GROUP_DESC_BOB, new RemoveGroupCommand(new Group(VALID_GROUP_BOB)));
        assertParseSuccess(parser, GROUP_DESC_AMY, new RemoveGroupCommand(new Group(VALID_GROUP_AMY)));
    }

    @Test
    public void parse_incorrectField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveGroupCommand.MESSAGE_USAGE);
        assertParseFailure(parser, NAME_DESC_AMY, expectedMessage);
        assertParseFailure(parser, MATRIC_NUMBER_DESC_AMY, expectedMessage);
    }

    @Test
    public void parse_invalidGroupFormat_failure() {
        String expectedMessage = Group.MESSAGE_GROUP_CONSTRAINTS;
        assertParseFailure(parser, INVALID_GROUP_DESC, Group.MESSAGE_GROUP_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_GROUP.toString() + " ", expectedMessage);
    }

    @Test
    public void parse_fieldNotPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveGroupCommand.MESSAGE_USAGE);

        // blank space
        assertParseFailure(parser, " ", expectedMessage);
        // newline character
        assertParseFailure(parser, "\n", expectedMessage);
        // group should be preceded by group prefix 'g/'
        assertParseFailure(parser, VALID_GROUP_AMY, expectedMessage);
        assertParseFailure(parser, VALID_GROUP_BOB, expectedMessage);
    }
}
