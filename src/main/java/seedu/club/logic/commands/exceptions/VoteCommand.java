package seedu.club.logic.commands.exceptions;

import static java.util.Objects.requireNonNull;
import static seedu.club.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static seedu.club.model.Model.PREDICATE_SHOW_ALL_POLLS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.commons.util.CollectionUtil;
import seedu.club.logic.commands.CommandResult;
import seedu.club.logic.commands.UndoableCommand;
import seedu.club.model.group.Group;
import seedu.club.model.member.Credentials;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
import seedu.club.model.member.ProfilePhoto;
import seedu.club.model.member.exceptions.DuplicateMemberException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.poll.Poll;
import seedu.club.model.tag.Tag;

/**
 * Votes in a poll of an existing poll in the club book.
 */
public class VoteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "vote";
    public static final String COMMAND_FORMAT = "edit POLL_INDEX ANWER_INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Votes in the poll identified "
            + "by the index number used in the last poll listing. \n"
            + "Parameters: POLL_INDEX (must be a positive integer) QUESTION_INDEX (must be a positive integer)";

    public static final String MESSAGE_VOTE_SUCCESS = "Your vote has been received";
    public static final String MESSAGE_VOTE_FAIL_NO_QUESTION = "No such question";
    public static final String MESSAGE_VOTE_FAIL_NO_ANSWER = "No such answer";
    public static final String MESSAGE_VOTE_FAIL_ALREADY_VOTED = "You have already voted in this poll";

    private final Index index;

    private Poll pollToVoteIn;
    private Poll votedPoll;

    /**
     * @param index of the poll in the filtered poll list to vote in
     */
    public VoteCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateMember(pollToVoteIn, votedPoll);
        } catch (QuestionNotFoundException questionNotFoundException) {
            throw new CommandException(MESSAGE_VOTE_FAIL_NO_QUESTION);
        } catch (AnswerNotFoundException answerNotFoundException) {
            throw new CommandException(MESSAGE_VOTE_FAIL_NO_ANSWER);
        } catch (UserAlreadyVotedException userAlreadyVotedException) {
            throw new CommandException(MESSAGE_VOTE_FAIL_ALREADY_VOTED);
        }
        model.updateFilteredPollList(PREDICATE_SHOW_ALL_POLLS);
        return new CommandResult(String.format(MESSAGE_VOTE_SUCCESS));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Poll> lastShownList = model.getFilteredPollList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLL_DISPLAYED_INDEX);
        }

        pollToVoteIn = lastShownList.get(index.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VoteCommand)) {
            return false;
        }

        // state check
        VoteCommand e = (VoteCommand) other;
        return index.equals(e.index)
                && Objects.equals(pollToVoteIn, e.pollToVoteIn);
    }

}
