# MuhdNurKamal
###### \java\seedu\club\commons\events\ui\CompressMembersRequestEvent.java
``` java
import seedu.club.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class CompressMembersRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\club\commons\events\ui\DecompressMembersRequestEvent.java
``` java
import seedu.club.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class DecompressMembersRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\club\commons\events\ui\HideResultsRequestEvent.java
``` java
import seedu.club.commons.events.BaseEvent;

/**
 * An event requesting to hide all poll results.
 */
public class HideResultsRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\club\commons\events\ui\ViewResultsRequestEvent.java
``` java
import seedu.club.commons.events.BaseEvent;

/**
 * An event requesting to view all poll results.
 */
public class ViewResultsRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\club\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case and finds partial match.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == true (partial match)
     *       containsWordIgnoreCase("ABc def", "ABCD") == false
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean partiallyContainsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        return sentence.toLowerCase().contains(preppedWord.toLowerCase());
    }
```
###### \java\seedu\club\logic\commands\AddPollCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.club.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_QUESTION;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.DuplicatePollException;

/**
 * Adds a poll to the club book.
 */
public class AddPollCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addpoll";
    public static final String COMMAND_FORMAT = "addpoll q/ ans/ [ans/...]";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "addp", "poll")
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a poll for members to respond to on Club Connect.\n"
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION + "When should the annual Appreciation Dinner be held? "
            + PREFIX_ANSWER + "April 13 "
            + PREFIX_ANSWER + "April 14 "
            + PREFIX_ANSWER + "April 21 ";

    public static final String MESSAGE_SUCCESS = "New poll added:\n%1$s";
    public static final String MESSAGE_DUPLICATE_POLL = "This poll already exists in Club Connect.";

    private final Poll toAdd;

    /**
     * Creates an AddPollCommand to add the specified {@code poll}
     */
    public AddPollCommand(Poll poll) {
        requireNonNull(poll);
        toAdd = poll;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        try {
            model.addPoll(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicatePollException e) {
            throw new CommandException(MESSAGE_DUPLICATE_POLL);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPollCommand // instanceof handles nulls
                && toAdd.equals(((AddPollCommand) other).toAdd));
    }
}
```
###### \java\seedu\club\logic\commands\CompressCommand.java
``` java
import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.CompressMembersRequestEvent;
import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Lists all members in the club book to the user.
 */
public class CompressCommand extends Command {

    public static final String COMMAND_WORD = "compress";
    public static final String MESSAGE_SUCCESS = "Member list view compressed.";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "comp")
    );


    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new CompressMembersRequestEvent());
        requireToSignUp();
        requireToLogIn();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\club\logic\commands\DecompressCommand.java
``` java
import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.DecompressMembersRequestEvent;
import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Lists all members in the club book to the user.
 */
public class DecompressCommand extends Command {

    public static final String COMMAND_WORD = "decompress";
    public static final String MESSAGE_SUCCESS = "Member list view decompressed.";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "decomp")
    );


    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new DecompressMembersRequestEvent());
        requireToSignUp();
        requireToLogIn();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\club\logic\commands\DeletePollCommand.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.PollNotFoundException;

/**
 * Deletes a poll identified using it's last displayed index from the club book.
 */
public class DeletePollCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletepoll";
    public static final String COMMAND_FORMAT = "deletepoll INDEX";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "delpoll", "rmpoll")
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the poll identified by the index number used in the last poll listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_POLL_SUCCESS = "Deleted poll: %1$s";

    private final Index targetIndex;

    private Poll pollToDelete;

    public DeletePollCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(pollToDelete);
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        try {
            model.deletePoll(pollToDelete);
        } catch (PollNotFoundException pnfe) {
            throw new AssertionError("The target poll cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_POLL_SUCCESS, pollToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        List<Poll> lastShownList = model.getFilteredPollList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLL_DISPLAYED_INDEX);
        }

        pollToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePollCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeletePollCommand) other).targetIndex) // state check
                && Objects.equals(this.pollToDelete, ((DeletePollCommand) other).pollToDelete));
    }
}
```
###### \java\seedu\club\logic\commands\HideResultsCommand.java
``` java
import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.HideResultsRequestEvent;
import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Hides all poll results in the club book to the user.
 */
public class HideResultsCommand extends Command {

