//@@author amrut-prabhu
package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.club.logic.commands.ImportCommand;

public class ImportCommandParserTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private ImportCommandParser parser = new ImportCommandParser();
    private String currentDirectoryPath = "./";
    private File currentDirectory = new File(currentDirectoryPath);

    @Test
    public void parse_validArgs_returnsImportCommand() throws Exception {
        File importFile = temporaryFolder.newFile("dummy.csv");

        String expectedImportFilePath = importFile.getAbsolutePath();
        File expectedImportFile = new File(expectedImportFilePath);
        assertParseSuccess(parser, expectedImportFile.getAbsolutePath(), new ImportCommand(expectedImportFile));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //non absolute file path
        assertParseFailure(parser, "data/dummy.csv", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ParserUtil.MESSAGE_INVALID_CSV_PATH + ImportCommand.MESSAGE_USAGE));

        //invalid file path
        assertParseFailure(parser, currentDirectory.getAbsolutePath() + "/data/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParserUtil.MESSAGE_INVALID_CSV_PATH
                        + ImportCommand.MESSAGE_USAGE));

        //invalid file type
        assertParseFailure(parser, currentDirectory.getAbsolutePath() + "/data/importTestFile.txt",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParserUtil.MESSAGE_INVALID_CSV_PATH
                        + ImportCommand.MESSAGE_USAGE));
    }
}
