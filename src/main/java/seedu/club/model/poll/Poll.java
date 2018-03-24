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
    private final Set<MatricNumber> polleesMatricNumbers;
    private final MatricNumber pollerMatricNumber;


    /**
     * Constructs a {@code Poll}.
     */
    public Poll(Question question, MatricNumber pollerMatricNumber, Answer... answers) {
        requireNonNull(question);
        requireNonNull(answers);
        requireNonNull(pollerMatricNumber);

        this.question = question;
        this.answers = Arrays.asList(answers);
        this.pollerMatricNumber = pollerMatricNumber;
        this.polleesMatricNumbers = new HashSet<>();
    }

    public Poll(Question question, MatricNumber pollerMatricNumber,
                List<Answer> answers, Set<MatricNumber> polleesMatricNumbers) {
        requireNonNull(question);
        requireNonNull(answers);
        requireNonNull(pollerMatricNumber);

        this.question = question;
        this.answers = answers;
        this.pollerMatricNumber = pollerMatricNumber;
        if (polleesMatricNumbers == null) {
            this.polleesMatricNumbers = new HashSet<>();
        } else {
            this.polleesMatricNumbers = polleesMatricNumbers;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Poll // instanceof handles nulls
                && this.question.equals(((Poll) other).question) // state check
                && this.answers.equals(((Poll) other).answers));
    }

    public String getPollerName() {
        // TODO
        return pollerMatricNumber.toString();
    }

    public Question getQuestion() {
        return question;
    }

    public MatricNumber getPollerMatricNumber() {
        return pollerMatricNumber;
    }

    /**
     * Returns an immutable answer list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    /**
     * Returns an immutable poll set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<MatricNumber> getPolleesMatricNumbers() {
        return Collections.unmodifiableSet(polleesMatricNumbers);
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
}