    public static final String COMMAND_WORD = "hideresults";
    public static final String MESSAGE_SUCCESS = "Poll results have been hidden.";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "hideres")
    );


    @Override
    public CommandResult execute() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        EventsCenter.getInstance().post(new HideResultsRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\club\logic\commands\VoteCommand.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.AnswerNotFoundException;
import seedu.club.model.poll.exceptions.PollNotFoundException;
import seedu.club.model.poll.exceptions.UserAlreadyVotedException;

/**
 * Votes in a poll of an existing poll in the club book.
 */
public class VoteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "vote";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "vpoll")
    );
    public static final String COMMAND_FORMAT = "vote POLL_INDEX ANSWER_INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds your vote to the poll identified "
            + "by the index number used in the last poll listing. \n"
            + "Parameters: POLL_INDEX (must be a positive integer) ANSWER_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3 2";

    public static final String MESSAGE_VOTE_SUCCESS = "Your vote has been recorded.\n%s";
    public static final String MESSAGE_VOTE_FAIL_ALREADY_VOTED = "You have already voted in this poll.";

    private final Index pollIndex;
    private final Index answerIndex;

    private Poll pollToVoteIn;

    /**
     * @param pollIndex   of the poll in the filtered poll list to vote in
     * @param answerIndex of the answer of the poll in the filtered poll list to vote in
     */
    public VoteCommand(Index pollIndex, Index answerIndex) {
        requireNonNull(pollIndex);
        requireNonNull(answerIndex);
        this.pollIndex = pollIndex;
        this.answerIndex = answerIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        String voteDetails;
        try {
            voteDetails = model.voteInPoll(pollToVoteIn, answerIndex);
        } catch (UserAlreadyVotedException userAlreadyVotedException) {
            throw new CommandException(MESSAGE_VOTE_FAIL_ALREADY_VOTED);
        } catch (PollNotFoundException questionNotFoundException) {
            throw new AssertionError("The target poll cannot be missing");
        } catch (AnswerNotFoundException answerNotFoundException) {
            throw new AssertionError("The target answer cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_VOTE_SUCCESS, voteDetails));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        List<Poll> lastShownList = model.getFilteredPollList();

        if (pollIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLL_DISPLAYED_INDEX);
        }

        pollToVoteIn = lastShownList.get(pollIndex.getZeroBased());

        if (answerIndex.getZeroBased() >= pollToVoteIn.getAnswers().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ANSWER_DISPLAYED_INDEX);
        }
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
        return pollIndex.equals(e.pollIndex)
                && answerIndex.equals(e.answerIndex);
    }

}
```
###### \java\seedu\club\logic\parser\AddPollCommandParser.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_QUESTION;

import java.util.List;
import java.util.stream.Stream;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.AddPollCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.Question;

/**
 * Parses input arguments and creates a new AddPollCommand object
 */
public class AddPollCommandParser implements Parser<AddPollCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPollCommand
     * and returns an AddPollCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPollCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUESTION, PREFIX_ANSWER);

        if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_ANSWER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPollCommand.MESSAGE_USAGE));
        }

        try {
            Question question = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION)).get();
            List<Answer> answerList = ParserUtil.parseAnswers(argMultimap.getAllValues(PREFIX_ANSWER));

            Poll poll = new Poll(question, answerList);

            return new AddPollCommand(poll);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\club\logic\parser\DeletePollCommandParser.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.club.commons.core.index.Index;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.DeletePollCommand;
import seedu.club.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePollCommand object
 */
public class DeletePollCommandParser implements Parser<DeletePollCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePollCommand
     * and returns a DeletePollCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeletePollCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeletePollCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePollCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\club\logic\parser\FindCommandParser.java
``` java

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.club.logic.commands.FindCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.member.FieldContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Prefix[] FINDABLE_PREFIXES = {PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE,
        PREFIX_MATRIC_NUMBER, PREFIX_GROUP, PREFIX_TAG};

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        for (Prefix prefix : FINDABLE_PREFIXES) {
            int prefixLength = prefix.toString().length();
            if (trimmedArgs.length() >= prefixLength && trimmedArgs.substring(0, prefixLength)
                    .equals(prefix.toString())) {
                String[] findArgs = trimmedArgs.substring(prefixLength, trimmedArgs.length())
                        .trim().split("\\s+");
                return new FindCommand(new FieldContainsKeywordsPredicate(
                        Arrays.asList(findArgs), prefix));
            }
        }

        return new FindCommand(new FieldContainsKeywordsPredicate(
                Arrays.asList(trimmedArgs.split("\\s+")), null));
    }
}
```
###### \java\seedu\club\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code oneBasedIndex} into a list of {@code Index} and returns it. Leading and trailing
     * whitespaces will be trimmed.
     * @throws IllegalValueException if any of the specified index is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseIndices(String oneBasedIndexes) throws IllegalValueException {
        String trimmedIndexes = oneBasedIndexes.trim();
        String[] stringIndexes = trimmedIndexes.split("\\s+");
        List<Index> indexes = new ArrayList<>();
        for (String s : stringIndexes) {
            if (!StringUtil.isNonZeroUnsignedInteger(s)) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            } else {
                indexes.add(Index.fromOneBased((Integer.parseInt(s))));
            }
        }
        return indexes;
    }
```
###### \java\seedu\club\logic\parser\VoteCommandParser.java
``` java
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.club.commons.core.index.Index;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.VoteCommand;
import seedu.club.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new VoteCommand object
 */
public class VoteCommandParser implements Parser<VoteCommand> {

    private static final int INDEX_ARGUMENT_POLL = 0;
    private static final int INDEX_ARGUMENT_ANSWER = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the VoteCommand
     * and returns a VoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VoteCommand parse(String args) throws ParseException {
        try {
            List<Index> indexes = ParserUtil.parseIndices(args);
            if (indexes.size() != 2) {
                throw new IllegalValueException(MESSAGE_INVALID_COMMAND_FORMAT);
            }
            return new VoteCommand(indexes.get(INDEX_ARGUMENT_POLL), indexes.get(INDEX_ARGUMENT_ANSWER));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VoteCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\club\model\ClubBook.java
``` java
    /**
     * Removes {@code key} from this {@code ClubBook}.
     *
     * @throws PollNotFoundException if the {@code key} is not in this {@code ClubBook}.
     */
    public boolean removePoll(Poll key) throws PollNotFoundException {
        if (polls.remove(key)) {
            return true;
        } else {
            throw new PollNotFoundException();
        }
    }
