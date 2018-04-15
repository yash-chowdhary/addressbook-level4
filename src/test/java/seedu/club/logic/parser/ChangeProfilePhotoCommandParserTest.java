//@@author amrut-prabhu
package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.club.logic.commands.ChangeProfilePhotoCommand;
import seedu.club.model.member.ProfilePhoto;

public class ChangeProfilePhotoCommandParserTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private ChangeProfilePhotoCommandParser parser = new ChangeProfilePhotoCommandParser();
    private String currentDirectoryPath = ".";
    private File currentDirectory = new File(currentDirectoryPath);

    @Test
    public void parse_validArgs_returnsChangeProfilePhotoommand() throws Exception {
        File imageFile = temporaryFolder.newFile("dummy.jpg");
        String expectedImageFilePath = imageFile.getAbsolutePath();
        ProfilePhoto expectedPhoto = new ProfilePhoto(expectedImageFilePath);
        assertParseSuccess(parser, expectedImageFilePath, new ChangeProfilePhotoCommand(expectedPhoto));

        imageFile = temporaryFolder.newFile("dummy.png");
        expectedImageFilePath = imageFile.getAbsolutePath();
        expectedPhoto = new ProfilePhoto(expectedImageFilePath);
        assertParseSuccess(parser, expectedImageFilePath, new ChangeProfilePhotoCommand(expectedPhoto));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //non absolute file path
        assertParseFailure(parser, "./dummyImage.png", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ProfilePhoto.MESSAGE_PHOTO_PATH_CONSTRAINTS + ChangeProfilePhotoCommand.MESSAGE_USAGE));

        //invalid file path
        assertParseFailure(parser, currentDirectory.getAbsolutePath(), String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ProfilePhoto.MESSAGE_PHOTO_PATH_CONSTRAINTS + ChangeProfilePhotoCommand.MESSAGE_USAGE));

        //invalid file type
        assertParseFailure(parser, currentDirectory.getAbsolutePath() + "/dummyImage.gif",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProfilePhoto.MESSAGE_PHOTO_PATH_CONSTRAINTS
                        + ChangeProfilePhotoCommand.MESSAGE_USAGE));
    }
}
