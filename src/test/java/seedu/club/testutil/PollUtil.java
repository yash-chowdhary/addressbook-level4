package seedu.club.testutil;

import static seedu.club.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_QUESTION;

import seedu.club.logic.commands.AddPollCommand;
import seedu.club.model.poll.Poll;

/**
 * A utility class for poll.
 */
public class PollUtil {

    /**
     * Returns an addPoll command string for adding the {@code poll}.
     */
    public static String getAddPollCommand(Poll poll) {
        return AddPollCommand.COMMAND_WORD + " " + getPollDetails(poll);
    }

    /**
     * Returns the part of command string for the given {@code poll}'s details.
     */
    public static String getPollDetails(Poll poll) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_QUESTION + poll.getQuestion().getValue() + " ");
        poll.getAnswers().stream().forEach(
            answer -> sb.append(PREFIX_ANSWER + answer.getValue() + " ")
        );
        return sb.toString();
    }
}