```
###### \java\seedu\club\model\ClubBook.java
``` java
    public void setPolls(Set<Poll> polls) {
        this.polls.setPolls(polls);
    }

    public void addPoll(Poll poll) throws DuplicatePollException {
        polls.add(poll);
    }

    public String voteInPoll(Poll poll, Index answerIndex, MatricNumber polleeMatricNumber)
            throws PollNotFoundException, AnswerNotFoundException, UserAlreadyVotedException {
        return polls.voteInPoll(poll, answerIndex, polleeMatricNumber);
    }
```
###### \java\seedu\club\model\member\FieldContainsKeywordsPredicate.java
``` java
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.club.commons.util.StringUtil;
import seedu.club.logic.parser.Prefix;
import seedu.club.model.tag.Tag;

/**
 * Tests that a {@code member}'s matches any of the keywords given according to the fieldType given by {@code prefix}.
 */
public class FieldContainsKeywordsPredicate implements Predicate<Member> {

    private final List<String> keywords;
    private final Prefix prefix;

    public FieldContainsKeywordsPredicate(List<String> keywords, Prefix prefix) {
        this.keywords = keywords;
        this.prefix = prefix;
    }

    @Override
    public boolean test(Member member) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.partiallyContainsWordIgnoreCase(getFieldValue(member), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FieldContainsKeywordsPredicate)) {
            return false;
        }
        if (!(this.keywords.equals(((FieldContainsKeywordsPredicate) other).keywords))) {
            return false;
        }
        if (this.prefix == null && ((FieldContainsKeywordsPredicate) other).prefix == null) {
            return true;
        }
        if (this.prefix == null || ((FieldContainsKeywordsPredicate) other).prefix == null) {
            return false;
        }
        return this.prefix.equals(((FieldContainsKeywordsPredicate) other).prefix);
    }

    /**
     * Get relevant field value of member according to {@code prefix}
     *  or all field values if {@code prefix} is null     *
     */
    private String getFieldValue(Member member) {

        String name = member.getName().toString();
        String phone = member.getPhone().toString();
        String email = member.getEmail().toString();
        String matricNumber = member.getMatricNumber().toString();
        String group = member.getGroup().toString();
        String tags = member.getTags().stream().map(Tag::toString).collect(Collectors.joining(" "));

        if (PREFIX_NAME.equals(prefix)) {
            return name;
        } else if (PREFIX_PHONE.equals(prefix)) {
            return phone;
        } else if (PREFIX_EMAIL.equals(prefix)) {
            return email;
        } else if (PREFIX_MATRIC_NUMBER.equals(prefix)) {
            return matricNumber;
        } else if (PREFIX_GROUP.equals(prefix)) {
            return group;
        } else if (PREFIX_TAG.equals(prefix)) {
            return tags;
        } else {
            return name + " " + phone + " " + email + " " + matricNumber + " " + group + " " + tags;
        }
    }
}
```
###### \java\seedu\club\model\member\MatricNumber.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

/**
 * Represents a member's matric number in the club book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatricNumber(String)}
 */
public class MatricNumber {

    public static final String MESSAGE_MATRIC_NUMBER_CONSTRAINTS =
            "Member matric number must be non-empty, begin with a letter, have 7 digits in the middle, "
                    + "and end with a letter";

    public static final String MATRIC_NUMBER_VALIDATION_REGEX = "^[aA]\\d{7}[a-zA-Z]$";

    public final String value;

    /**
     * Constructs a {@code MatricNumber}.
     *
     * @param matricNumber A valid matric number.
     */
    public MatricNumber(String matricNumber) {
        requireNonNull(matricNumber);
        checkArgument(isValidMatricNumber(matricNumber), MESSAGE_MATRIC_NUMBER_CONSTRAINTS);
        this.value = matricNumber.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid member email.
     */
    public static boolean isValidMatricNumber(String test) {
        return test.matches(MATRIC_NUMBER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatricNumber // instanceof handles nulls
                && this.value.equals(((MatricNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\club\model\ModelManager.java
``` java
    @Override
    public synchronized void addPoll(Poll poll) throws DuplicatePollException {
        requireNonNull(poll);
        clubBook.addPoll(poll);
        updateFilteredPollList(new PollIsRelevantToMemberPredicate(getLoggedInMember()));
        indicateClubBookChanged();
    }

    @Override
    public String voteInPoll(Poll poll, Index answerIndex)
            throws PollNotFoundException, AnswerNotFoundException, UserAlreadyVotedException {
        requireAllNonNull(poll, answerIndex);
        String voteDetails = clubBook.voteInPoll(poll, answerIndex, getLoggedInMember().getMatricNumber());
        indicateClubBookChanged();
        return voteDetails;
    }

    @Override
    public synchronized void deletePoll(Poll target) throws PollNotFoundException {
        requireNonNull(target);
        clubBook.removePoll(target);
        indicateClubBookChanged();
    }

