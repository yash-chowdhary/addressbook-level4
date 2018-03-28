package seedu.club.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.club.model.member.MatricNumber;
import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.Question;
import seedu.club.model.util.SampleDataUtil;

/**
 * A utility class to help with building poll objects.
 */
public class PollBuilder {

    public static final String DEFAULT_QUESTION = "What is the meaning of life";
    public static final String DEFAULT_ANSWER_ONE = "Fourty Two";
    public static final String DEFAULT_ANSWER_TWO = "Fourty Three";
    public static final String DEFAULT_POLLEE_MATRIC_NUMBER_ONE = "A1234567B";
    public static final String DEFAULT_POLLEE_MATRIC_NUMBER_TWO = "A1234567C";

    private Question question;
    private List<Answer> answers;
    private Set<MatricNumber> polleesMatricNumbers;

    public PollBuilder() {
        question = new Question(DEFAULT_QUESTION);
        answers = Arrays.asList(new Answer(DEFAULT_ANSWER_ONE), new Answer(DEFAULT_ANSWER_TWO));
        polleesMatricNumbers = new HashSet<>();
        polleesMatricNumbers.add(new MatricNumber(DEFAULT_POLLEE_MATRIC_NUMBER_ONE));
        polleesMatricNumbers.add(new MatricNumber(DEFAULT_POLLEE_MATRIC_NUMBER_TWO));
    }

    /**
     * Initializes the PollBuilder with the data of {@code pollToCopy}.
     */
    public PollBuilder(Poll pollToCopy) {
        question = pollToCopy.getQuestion();
        answers = new ArrayList<>(pollToCopy.getAnswers());
        polleesMatricNumbers = new HashSet<>(pollToCopy.getPolleesMatricNumbers());
    }

    /**
     * Sets the {@code question} of the {@code poll} that we are building.
     */
    public PollBuilder withQuestion(String question) {
        this.question = new Question(question);
        return this;
    }

    /**
     * Parses the {@code answers} into a {@code Set<Tag>} and set it to the {@code poll} that we are building.
     */
    public PollBuilder withAnswers(String ... answers) {
        this.answers = SampleDataUtil.getAnswerList(answers);
        return this;
    }

    /**
     * Sets the {@code pollesMatricNumbers} of the {@code poll} that we are building.
     */
    public PollBuilder withPolleesMatricNumbers(String... polleesMatricNumbers) {
        this.polleesMatricNumbers = SampleDataUtil.getMatricNumberSet(polleesMatricNumbers);
        return this;
    }

    public Poll build() {
        return new Poll(question, answers, polleesMatricNumbers);
    }
}
