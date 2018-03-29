package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.club.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.File;

import org.junit.Test;

import seedu.club.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();
    private String currentDirectoryPath = ".";
    private File currentDirectory = new File(currentDirectoryPath);

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        String exportFilePath = currentDirectory.getAbsolutePath() + "/data/exportTestFile.csv";
        File exportFile = new File(exportFilePath);
        assertParseSuccess(parser, exportFilePath, new ExportCommand(exportFile));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }
}