```
###### \java\seedu\club\model\poll\Answer.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

/**
 * Represents an answer to a question of a poll
 */
public class Answer {

    public static final String MESSAGE_ANSWER_CONSTRAINTS = "Answer cannot be empty";
    public static final String MESSAGE_ANSWER_NUMBER_ANSWERED_CONSTRAINTS =
            "Number answered for an answer should be non-negative";
    public static final String ANSWER_VALIDATION_REGEX = ".*\\S.*";
    public static final String ANSWER_LABEL = "Ans: ";
    public static final int NUMBER_ZERO_VOTE_COUNT = 0;

    private String value;
    private int voteCount;

    public Answer(String value) {
        this(value, NUMBER_ZERO_VOTE_COUNT);
    }

    public Answer(String value, int voteCount) {
        requireNonNull(value);
        checkArgument(isValidAnswer(value), MESSAGE_ANSWER_CONSTRAINTS);
        this.value = value;
        this.voteCount = voteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getValue() {
        return value;
    }

    public void voteThisAnswer() {
        voteCount++;
    }

    public static boolean isValidAnswer(String value) {
        return value.matches(ANSWER_VALIDATION_REGEX);
    }

    public static boolean isValidNoOfMembersAnswered(int noOfMembersAnswered) {
        return noOfMembersAnswered >= NUMBER_ZERO_VOTE_COUNT;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Answer // instanceof handles nulls
                && this.value.equals(((Answer) other).value) // state check
                && this.voteCount == ((Answer) other).voteCount); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return ANSWER_LABEL + value;
    }
}
```
###### \java\seedu\club\model\poll\exceptions\AnswerNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified answer.
 */
public class AnswerNotFoundException extends Exception {}
```
###### \java\seedu\club\model\poll\exceptions\PollNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified poll.
 */
public class PollNotFoundException extends Exception {}
```
###### \java\seedu\club\model\poll\exceptions\UserAlreadyVotedException.java
``` java
/**
 * Signals that the current logged in member has already voted for the poll.
 */
public class UserAlreadyVotedException extends Exception {}
```
###### \java\seedu\club\model\poll\Poll.java
``` java
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.club.commons.core.index.Index;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.poll.exceptions.AnswerNotFoundException;
import seedu.club.model.poll.exceptions.UserAlreadyVotedException;

/**
 * Represents a Poll in the club book.
 */
public class Poll {

    private final Question question;
    private ObservableList<Answer> answers;
    private Set<MatricNumber> polleesMatricNumbers;

    /**
     * Constructs a {@code Poll}.
     */
    public Poll(Question question, List<Answer> answers) {
        this(question, answers, null);
    }

    public Poll(Question question, List<Answer> answers,
                Set<MatricNumber> polleesMatricNumbers) {
        requireNonNull(question);
        requireNonNull(answers);
        this.question = question;
        setAnswers(answers);
        setPolleesMatricNumbers(polleesMatricNumbers);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Poll // instanceof handles nulls
                && this.question.equals(((Poll) other).question) // state check
                && this.polleesMatricNumbers.equals(((Poll) other).polleesMatricNumbers)
                && this.answers.equals(((Poll) other).answers));
    }

    public Question getQuestion() {
        return question;
    }

    public int getTotalVoteCount() {
        return answers.stream().map(Answer::getVoteCount).reduce(0, Integer::sum);
    }

    /**
     * Returns an immutable answer list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public ObservableList<Answer> getAnswers() {
        return FXCollections.unmodifiableObservableList(answers);
    }

    private void setAnswers(List<Answer> answers) {
        assert answers != null && !answers.isEmpty();
        List<Answer> clonedAnswers = new ArrayList<>();
        for (Answer answer : answers) {
            clonedAnswers.add(new Answer(answer.getValue(), answer.getVoteCount()));
        }
        this.answers = FXCollections.observableArrayList(clonedAnswers);
    }

    /**
     * Returns an immutable poll set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<MatricNumber> getPolleesMatricNumbers() {
        return Collections.unmodifiableSet(polleesMatricNumbers);
    }

    private void setPolleesMatricNumbers(Set<MatricNumber> polleesMatricNumbers) {
        this.polleesMatricNumbers = new HashSet<>();
        if (polleesMatricNumbers != null) {
            for (MatricNumber matricNumber : polleesMatricNumbers) {
                this.polleesMatricNumbers.add(new MatricNumber(matricNumber.toString()));
            }
        }
    }

    /**
     * Increases vote count of the answer specified by answerIndex.
     * Pollee of the vote is specified by polleeMatricNumber.
     *
     * @param answerIndex        index of the answer of this poll to be voted for
     * @param polleeMatricNumber matricNumber of the pollee that is voting for the answer
     * @throws AnswerNotFoundException   if answerIndex is not answerIndex of any answers of this poll
     * @throws UserAlreadyVotedException if pollee has already voted in the poll
     */
    public String vote(Index answerIndex, MatricNumber polleeMatricNumber) throws
            AnswerNotFoundException, UserAlreadyVotedException {
        Answer answer;
        if (polleesMatricNumbers.contains(polleeMatricNumber)) {
            throw new UserAlreadyVotedException();
        } else {
            try {
                answer = answers.get(answerIndex.getZeroBased());
                answer.voteThisAnswer();

            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new AnswerNotFoundException();
            }
            polleesMatricNumbers.add(polleeMatricNumber);
            return this.question + "\n" + answer;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answers);
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return question + "\n"
                + answers.stream().map(Answer::toString).collect(Collectors.joining("\n"));
    }
}
```
###### \java\seedu\club\model\poll\PollIsRelevantToMemberPredicate.java
``` java
import static seedu.club.model.group.Group.GROUP_EXCO;

import java.util.function.Predicate;

import seedu.club.model.member.Member;

/**
 * Tests that a {@code poll} is relevant to to {@code member}
 * If member is null, no polls will be shown
 * If member is an exco, all polls are relevant else only polls that have not been answered by member will be shown
 */
public class PollIsRelevantToMemberPredicate implements Predicate<Poll> {

    private final Member member;

    public PollIsRelevantToMemberPredicate(Member member) {
        this.member = member;
    }

    @Override
    public boolean test(Poll poll) {
        if (member == null) {
            return false;
        } else if (member.getGroup().toString().equalsIgnoreCase(GROUP_EXCO)) {
            return true;
        } else {
            return !poll.getPolleesMatricNumbers().contains(member.getMatricNumber());
        }
    }

    public Member getMember() {
        return member;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this)  // short circuit if same object
                || (other instanceof PollIsRelevantToMemberPredicate     // handles nulls
                && this.member.equals(((PollIsRelevantToMemberPredicate) other).getMember()));   // state check
    }
}
```
###### \java\seedu\club\model\poll\Question.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.AppUtil.checkArgument;

