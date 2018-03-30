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

import org.junit.Test;

import seedu.club.logic.commands.LogInCommand;
import seedu.club.logic.commands.SignUpCommand;
import seedu.club.model.ClubBook;
import seedu.club.model.member.Member;
import seedu.club.model.util.SampleDataUtil;
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
        Member[] expectedList = SampleDataUtil.getSampleMembers();
        String signupcommand = SignUpCommand.COMMAND_WORD +  " "
                + PREFIX_NAME + expectedList[0].getName().toString() + " "
                + PREFIX_PHONE + expectedList[0].getPhone().toString() + " "
                + PREFIX_EMAIL + expectedList[0].getEmail().value + " "
                + PREFIX_MATRIC_NUMBER + expectedList[0].getMatricNumber() + " "
                + PREFIX_TAG + "friends ";
        String logincommand = LogInCommand.COMMAND_WORD + " u/" + expectedList[0].getCredentials().getUsername().value
                + " pw/password";
        executeCommand(signupcommand);
        executeCommand(logincommand);
        Member[] list = new Member[1];
        list[0] = expectedList[0];
        assertListMatching(getMemberListPanel(), list);
    }
}
