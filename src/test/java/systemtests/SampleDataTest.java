package systemtests;

import static seedu.club.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.club.logic.commands.LogInCommand;
import seedu.club.model.ClubBook;
import seedu.club.model.member.Member;
import seedu.club.model.util.SampleDataUtil;
import seedu.club.testutil.TestUtil;

public class SampleDataTest extends ClubBookSystemTest {
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
    public void clubBook_dataFileDoesNotExist_loadSampleData() {
        ObservableList<Member> memberObservableList = getModel().getClubBook().getMemberList();
        String logInCommand = LogInCommand.COMMAND_WORD + " u/" + memberObservableList.get(0).getMatricNumber().value
                + " pw/password";
        executeCommand(logInCommand);
        Member[] expectedList = SampleDataUtil.getSampleMembers();
        assertListMatching(getMemberListPanel(), expectedList);
    }
}