/**
 * Represents an question of a poll
 */
public class Question {

    public static final String MESSAGE_QUESTION_CONSTRAINTS = "You need a question for the poll.";
    public static final String QUESTION_VALIDATION_REGEX = ".*\\S.*";
    public static final String QUESTION_LABEL = "Question: ";

    private String value;

    public Question(String value) {
        requireNonNull(value);
        checkArgument(isValidQuestion(value), MESSAGE_QUESTION_CONSTRAINTS);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValidQuestion(String test) {
        return test.matches(QUESTION_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Question // instanceof handles nulls
                && this.value.equals(((Question) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return QUESTION_LABEL + value;
    }
}
```
###### \java\seedu\club\model\poll\UniquePollList.java
``` java
    /**
     * Replaces the specified {@code poll} with a deep copy except that the copy has the {@code answer}
     * specified by {@code answerIndex} increased it's vote count by 1 and the {@code polleeMatricNumber} will be added
     * to the {@code polleesMatricNumber} of the {@code poll}
     *
     * @param poll to be copied
     * @param answerIndex of the answer of the poll to be voted for
     * @param polleeMatricNumber of pollee who wants to vote for the answer of the poll
     *
     * @throws PollNotFoundException if poll is not in this list
     * @throws AnswerNotFoundException if answerIndex is not answerIndex of any answers of poll
     * @throws UserAlreadyVotedException if pollee has already voted in the poll
     */
    public String voteInPoll(Poll poll, Index answerIndex, MatricNumber polleeMatricNumber)
            throws PollNotFoundException, AnswerNotFoundException, UserAlreadyVotedException {
        int pollIndex = internalList.indexOf(poll);
        String voteDetails;
        if (pollIndex == -1) {
            throw new PollNotFoundException();
        } else {
            Poll pollDeepCopy = new Poll(new Question(poll.getQuestion().getValue()),
                    poll.getAnswers(), poll.getPolleesMatricNumbers());
            voteDetails = pollDeepCopy.vote(answerIndex, polleeMatricNumber);
            internalList.set(pollIndex, pollDeepCopy);
        }
        return voteDetails;
    }
```
###### \java\seedu\club\storage\XmlAdaptedAnswer.java
``` java
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.poll.Answer;

/**
 * JAXB-friendly adapted version of the Answer.
 */
public class XmlAdaptedAnswer {

    @XmlValue
    private String value;
    @XmlAttribute
    private int noOfMembersAnswered;

    /**
     * Constructs an XmlAdaptedAnswer.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAnswer() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName}.
     */
    public XmlAdaptedAnswer(String value, int noOfMembersAnswered) {
        this.value = value;
        this.noOfMembersAnswered = noOfMembersAnswered;
    }

    /**
     * Converts a given Answer into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAnswer(Answer source) {
        value = source.getValue();
        noOfMembersAnswered = source.getVoteCount();
    }

    /**
     * Converts this jaxb-friendly adapted Answer object into the model's Answer object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted member
     */
    public Answer toModelType() throws IllegalValueException {
        if (!Answer.isValidAnswer(value)) {
            throw new IllegalValueException(Answer.MESSAGE_ANSWER_CONSTRAINTS);
        }
        if (!Answer.isValidNoOfMembersAnswered(noOfMembersAnswered)) {
            throw new IllegalValueException(Answer.MESSAGE_ANSWER_NUMBER_ANSWERED_CONSTRAINTS);
        }
        return new Answer(value, noOfMembersAnswered);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAnswer)) {
            return false;
        }

        return value.equals(((XmlAdaptedAnswer) other).value)
                && noOfMembersAnswered == ((XmlAdaptedAnswer) other).noOfMembersAnswered;
    }
}
```
###### \java\seedu\club\storage\XmlAdaptedMatricNumber.java
``` java
import java.util.Objects;

import javax.xml.bind.annotation.XmlValue;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.member.MatricNumber;

/**
 * JAXB-friendly version of the member.
 */
public class XmlAdaptedMatricNumber {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Matric Number missing";
    @XmlValue
    private String matricNumber;

    /**
     * Constructs an XmlAdaptedMember.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMatricNumber() {}

    /**
     * Constructs an {@code XmlAdaptedMember} with the given member details.
     */

    public XmlAdaptedMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    /**
     * Converts a given MatricNumber into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMatricNumber
     */
    public XmlAdaptedMatricNumber(MatricNumber source) {
        matricNumber = source.toString();
    }

    /**
     * Converts this jaxb-friendly adapted matricNumber object into the model's matricNumber object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted member
     */
    public MatricNumber toModelType() throws IllegalValueException {
        if (this.matricNumber == null) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }
        if (!MatricNumber.isValidMatricNumber(this.matricNumber)) {
            throw new IllegalValueException(MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS);
        }
        return new MatricNumber(matricNumber);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMatricNumber)) {
            return false;
        }

