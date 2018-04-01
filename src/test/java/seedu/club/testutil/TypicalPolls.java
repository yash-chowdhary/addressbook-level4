package seedu.club.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.club.model.ClubBook;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.exceptions.DuplicatePollException;

/**
 * A utility class containing a list of {@code poll} objects
 * to be used in tests.
 */
public class TypicalPolls {

    public static final Poll POLL_WHAT = new PollBuilder().withQuestion("What are you?")
            .withAnswers("A vampire", "A zombie")
            .withPolleesMatricNumbers("A1234567A", "A3333333A").build();

    public static final Poll POLL_HOW = new PollBuilder().withQuestion("How are you?")
            .withAnswers("I'm fine", "Not good man")
            .withPolleesMatricNumbers("A3333333A", "A2222222A").build();

    public static final Poll POLL_WHEN = new PollBuilder().withQuestion("When are you?")
            .withAnswers("I don't get it", "Umm..")
            .withPolleesMatricNumbers("A2222222A", "A1234567A").build();
    public static final Poll POLL_WHO = new PollBuilder().withQuestion("Who are you?")
            .withAnswers("Your father", "Your mom")
            .withPolleesMatricNumbers("A3333333A", "A1111111A").build();


    private TypicalPolls() {} // prevents instantiation

    /**
     * Returns an {@code ClubBook} with all the typical polls.
     */
    public static ClubBook getTypicalClubBook() {
        ClubBook ab = new ClubBook();
        for (Poll poll : getTypicalPolls()) {
            try {
                ab.addPoll(poll);
            } catch (DuplicatePollException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Poll> getTypicalPolls() {
        return new ArrayList<>(Arrays.asList(POLL_HOW, POLL_WHAT, POLL_WHEN, POLL_WHO));
    }
}
