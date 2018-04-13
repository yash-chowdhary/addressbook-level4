package seedu.club.model.task;
//@@author yash-chowdhary
import java.util.function.Predicate;

import seedu.club.model.member.Member;

/**
 * Tests that a {@code task} is related to (Assignor or Assignee) the {@code member}.
 */
public class TaskIsRelatedToMemberPredicate implements Predicate<Task> {

    private final Member member;

    public TaskIsRelatedToMemberPredicate(Member member) {
        this.member = member;
    }

    @Override
    public boolean test(Task task) {
        return member.getMatricNumber().toString().equalsIgnoreCase(task.getAssignor().getValue())
                || member.getMatricNumber().toString().equalsIgnoreCase(task.getAssignee().getValue());
    }

    public Member getMember() {
        return member;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this)  // short circuit if same object
                || (other instanceof TaskIsRelatedToMemberPredicate     // handles nulls
                && this.member.equals(((TaskIsRelatedToMemberPredicate) other).getMember()));   // state check
    }
}