        XmlAdaptedMatricNumber otherMatricNumber = (XmlAdaptedMatricNumber) other;
        return Objects.equals(matricNumber, otherMatricNumber.matricNumber);
    }
}
```
###### \java\seedu\club\storage\XmlAdaptedPoll.java
``` java
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.Question;

/**
 * JAXB-friendly version of the member.
 */
public class XmlAdaptedPoll {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "member's %s field is missing!";

    @XmlElement(required = true)
    private String question;
    @XmlElement(required = true)
    private List<XmlAdaptedAnswer> answers = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedMatricNumber> polleesMatricNumbers = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedMember.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPoll() {}

    /**
     * Constructs an {@code XmlAdaptedMember} with the given member details.
     */

    public XmlAdaptedPoll(String question, List<XmlAdaptedAnswer> answers,
                          List<XmlAdaptedMatricNumber> polleesMatricNumbers) {
        this.question = question;
        if (polleesMatricNumbers != null) {
            this.polleesMatricNumbers = new ArrayList<XmlAdaptedMatricNumber>(polleesMatricNumbers);
        }
        if (answers != null) {
            this.answers = new ArrayList<>(answers);
        }
    }

    /**
     * Converts a given Poll into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPoll
     */
    public XmlAdaptedPoll(Poll source) {
        question = source.getQuestion().getValue();
        answers = new ArrayList<>();
        for (Answer answer : source.getAnswers()) {
            answers.add(new XmlAdaptedAnswer(answer));
        }
        polleesMatricNumbers = new ArrayList<>();
        for (MatricNumber polleeMatricNumber : source.getPolleesMatricNumbers()) {
            polleesMatricNumbers.add(new XmlAdaptedMatricNumber(polleeMatricNumber));
        }
    }

    /**
     * Converts this jaxb-friendly adapted poll object into the model's poll object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted member
     */
    public Poll toModelType() throws IllegalValueException {
        if (this.answers == null || answers.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Answer.class.getSimpleName()));
        }

        final List<Answer> answersToReturn = new ArrayList<>();
        for (XmlAdaptedAnswer answer : answers) {
            answersToReturn.add(answer.toModelType());
        }

        if (this.polleesMatricNumbers == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MatricNumber.class.getSimpleName()));
        }

        final Set<MatricNumber> polleesMatricNumbersToReturn = new HashSet<>();
        for (XmlAdaptedMatricNumber matricNumber : polleesMatricNumbers) {
            polleesMatricNumbersToReturn.add(matricNumber.toModelType());
        }

        if (this.question == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Question.class.getSimpleName()));
        }
        if (!Question.isValidQuestion(question)) {
            throw new IllegalValueException(Question.MESSAGE_QUESTION_CONSTRAINTS);
        }
        final Question questionToReturn = new Question(this.question);

        Poll poll = new Poll (questionToReturn, answersToReturn, polleesMatricNumbersToReturn);
        return poll;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPoll)) {
            return false;
        }

        XmlAdaptedPoll otherPoll = (XmlAdaptedPoll) other;
        return Objects.equals(question, otherPoll.question)
                && Objects.equals(answers, otherPoll.answers)
                && Objects.equals(polleesMatricNumbers, otherPoll.polleesMatricNumbers);
    }
}
```
###### \java\seedu\club\ui\AnswerCard.java
``` java
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import seedu.club.model.poll.Answer;

/**
 * A UI component that displays information of an {@code answer}.
 */
public class AnswerCard extends UiPart<Region> {

    private static final String FXML = "AnswerListCard.fxml";
    private static final String DESCRIPTION_VOTE_COUNT = "Votes: ";
    private static final String PERCENTAGE_SYMBOL = "%";
    private final int totalVoteCount;

    @FXML
    private Label answerValue;

    @FXML
    private Label choice;

    @FXML
    private Label voteCount;

    @FXML
    private ProgressBar votePercentageBar;

    @FXML
    private Label votePercentage;

    /**
     * A constructor to initialize AnswerCard using {@value FXML} with results
     */
    public AnswerCard(Answer answer, int displayedIndex, int totalVoteCount) {
        super(FXML);
        this.totalVoteCount = totalVoteCount;
        choice.setText(displayedIndex + ". ");
        answerValue.setText(answer.getValue());
        voteCount.setText(DESCRIPTION_VOTE_COUNT + answer.getVoteCount());
        setVotePercentage(answer);
    }

    /**
     * A constructor to initialize AnswerCard using without results
     *
     * @param fxml file configure layout of this AnswerCard
     */
    public AnswerCard(Answer answer, int displayedIndex, int totalVoteCount, String fxml) {
        super(fxml);
        this.totalVoteCount = totalVoteCount;
        choice.setText(displayedIndex + ". ");
        answerValue.setText(answer.getValue());
    }

    private void setVotePercentage(Answer answer) {
        int voteCount = answer.getVoteCount();
        double voteFraction = totalVoteCount == 0
                ? 0 : ((double) voteCount) / totalVoteCount;
        votePercentageBar.setProgress(voteFraction);
        votePercentage.setText((Math.round(voteFraction * 1000)) / 10 + PERCENTAGE_SYMBOL);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnswerCard)) {
            return false;
        }

        // state check
        AnswerCard card = (AnswerCard) other;
        return choice.getText().equals(card.choice.getText())
                && answerValue.equals(card.answerValue);
    }
}
```
###### \java\seedu\club\ui\AnswerListPanel.java
``` java
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.club.commons.core.LogsCenter;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;

/**
 * Panel containing the list of answers.
 */
