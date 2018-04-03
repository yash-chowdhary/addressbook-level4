package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.GROUP_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_MATRIC_NUMBER_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.club.logic.commands.CommandTestUtil.MATRIC_NUMBER_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.MATRIC_NUMBER_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.PASSWORD_DESC;
import static seedu.club.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.club.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.club.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.club.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.club.logic.commands.CommandTestUtil.USERNAME_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.USERNAME_DESC_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.club.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.AddCommand;
import seedu.club.logic.commands.VoteCommand;
import seedu.club.model.group.Group;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
import seedu.club.model.tag.Tag;
import seedu.club.testutil.MemberBuilder;

public class VoteCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, VoteCommand.MESSAGE_USAGE);

    private VoteCommandParser parser = new VoteCommandParser();

    @Test
    public void parse_twoIndicesPresent_success() {
        assertParseSuccess(parser,"1 2",
                new VoteCommand(Index.fromOneBased(1), Index.fromOneBased(2)));

        assertParseSuccess(parser,"2 99",
                new VoteCommand(Index.fromOneBased(2), Index.fromOneBased(99)));

        assertParseSuccess(parser,"10 1",
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
