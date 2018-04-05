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
###### \java\seedu\club\logic\commands\VoteCommand.java
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

    public static final String MESSAGE_VOTE_SUCCESS = "Your vote has been received";
    public static final String MESSAGE_VOTE_FAIL_ALREADY_VOTED = "You have already voted in this poll";

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
        try {
            model.voteInPoll(pollToVoteIn, answerIndex);
        } catch (UserAlreadyVotedException userAlreadyVotedException) {
            throw new CommandException(MESSAGE_VOTE_FAIL_ALREADY_VOTED);
        } catch (PollNotFoundException questionNotFoundException) {
            throw new AssertionError("The target poll cannot be missing");
        } catch (AnswerNotFoundException answerNotFoundException) {
            throw new AssertionError("The target answer cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_VOTE_SUCCESS));
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
                && answerIndex.equals(e.answerIndex)
                && Objects.equals(pollToVoteIn, e.pollToVoteIn);
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
        String[] stringIndexes = trimmedIndexes.split(" ");
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

    public void voteInPoll(Poll poll, Index answerIndex, MatricNumber polleeMatricNumber)
            throws PollNotFoundException, AnswerNotFoundException, UserAlreadyVotedException {
        polls.voteInPoll(poll, answerIndex, polleeMatricNumber);
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
        if (!(other instanceof  FieldContainsKeywordsPredicate)) {
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
###### \java\seedu\club\model\ModelManager.java
``` java
    @Override
    public synchronized void addPoll(Poll poll) throws DuplicatePollException {
        clubBook.addPoll(poll);
        updateFilteredPollList(new PollIsRelevantToMemberPredicate(getLoggedInMember()));
        indicateClubBookChanged();
    }

    @Override
    public void voteInPoll(Poll poll, Index answerIndex)
            throws PollNotFoundException, AnswerNotFoundException, UserAlreadyVotedException {
        requireAllNonNull(poll, answerIndex);

        clubBook.voteInPoll(poll, answerIndex, getLoggedInMember().getMatricNumber());
        indicateClubBookChanged();
    }

    @Override
    public synchronized void deletePoll(Poll target) throws PollNotFoundException {
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
    public static final String PREFIX_ANSWER = "Ans: ";
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
        return PREFIX_ANSWER + value;
    }
}
```
###### \java\seedu\club\model\poll\exceptions\AnswerNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified poll.
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
 * Signals that the operation is unable to find the specified poll.
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
    public void vote(Index answerIndex, MatricNumber polleeMatricNumber) throws
            AnswerNotFoundException, UserAlreadyVotedException {
        if (polleesMatricNumbers.contains(polleeMatricNumber)) {
            throw new UserAlreadyVotedException();
        } else {
            try {
                answers.get(answerIndex.getZeroBased()).voteThisAnswer();
            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new AnswerNotFoundException();
            }
            polleesMatricNumbers.add(polleeMatricNumber);
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
        return "[ " + question + " ]"
                + answers.stream().map(Answer::toString).collect(Collectors.joining(","));
    }
}
```
###### \java\seedu\club\model\poll\PollIsRelevantToMemberPredicate.java
``` java
import java.util.function.Predicate;

import seedu.club.model.member.Member;

/**
 * Tests that a {@code poll} is relevant to to {@code member}
 * If member is null, no polls will be shown
 * If member is an exco, all polls are relevant else only polls that have not been answered by member will be shown
 */
public class PollIsRelevantToMemberPredicate implements Predicate<Poll> {

    private static final String GROUP_EXCO = "exco";
    private final Member member;

    public PollIsRelevantToMemberPredicate(Member member) {
        this.member = member;
    }

    @Override
    public boolean test(Poll poll) {
        if (member == null) {
            return false;
        } else if (member.getGroup().toString().equalsIgnoreCase("exco")) {
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

    public static final String MESSAGE_QUESTION_CONSTRAINTS = "Questions may not be empty";
    public static final String QUESTION_VALIDATION_REGEX = ".*\\S.*";
    public static final String PREFIX_QUESTION = "Qn: ";

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
        return PREFIX_QUESTION + value;
    }
}
```
###### \java\seedu\club\model\poll\UniquePollList.java
``` java
    /**
     * Replaces the specified poll with a deep copy except that the copy has the specified answer
     * increased it's vote count by 1.
     *
     * @param poll to be copied
     * @param answerIndex of poll to be voted for
     * @param polleeMatricNumber of pollee who wants to vote for the answer of the poll
     *
     * @throws PollNotFoundException if poll is not in this list
     * @throws AnswerNotFoundException if answerIndex is not answerIndex of any answers of poll
     * @throws UserAlreadyVotedException if pollee has already voted in the poll
     */
    public void voteInPoll(Poll poll, Index answerIndex, MatricNumber polleeMatricNumber)
            throws PollNotFoundException, AnswerNotFoundException, UserAlreadyVotedException {
        int pollIndex = internalList.indexOf(poll);
        if (pollIndex == -1) {
            throw new PollNotFoundException();
        } else {
            Poll pollDeepCopy = new Poll(new Question(poll.getQuestion().getValue()),
                    poll.getAnswers(), poll.getPolleesMatricNumbers());
            pollDeepCopy.vote(answerIndex, polleeMatricNumber);
            internalList.set(pollIndex, pollDeepCopy);
        }
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Region;
import seedu.club.model.poll.Answer;

/**
 * A UI component that displays information of an {@code answer}.
 */
public class AnswerCard extends UiPart<Region> {

    private static final String FXML = "AnswerListCard.fxml";
    private static final String DESCRIPTION_VOTE_COUNT = "Vote Count: ";
    private final int totalVoteCount;

    @FXML
    private Label answerValue;

    @FXML
    private Label choice;

    @FXML
    private Label voteCount;

    @FXML
    private ProgressIndicator voteCountIndicator;

    /**
     * A constructor to initialize AnswerCard using {@value FXML} with results
     */
    public AnswerCard(Answer answer, int displayedIndex, int totalVoteCount) {
        super(FXML);
        this.totalVoteCount = totalVoteCount;
        choice.setText(displayedIndex + ". ");
        answerValue.setText(answer.getValue());
        voteCount.setText(DESCRIPTION_VOTE_COUNT + answer.getVoteCount());
        setVoteCountIndicator(answer);
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

    private void setVoteCountIndicator(Answer answer) {
        int voteCount = answer.getVoteCount();
        double progress = totalVoteCount == 0
                ? 0 : ((double) voteCount) / totalVoteCount;
        voteCountIndicator.setProgress(progress);
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
###### \resources\view\AnswerListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ProgressIndicator?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">

    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150"/>
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="33.0" prefHeight="106.0" prefWidth="213.0" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5"/>
            </padding>
            <HBox alignment="CENTER_LEFT" prefHeight="65.0" prefWidth="135.0" spacing="5" VBox.vgrow="ALWAYS">
                <Label fx:id="choice" styleClass="cell_big_label">
                    <minWidth>
                        <Region fx:constant="USE_PREF_SIZE"/>
                    </minWidth>
                </Label>
                <Label fx:id="answerValue" wrapText="true"/>
            </HBox>
            <HBox alignment="CENTER_LEFT" prefHeight="52.0" prefWidth="142.0" spacing="5">
                <children>
                    <Label fx:id="voteCount" styleClass="cell_big_label">
                        <minWidth>
                            <Region fx:constant="USE_PREF_SIZE"/>
                        </minWidth>
                    </Label>
                    <ProgressIndicator fx:id="voteCountIndicator" prefHeight="31.0" prefWidth="38.0" progress="0.0"/>
                </children>
            </HBox>
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
<HBox xmlns:fx="http://javafx.com/fxml/1" id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.121">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150"/>
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="33.0" prefHeight="106.0" prefWidth="213.0" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5"/>
            </padding>
            <HBox alignment="CENTER_LEFT" prefHeight="65.0" prefWidth="135.0" spacing="5" VBox.vgrow="ALWAYS">
                <Label fx:id="choice" styleClass="cell_big_label">
                    <minWidth>
                        <Region fx:constant="USE_PREF_SIZE"/>
                    </minWidth>
                </Label>
                <Label fx:id="answerValue" wrapText="true"/>
            </HBox>
        </VBox>
        <rowConstraints>
            <RowConstraints/>
        </rowConstraints>
    </GridPane>
</HBox>
```