public class AnswerListPanel extends UiPart<Region> {
    private static final String FXML = "AnswerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AnswerListPanel.class);
    private final Poll poll;
    private boolean isShowingResults;

    @FXML
    private VBox answersPlaceholder;

    public AnswerListPanel(ObservableList<Answer> answerList, Poll poll, boolean isShowingResults) {
        super(FXML);
        this.poll = poll;
        this.isShowingResults = isShowingResults;
        setAnswersPlaceholder(answerList);
    }

    private void setAnswersPlaceholder(ObservableList<Answer> answerList) {
        int totalVoteCount = poll.getTotalVoteCount();
        ObservableList<Node> children = answersPlaceholder.getChildren();
        if (isShowingResults) {
            for (int index = 0; index < answerList.size(); index++) {
                children.add(new AnswerCard(answerList.get(index), index + 1, totalVoteCount).getRoot());
            }
        } else {
            for (int index = 0; index < answerList.size(); index++) {
                children.add(new RestrictedAnswerCard(answerList.get(index), index + 1, totalVoteCount).getRoot());
            }
        }
    }
}
```
###### \java\seedu\club\ui\CompressedMemberCard.java
``` java
import seedu.club.model.member.Member;

/**
 * An UI component that displays compressed information of a {@code member}.
 */
public class CompressedMemberCard extends MemberCard {

    private static final String FXML = "CompressedMemberListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public CompressedMemberCard(Member member, int displayedIndex) {
        super(member, displayedIndex, FXML);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompressedMemberCard)) {
            return false;
        }

        // state check
        CompressedMemberCard card = (CompressedMemberCard) other;
        return getId().getText().equals(getId().getText())
                && member.equals(card.member);
    }
}
```
###### \java\seedu\club\ui\MemberCard.java
``` java
    /**
     * A constructor to initialize MemberCard using without matricNumber
     *
     * @param fxml file configure layout of this MemberCard
     */
    public MemberCard(Member member, int displayedIndex, String fxml) {
        super(fxml);
        this.member = member;
        id.setText(displayedIndex + ". ");
        name.setText(member.getName().fullName);
        phone.setText(member.getPhone().value);
        group.setText(member.getGroup().groupName);
        email.setText(member.getEmail().value);
        setProfilePhoto(member);
    }

```
###### \java\seedu\club\ui\MemberListPanel.java
``` java
    /**
     * Compresses view of member details.
     */
    private void compressMemberCards() {
        if (!isDisplayingCompressedMembers) {
            isDisplayingCompressedMembers = true;
            setMemberListView(memberList);
        }
    }

    /**
     * Decompresses view of member details.
     */
    private void decompressMemberCards() {
        if (isDisplayingCompressedMembers) {
            isDisplayingCompressedMembers = false;
            setMemberListView(memberList);
        }
    }
```
###### \java\seedu\club\ui\MemberListPanel.java
``` java
    @Subscribe
    private void handleCompressMembersEvent(CompressMembersRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        compressMemberCards();
    }

    @Subscribe
    private void handledeCompressMembersEvent(DecompressMembersRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        decompressMemberCards();
    }

    public boolean isDisplayingCompressedMembers() {
        return isDisplayingCompressedMembers;
    }
```
###### \java\seedu\club\ui\PollCard.java
``` java
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.club.model.poll.Poll;

/**
 * An UI component that displays information of a {@code poll}.
 */
public class PollCard extends UiPart<Region> {

    private static final String FXML = "PollListCard.fxml";
    private static final String DESCRIPTION_TOTAL_VOTE_COUNT = "Total Votes: ";

    public final Poll poll;

    private AnswerListPanel answerListPanel;

    @FXML
    private HBox cardPane;

    @FXML
    private Label id;

    @FXML
    private Label question;

    @FXML
    private StackPane answerListPanelPlaceholder;

    @FXML
    private Label totalVoteCount;

    /**
     * A constructor to initialize PollCard using {@value FXML} with results
     */
    public PollCard(Poll poll, int displayedIndex) {
        super(FXML);
        this.poll = poll;
        id.setText(displayedIndex + ". ");
        question.setText(poll.getQuestion().getValue());

        answerListPanel = new AnswerListPanel(poll.getAnswers(), poll, true);
        answerListPanelPlaceholder.getChildren().add(answerListPanel.getRoot());

        totalVoteCount.setText(DESCRIPTION_TOTAL_VOTE_COUNT + poll.getTotalVoteCount());
    }

    /**
     * A constructor to initialize PollCard using {@param fxml} without results
     */
    public PollCard(Poll poll, int displayedIndex, String fxml) {
        super(fxml);
        this.poll = poll;
        id.setText(displayedIndex + ". ");
        question.setText(poll.getQuestion().getValue());

        answerListPanel = new AnswerListPanel(poll.getAnswers(), poll, false);
        answerListPanelPlaceholder.getChildren().add(answerListPanel.getRoot());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PollCard)) {
            return false;
        }

        // state check
        PollCard card = (PollCard) other;
        return id.getText().equals(card.id.getText())
                && poll.equals(card.poll);
    }
}
```
###### \java\seedu\club\ui\PollListPanel.java
``` java
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.events.ui.HideResultsRequestEvent;
import seedu.club.commons.events.ui.ViewResultsRequestEvent;
import seedu.club.model.poll.Poll;

/**
 * Panel containing the list of polls.
 */
public class PollListPanel extends UiPart<Region> {
    private static final String FXML = "PollListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PollListPanel.class);
    private boolean isShowingPollResults;
    private ObservableList<Poll> pollList;

