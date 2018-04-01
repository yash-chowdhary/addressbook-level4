//@@author amrut-prabhu
package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.club.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private ExportCommandParser parser = new ExportCommandParser();
    private String currentDirectoryPath = ".";
    private File currentDirectory = new File(currentDirectoryPath);

    @Test
    public void parse_validArgs_returnsExportCommand() throws Exception {
        File exportFile = temporaryFolder.newFile("actual.csv");

        String expectedExportFilePath = exportFile.getAbsolutePath();
        File expectedExportFile = new File(expectedExportFilePath);
        assertParseSuccess(parser, expectedExportFile.getAbsolutePath(), new ExportCommand(expectedExportFile));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //non absolute file path
        assertParseFailure(parser, "data/exportTestFile.csv", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));

        //invalid file path
        assertParseFailure(parser, currentDirectory.getAbsolutePath() + "/data/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));

        //invalid file type
        assertParseFailure(parser, currentDirectory.getAbsolutePath() + "/data/exportTestFile.txt",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }
}
