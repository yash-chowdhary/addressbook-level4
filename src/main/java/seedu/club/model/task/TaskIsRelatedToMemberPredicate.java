package seedu.club.model.task;

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
        return member.getName().toString().equalsIgnoreCase(task.getAssignor().getAssignor());
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
