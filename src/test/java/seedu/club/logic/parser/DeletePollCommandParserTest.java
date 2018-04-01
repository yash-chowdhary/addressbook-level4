package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_POLL;

import org.junit.Test;

import seedu.club.logic.commands.DeletePollCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeletePollCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeletePollCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeletePollCommandParserTest {

    private DeletePollCommandParser parser = new DeletePollCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeletePollCommand(INDEX_FIRST_POLL));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePollCommand.MESSAGE_USAGE));
    }
}
