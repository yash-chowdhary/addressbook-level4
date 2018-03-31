package systemtests;

import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.club.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.SignUpCommand;
import seedu.club.model.ClubBook;
import seedu.club.model.group.Group;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
import seedu.club.model.tag.Tag;
import seedu.club.testutil.TestUtil;

public class SignUpCommandSystemTest extends ClubBookSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected ClubBook getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected String getDataFileLocation() {
        String filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void signup() {
        Member[] list = new Member[1];
        list[0] =  new Member(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new MatricNumber("A5215090A"), new Group("logistics"),
                getTagSet("friends"));
        String signupcommand = SignUpCommand.COMMAND_WORD +  " "
                + PREFIX_NAME + list[0].getName().toString() + " "
                + PREFIX_PHONE + list[0].getPhone().toString() + " "
                + PREFIX_EMAIL + list[0].getEmail().value + " "
                + PREFIX_MATRIC_NUMBER + list[0].getMatricNumber() + " "
                + PREFIX_TAG + "friends ";
        String logincommand = LogInCommand.COMMAND_WORD + " u/" + list[0].getCredentials().getUsername().value
                + " pw/password";
        executeCommand(signupcommand);
        executeCommand(logincommand);
        assertListMatching(getMemberListPanel(), list);
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
}
