package seedu.club.logic.parser;
//@@author MuhdNurKamal
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.VoteCommand;

public class VoteCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, VoteCommand.MESSAGE_USAGE);

    private VoteCommandParser parser = new VoteCommandParser();

    @Test
    public void parse_twoIndicesPresent_success() {
        assertParseSuccess(parser, "1 2",
                new VoteCommand(Index.fromOneBased(1), Index.fromOneBased(2)));

        assertParseSuccess(parser, "2 99",
                new VoteCommand(Index.fromOneBased(2), Index.fromOneBased(99)));

        assertParseSuccess(parser, "10 1",
                new VoteCommand(Index.fromOneBased(10), Index.fromOneBased(1)));
    }

    @Test
    public void parse_negativeIndices_failure() {
        assertParseFailure(parser, "-1 2", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "1 -2", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "-1 -2", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_oneIndexMissing_failure() {
        assertParseFailure(parser, "2", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "1 ", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "  8  ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_moreThanTwoIndices_failure() {
        assertParseFailure(parser, "2 2  22  11 1", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "2  3   1", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "2 1 11", MESSAGE_INVALID_FORMAT);
    }
}