    @FXML
    private ListView<PollCard> pollListView;

    public PollListPanel(ObservableList<Poll> pollList) {
        super(FXML);
        this.pollList = pollList;
        setPollListView();
        registerAsAnEventHandler(this);
    }

    private void setPollListView() {
        ObservableList<PollCard> mappedList = EasyBind.map(
                pollList, (poll) -> {
                if (isShowingPollResults) {
                    return new PollCard(poll, pollList.indexOf(poll) + 1);
                } else {
                    return new RestrictedPollCard(poll, pollList.indexOf(poll) + 1);
                }
            });
        pollListView.setItems(mappedList);
        pollListView.setCellFactory(listView -> new PollListViewCell());
    }

    /**
     * Shows results of polls
     */
    private void showPollResults() {
        if (!isShowingPollResults) {
            isShowingPollResults = true;
            setPollListView();
        }
    }

    /**
     * Hides results of polls
     */
    private void hidePollResults() {
        if (isShowingPollResults) {
            isShowingPollResults = false;
            setPollListView();
        }
    }

    @Subscribe
    private void handleViewResultsEvent(ViewResultsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPollResults();
    }

    @Subscribe
    private void handleHideResultsEvent(HideResultsRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        hidePollResults();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PollCard}.
     */
    class PollListViewCell extends ListCell<PollCard> {

        @Override
        protected void updateItem(PollCard poll, boolean empty) {
            super.updateItem(poll, empty);

            if (empty || poll == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(poll.getRoot());
            }
        }
    }
}
```
###### \java\seedu\club\ui\RestrictedAnswerCard.java
``` java
import seedu.club.model.poll.Answer;

/**
 * An UI component that displays information of a {@code answerValue} except the results.
 */
public class RestrictedAnswerCard extends AnswerCard {

    private static final String FXML = "RestrictedAnswerListCard.fxml";

    public RestrictedAnswerCard(Answer answer, int displayedIndex, int totalVoteCount) {
        super(answer, displayedIndex, totalVoteCount, FXML);
    }
}
```
###### \java\seedu\club\ui\RestrictedPollCard.java
``` java
import seedu.club.model.poll.Poll;

/**
 * An UI component that displays information of a {@code poll} excluding the results.
 */
public class RestrictedPollCard extends PollCard {

    private static final String FXML = "RestrictedPollListCard.fxml";

    public RestrictedPollCard(Poll poll, int displayedIndex) {
        super(poll, displayedIndex, FXML);
    }
}
```
###### \resources\view\AnswerListCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ProgressBar?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<HBox xmlns:fx="http://javafx.com/fxml/1" id="cardPane" fx:id="cardPane" maxHeight="1.7976931348623157E308"
      xmlns="http://javafx.com/javafx/8.0.121">

    <GridPane maxHeight="1.7976931348623157E308" HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150"/>
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" maxHeight="1.7976931348623157E308"
              prefWidth="213.0" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="2"/>
            </padding>
            <HBox maxHeight="1.7976931348623157E308" prefWidth="135.0" spacing="5" VBox.vgrow="ALWAYS">
                <Label fx:id="choice" alignment="TOP_LEFT" maxHeight="1.7976931348623157E308"
                       styleClass="cell_big_label" wrapText="true">
                    <minWidth>
                        <Region fx:constant="USE_PREF_SIZE"/>
                    </minWidth>
                </Label>
                <Label fx:id="answerValue" alignment="TOP_LEFT" maxHeight="1.7976931348623157E308"
                       styleClass="cell_big_label" wrapText="true"/>
            </HBox>
            <VBox maxHeight="1.7976931348623157E308">
                <children>
                    <Label fx:id="voteCount" maxHeight="1.7976931348623157E308" styleClass="cell_small_label">
                        <minWidth>
                            <Region fx:constant="USE_PREF_SIZE"/>
                        </minWidth>
                    </Label>
                    <HBox>
                        <children>
                            <ProgressBar fx:id="votePercentageBar" maxWidth="160.0" prefHeight="18.0" prefWidth="160.0"
                                         progress="0.0">
                                <HBox.margin>
                                    <Insets/>
                                </HBox.margin>
                            </ProgressBar>
                            <Label fx:id="votePercentage" alignment="TOP_LEFT" styleClass="cell_small_label">
                                <minWidth>
                                    <Region fx:constant="USE_PREF_SIZE"/>
                                </minWidth>
                                <HBox.margin>
                                    <Insets left="20.0"/>
                                </HBox.margin>
                            </Label>
                        </children>
                    </HBox>
                </children>
            </VBox>
        </VBox>
        <rowConstraints>
            <RowConstraints/>
        </rowConstraints>
    </GridPane>
</HBox>
```
###### \resources\view\RestrictedAnswerListCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" maxHeight="1.7976931348623157E308" minWidth="0.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane maxHeight="1.7976931348623157E308" HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" maxHeight="1.7976931348623157E308" minHeight="33.0" prefWidth="213.0" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox maxHeight="1.7976931348623157E308" prefWidth="135.0" spacing="5" VBox.vgrow="ALWAYS">
                <Label fx:id="choice" alignment="TOP_LEFT" maxHeight="1.7976931348623157E308" styleClass="cell_big_label">
                    <minWidth>
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="answerValue" alignment="TOP_LEFT" maxHeight="1.7976931348623157E308" styleClass="cell_big_label" wrapText="true" />
            </HBox>
        </VBox>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
</HBox>
```
