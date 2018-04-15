package seedu.club.model.poll;

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.club.commons.core.index.Index;
import seedu.club.commons.util.CollectionUtil;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.poll.exceptions.AnswerNotFoundException;
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.model.poll.exceptions.PollNotFoundException;
import seedu.club.model.poll.exceptions.UserAlreadyVotedException;

/**
 * A list of polls that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Poll#equals(Object)
 */
public class UniquePollList implements Iterable<Poll> {

    private final ObservableList<Poll> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PollList.
     */
    public UniquePollList() {}

    /**
     * Creates a UniquePollList using given polls.
     * Enforces no nulls.
     */
    public UniquePollList(Set<Poll> polls) {
        requireAllNonNull(polls);
        internalList.addAll(polls);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Replaces the Polls in this list with those in the argument poll list.
     */
    public void setPolls(Set<Poll> polls) {
        requireAllNonNull(polls);
        internalList.setAll(polls);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    //@@author MuhdNurKamal
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
    //@@author

    /**
     * Returns true if the list contains an equivalent Poll as the given argument.
     */
    public boolean contains(Poll toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Poll to the list.
     *
     * @throws DuplicatePollException if the Poll to add is a duplicate of an existing Poll in the list.
     */
    public void add(Poll toAdd) throws DuplicatePollException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePollException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes the equivalent poll from the list.
     *
     * @throws PollNotFoundException if no such poll could be found in the list.
     */
    public boolean remove(Poll toRemove) throws PollNotFoundException {
        requireNonNull(toRemove);
        final boolean pollFoundAndDeleted = internalList.remove(toRemove);
        if (!pollFoundAndDeleted) {
            throw new PollNotFoundException();
        }
        return pollFoundAndDeleted;
    }

    @Override
    public Iterator<Poll> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Poll> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniquePollList // instanceof handles nulls
                        && this.internalList.equals(((UniquePollList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
}
