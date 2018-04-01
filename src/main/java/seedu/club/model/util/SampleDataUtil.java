package seedu.club.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.club.model.ClubBook;
import seedu.club.model.ReadOnlyClubBook;

import seedu.club.model.group.Group;

import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
import seedu.club.model.member.exceptions.DuplicateMemberException;

import seedu.club.model.poll.Answer;
import seedu.club.model.poll.Poll;
import seedu.club.model.poll.Question;
import seedu.club.model.poll.exceptions.DuplicatePollException;
import seedu.club.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ClubBook} with sample data.
 */
public class SampleDataUtil {
    public static Member[] getSampleMembers() {
        return new Member[] {
            new Member(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new MatricNumber("A5215090A"), new Group("logistics"),
                    getTagSet("friends")),
            new Member(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new MatricNumber("A0632713Q"), new Group("production"),
                    getTagSet("colleagues", "friends")),
            new Member(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new MatricNumber("A1010027G"), new Group("exco"),
                    getTagSet("neighbours")),
            new Member(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new MatricNumber("A7251856A"), new Group("marketing"),
                    getTagSet("family")),
            new Member(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new MatricNumber("A4960627S"), new Group("pr"),
                    getTagSet("classmates")),
            new Member(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new MatricNumber("A2488865L"), new Group("legal"),
                    getTagSet("colleagues"))
        };
    }

    public static Poll[] getSamplePolls() {
        return new Poll[] {
            new Poll(new Question("How are you?"),
                    Arrays.asList(new Answer("I'm fine"), new Answer("Not good man"))),
            new Poll(new Question("What are you?"),
                    Arrays.asList(new Answer("A vampire"), (new Answer("A zombie")))),
            new Poll(new Question("Who are you?"),
                Arrays.asList(new Answer("Your father"), (new Answer("Your mom")))),
            new Poll(new Question("When are you?"),
                Arrays.asList(new Answer("I don't get it"), (new Answer("Umm.."))))
        };
    }

    public static ReadOnlyClubBook getSampleClubBook() {
        try {
            ClubBook sampleCb = new ClubBook();
            for (Member sampleMember : getSampleMembers()) {
                sampleCb.addMember(sampleMember);
            }
            for (Poll samplePoll : getSamplePolls()) {
                sampleCb.addPoll(samplePoll);
            }
            return sampleCb;
        } catch (DuplicateMemberException e) {
            throw new AssertionError("sample data cannot contain duplicate members", e);
        } catch (DuplicatePollException e) {
            throw new AssertionError("sample data cannot contain duplicate polls", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    /**
     * Returns a answer list containing the list of strings given.
     */
    public static List<Answer> getAnswerList(String... strings) {
        List<Answer> answers = new ArrayList<>();
        for (String s : strings) {
            answers.add(new Answer(s));
        }
        return answers;
    }

    /**
     * Returns a matric number set containing the list of strings given.
     */
    public static Set<MatricNumber> getMatricNumberSet(String... strings) {
        Set<MatricNumber> matricNumbers = new HashSet<>();
        for (String s : strings) {
            matricNumbers.add(new MatricNumber(s));
        }
        return matricNumbers;
    }

}
