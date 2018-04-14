package seedu.club.model.poll;
//@@author MuhdNurKamal
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
