package seedu.club.model.poll;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.club.model.member.MatricNumber;

/**
 * Represents a Poll in the club book.
 */
public class Poll {

    private final Question question;
    private final List<Answer> answers;
    private final Set<MatricNumber> polleesMatricNumber;
    private final MatricNumber pollerMatricNumber;


    /**
     * Constructs a {@code Poll}.
     */
    public Poll(Question question, MatricNumber pollerMatricNumber, Answer... answers) {
        requireNonNull(question);
        requireNonNull(answers);

        this.question = question;
        this.answers = Arrays.asList(answers);
        this.pollerMatricNumber = pollerMatricNumber;
        polleesMatricNumber = new HashSet<>();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Poll // instanceof handles nulls
                && this.question.equals(((Poll) other).question) // state check
                && this.answers.equals(((Poll) other).answers));
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
        return "[ " + question + "]"
                + answers.stream().map(Answer::toString).collect(Collectors.joining(","));
    }

    public String getPollerName() {
        // TODO
        return pollerMatricNumber.toString();
    }

    public Question getQuestion() {
        return question;
    }

    /**
     * Returns an immutable answer list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }
}
