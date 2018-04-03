package seedu.club.model.poll;
import java.util.function.Predicate;

import seedu.club.model.member.Member;

/**
 * Tests that a {@code poll} is relevant to to {@code member}
 * If member is null, no polls will be shown
 * If member is an exco, all polls are relevant else only polls that have not been answered by member will be shown
 */
public class PollIsRelevantPredicate implements Predicate<Poll> {

    private static final String GROUP_EXCO = "exco";
    private final Member member;

    public PollIsRelevantPredicate(Member member) {
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
                || (other instanceof PollIsRelevantPredicate     // handles nulls
                && this.member.equals(((PollIsRelevantPredicate) other).getMember()));   // state check
    }
}
