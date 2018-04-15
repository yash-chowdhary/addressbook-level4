# amrut-prabhu
###### \java\seedu\club\commons\events\model\NewExportDataAvailableEvent.java
``` java
package seedu.club.commons.events.model;

import java.io.File;

import seedu.club.commons.events.BaseEvent;

/**
 * Indicates that a new member to export is available.
 */
public class NewExportDataAvailableEvent extends BaseEvent {

    public final File exportFile;
    public final String data;
    private boolean isDataExported;

    public NewExportDataAvailableEvent(File exportFile, String data) {
        this.exportFile = exportFile;
        this.data = data;
        this.isDataExported = true;
    }

    public boolean isDataExported() {
        return isDataExported;
    }

    public void setDataExported(boolean isDataExported) {
        this.isDataExported = isDataExported;
    }

    @Override
    public String toString() {
        return "add " + data + " to file " + exportFile.getAbsolutePath();
    }
}
```
###### \java\seedu\club\commons\events\model\ProfilePhotoChangedEvent.java
``` java
package seedu.club.commons.events.model;

import static seedu.club.storage.ProfilePhotoStorage.PHOTO_FILE_EXTENSION;

import seedu.club.commons.events.BaseEvent;

/**
 * Indicates that the profile photo of a member has changed.
 */
public class ProfilePhotoChangedEvent extends BaseEvent {

    public final String originalPhotoPath;
    public final String newFileName;
    private boolean isPhotoChanged;

    public ProfilePhotoChangedEvent(String originalPhotoPath, String newFileName) {
        this.originalPhotoPath = originalPhotoPath;
        this.newFileName = newFileName;
        this.isPhotoChanged = true;
    }

    public boolean isPhotoChanged() {
        return isPhotoChanged;
    }

    public void setPhotoChanged(boolean isPhotoChanged) {
        this.isPhotoChanged = isPhotoChanged;
    }


    @Override
    public String toString() {
        return originalPhotoPath + " is being stored as " + newFileName + PHOTO_FILE_EXTENSION;
    }

}
```
###### \java\seedu\club\commons\events\storage\DataReadingExceptionEvent.java
``` java
package seedu.club.commons.events.storage;

import seedu.club.commons.events.BaseEvent;

/**
 * Indicates an exception during a file reading.
 */
public class DataReadingExceptionEvent extends BaseEvent {

    public final Exception exception;

    public DataReadingExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return exception.toString();
    }

}
```
###### \java\seedu\club\commons\exceptions\PhotoReadException.java
``` java
package seedu.club.commons.exceptions;

import java.io.IOException;

/**
 * Represents an error while reading a photo file.
 */
public class PhotoReadException extends IOException {

    public PhotoReadException() {}

    public PhotoReadException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }

}
```
###### \java\seedu\club\commons\exceptions\PhotoWriteException.java
``` java
package seedu.club.commons.exceptions;

import java.io.IOException;

/**
 * Represents an error while writing a photo file.
 */
public class PhotoWriteException extends IOException {

    public PhotoWriteException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }

}
```
###### \java\seedu\club\commons\util\CsvUtil.java
``` java
package seedu.club.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_MEMBER_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.exceptions.DataConversionException;
import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.parser.ArgumentMultimap;
import seedu.club.logic.parser.ArgumentTokenizer;
import seedu.club.logic.parser.ParserUtil;
import seedu.club.logic.parser.Prefix;
import seedu.club.model.group.Group;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
import seedu.club.model.member.UniqueMemberList;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.tag.Tag;


/**
 * Helps with reading from and writing to CSV files.
 */
public class CsvUtil {

    private static final String CSV_FIELD_SEPARATOR = ",";
    private static final String CSV_FIELD_SURROUNDER = "\"";
    private static final String CSV_VALUE_SEPARATOR = ",";
    private static final String EMPTY_STRING = "";
    private static final String NEWLINE = System.lineSeparator();
    private static final String SPACE = " ";

    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    // ================ Import CSV data methods ==============================

    /**
     * Returns {@code this} Member's data in the format of a CSV record.
     *
     * @return {@code String} containing the data in CSV format.
     */
    public static String getHeaders() {
        final StringBuilder builder = new StringBuilder();

        addFieldInCsv(builder, "Name");
        addFieldInCsv(builder, "Phone");
        addFieldInCsv(builder, "Email");
        addFieldInCsv(builder, "Matriculation Number");
        addFieldInCsv(builder, "Group");
        addLastCsvField(builder, "Tags");

        builder.append(NEWLINE);

        return builder.toString();
    }

    /**
     * Returns {@code objectToConver}'s data in the format of a CSV record.
     * objectToConvert is expected to be a {@code Member} object.
     *
     *  @return {@code String} containing the data in CSV format.
     * @see Member
     */
    public static String toCsvFormat(Object objectToConvert) {
        requireNonNull(objectToConvert);

        Member memberToConvert;
        if (objectToConvert instanceof Member) {
            memberToConvert = (Member) objectToConvert;
        } else {
            assert false : "Object to convert to CSV is expected to be a Member object";
            return EMPTY_STRING;
        }

        StringBuilder builder = new StringBuilder();

        addFieldInCsv(builder, memberToConvert.getName().toString());
        addFieldInCsv(builder, memberToConvert.getPhone().toString());
        addFieldInCsv(builder, memberToConvert.getEmail().toString());
        addFieldInCsv(builder, memberToConvert.getMatricNumber().toString());
        addFieldInCsv(builder, memberToConvert.getGroup().toString());
        addCsvTags(builder, memberToConvert);

        builder.append(NEWLINE);

        return builder.toString();
    }

    /**
     * Appends (@code builder} with {@code field} in CSV format.
     *
     * @param builder StringBuilder which is to be appended to.
     * @param field Field value that is to be appended.
     */
    private static void addFieldInCsv(StringBuilder builder, String field) {
        assert field != null : "Field cannot be null in Member object";

        builder.append(CSV_FIELD_SURROUNDER)
                .append(field)
                .append(CSV_FIELD_SURROUNDER)
                .append(CSV_FIELD_SEPARATOR);
    }

    /**
     * Appends (@code builder} with last {@code field} in CSV format without suffixing with {@code CSV_FIELD_SEPARATOR}.
     *
     * @param builder StringBuilder which is to be appended to.
     * @param field The final field value that is to be appended.
     */
    private static void addLastCsvField(StringBuilder builder, String field) {
        assert field != null : "Field cannot be null in Member object";

        builder.append(CSV_FIELD_SURROUNDER)
                .append(field)
                .append(CSV_FIELD_SURROUNDER);
    }

    /**
     * Appends (@code builder} with all tags of {@code member} in CSV format.
     *
     * @param builder StringBuilder which is to be appended.
     * @param member Member whose tags are to be appended.
     */
    private static void addCsvTags(StringBuilder builder, Member member) {
        builder.append(CSV_FIELD_SURROUNDER);
        member.getTags().forEach(tag -> builder.append(tag.getTagName())
                .append(CSV_VALUE_SEPARATOR)); //Results in an extra "," at end of tag list.
        builder.append(CSV_FIELD_SURROUNDER); //No CSV_FIELD_SEPARATOR as this is the last field.
    }

    // ================ Import CSV data methods ==============================

    /**
     * Returns a {@code Member} created using the given raw {@code rawData}.
     *
     * @param rawData Contains all the rawData of the member extracted from the file.
     * @throws DataConversionException Thrown if the rawData of the member is not in the specified format.
     */
    private static Member getMember(String rawData) throws DataConversionException {
        String remainingData = rawData;
        String memberData = SPACE;
        String memberFieldValue;
        String[] fieldValues;

        try {
            fieldValues = nextValue(remainingData);
            memberFieldValue = fieldValues[0];
            remainingData = fieldValues[1];
            memberData = addMemberData(memberData, PREFIX_NAME.toString(), memberFieldValue);

            fieldValues = nextValue(remainingData);
            memberFieldValue = fieldValues[0];
            remainingData = fieldValues[1];
            memberData = addMemberData(memberData, PREFIX_PHONE.toString(), memberFieldValue);

            fieldValues = nextValue(remainingData);
            memberFieldValue = fieldValues[0];
            remainingData = fieldValues[1];
            memberData = addMemberData(memberData, PREFIX_EMAIL.toString(), memberFieldValue);

            fieldValues = nextValue(remainingData);
            memberFieldValue = fieldValues[0];
            remainingData = fieldValues[1];
            memberData = addMemberData(memberData, PREFIX_MATRIC_NUMBER.toString(), memberFieldValue);

            fieldValues = nextValue(remainingData);
            memberFieldValue = fieldValues[0];
            remainingData = fieldValues[1];
            memberData = addMemberData(memberData, PREFIX_GROUP.toString(), memberFieldValue);

            fieldValues = nextValue(remainingData);
            memberFieldValue = fieldValues[0];
            memberData = addMemberData(memberData, PREFIX_TAG.toString(), memberFieldValue);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            throw new DataConversionException(aioobe);
        }

        try {
            return parseMember(memberData);
        } catch (IllegalValueException ive) {
            throw new DataConversionException(ive);
        }
    }

    /**
     * Returns the next data value of the member.
     * @param data Raw member data.
     * @return {@code String[]} with the next data value at index 0 and remaining data (if it exists) at index 1.
     */
    private static String[] nextValue(String data) {
        String[] values;
        if (data.charAt(0) == '\"') {
            values = data.substring(1).split(CSV_FIELD_SURROUNDER + CSV_FIELD_SEPARATOR, 2);
        } else {
            values = data.split(CSV_FIELD_SEPARATOR, 2);
        }

        return values;
    }

    /**
     * Appends {@code dataToAdd} to {@code memberData} in the required format.
     *
     * @param memberData The current data of the member.
     * @param prefix The prefix needed, depending on the type of {@code dataToAdd}.
     * @param dataToAdd The data that is to be added to {@code memberData}.
     * @return {@code memberData} appended with {@code dataToAdd} in the required format.
     */
    private static String addMemberData(String memberData, String prefix, String dataToAdd) {
        StringBuilder builder = new StringBuilder(memberData);

        if (prefix.equals(PREFIX_TAG.toString())) {
            return addMemberTags(memberData, dataToAdd);
        } else if (dataToAdd.length() != 0) {
            builder.append(prefix).append(removeExcessCharacters(dataToAdd)).append(SPACE);
        }

        return builder.toString();
    }

    /**
     * Appends the tags in {@code dataToAdd} to {@code memberData} in the required format.
     *
     * @param memberData The current data of the member.
     * @param dataToAdd The tags to be added to {@code memberData}.
     * @return {@code memberData} appended with {@code dataToAdd} in the required format.
     */
    private static String addMemberTags(String memberData, String dataToAdd) {
        StringBuilder builder = new StringBuilder(memberData);

        if (dataToAdd.length() != 0) {
            String[] tags = dataToAdd.split(",");

            for (String tag : tags) {
                tag = removeExcessCharacters(tag);
                if (tag.length() > 0) {
                    builder.append(PREFIX_TAG).append(tag).append(SPACE);
                }
            }
        }

        return builder.toString();
    }

    /**
     * Removes leading and trailing whitespaces and double quotes (") from {@code data}.
     */
    private static String removeExcessCharacters(String data) {
        requireNonNull(data);

        //Remove whitespace
        data = data.trim();

        //Remove double quotes(")
        if (data.length() > 0 && data.charAt(0) == '\"') { //First character is "
            data = data.substring(1);
        } else if (data.length() > 0 && data.charAt(data.length() - 1) == '\"') { //Last character is "
            data = data.substring(0, data.length() - 1);
        }

        return data;
    }

    /**
     * Returns a member created by parsing {@code memberData}.
     *
     * @param memberData Data of the member with cli prefixes.
     * @throws IllegalValueException Thrown if the data does not conform the expected format.
     */
    private static Member parseMember(String memberData) throws IllegalValueException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(memberData, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_MATRIC_NUMBER, PREFIX_GROUP, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MATRIC_NUMBER, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_MEMBER_FORMAT, memberData));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
        MatricNumber matricNumber = ParserUtil.parseMatricNumber(argMultimap.getValue(PREFIX_MATRIC_NUMBER)).get();
        Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP))
                .orElse(new Group(Group.DEFAULT_GROUP));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Member member = new Member(name, phone, email, matricNumber, group, tagList);

        return member;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


    // ================ CSV File level methods ==============================

    /**
     * Returns true if {@code path} represents the path of a CSV (.csv) file.
     *
     * @param path Path whose validity is to be checked.
     */
    public static boolean isValidCsvFileName(String path) {
        String csvFileExtension = ".csv";

        int length = path.length();
        String fileExtension = path.substring(length - 4);

        return fileExtension.compareToIgnoreCase(csvFileExtension) == 0;
    }

    /**
     * Saves the data to the file in csv format.
     *
     * @param file A valid existing csv file.
     * @throws IOException Thrown if there is an error writing to the file.
     */
    public static void saveDataToFile(File file, String data) throws IOException {
        requireNonNull(file);
        requireNonNull(data);

        logger.fine("Writing headers and info of members to the file");
        FileUtil.writeToFile(file, data);
    }

    /**
     * Loads a {@code UniqueMemberList} from the data in the csv file.
     * Assumes file exists.
     * Ignores DataConversionException and DuplicateMemberException.
     *
     * @param file Points to a valid csv file containing data that match the {@code Member}.
     *             Cannot be null.
     * @throws IOException Thrown if there is an error reading from the file.
     */
    public static UniqueMemberList getDataFromFile(File file) throws IOException {
        requireNonNull(file);
        UniqueMemberList importedMembers = new UniqueMemberList();
        String data = FileUtil.readFromFile(file);
        String[] membersData = data.split("\n");

        for (int i = 1; i < membersData.length; i++) { //membersData[0] contains Headers
            try {
                Member member = getMember(membersData[i]);
                importedMembers.add(member);
            } catch (DataConversionException dce) {
                logger.warning("DataConversionException encountered while converting " + membersData[i]);
            } catch (DuplicateMatricNumberException dmne) {
                logger.warning("DuplicateMemberException encountered due to " + membersData[i]);
            }
        }

        return importedMembers;
    }

}
```
###### \java\seedu\club\commons\util\FileUtil.java
``` java
    /**
     * Returns true if {@code file} represents the absolute path of a file.
     */
    public static boolean isAbsoluteFilePath(File file) {
        return file.isAbsolute() && !file.isDirectory();
    }

    /**
     * Parses a {@code path} into a {@code File}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code path} is invalid.
     */
    public static File parsePath(String path) throws IllegalValueException {
        requireNonNull(path);
        String trimmedPath = path.trim();

        if (trimmedPath.isEmpty()) {
            throw new IllegalValueException(MESSAGE_INVALID_PATH);
        }

        return new File(trimmedPath);
    }
```
###### \java\seedu\club\logic\commands\ChangeProfilePhotoCommand.java
``` java
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.UpdateSelectionPanelEvent;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.member.ProfilePhoto;

/**
 * Changes the profile photo of the currently logged in member.
 */
public class ChangeProfilePhotoCommand extends Command {

    public static final String COMMAND_WORD = "changepic";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "pic", "profilepic")
    );
    public static final String COMMAND_FORMAT = COMMAND_WORD + " PATH";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes your profile photo.\n"
            + "Parameters: PHOTO_FILE_PATH (must be an absolute file path to your new profile photo)\n"
            + "Example: " + COMMAND_WORD
            + " C:/Users/John Doe/Desktop/john_doe.jpg";

    public static final String MESSAGE_INVALID_PHOTO_PATH = "Invalid photo path: %1$s";
    public static final String MESSAGE_CHANGE_PROFILE_PHOTO_SUCCESS = "Your profile photo has been changed to: %1$s";

    private ProfilePhoto profilePhoto;

    /**
     * @param profilePhoto of the member
     */
    public ChangeProfilePhotoCommand(ProfilePhoto profilePhoto) {
        requireNonNull(profilePhoto);
        this.profilePhoto = profilePhoto;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert profilePhoto.getPhotoPath() != null : "Photo path should not be null.";
        requireToSignUp();
        requireToLogIn();
        try {
            model.addProfilePhoto(profilePhoto.getPhotoPath());
            EventsCenter.getInstance().post(new UpdateSelectionPanelEvent(model.getLoggedInMember(), null, false,
                    null, false));
            return new CommandResult(String.format(MESSAGE_CHANGE_PROFILE_PHOTO_SUCCESS, profilePhoto.getPhotoPath()));
        } catch (PhotoReadException pre) {
            throw new CommandException(String.format(MESSAGE_INVALID_PHOTO_PATH, profilePhoto.getPhotoPath()));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChangeProfilePhotoCommand)) {
            return false;
        }

        // state check
        ChangeProfilePhotoCommand e = (ChangeProfilePhotoCommand) other;
        return this.profilePhoto.equals(e.profilePhoto);
    }
}
```
###### \java\seedu\club\logic\commands\DeleteTagCommand.java
``` java
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.UpdateSelectionPanelEvent;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.tag.Tag;
import seedu.club.model.tag.exceptions.TagNotFoundException;

/**
 * Removes a tag from all members in the club book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletetag";
    public static final String COMMAND_FORMAT = "deletetag t/ ";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "deltag", "rmtag")
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified tag from all members.\n"
            + "Parameters: TAG (must be an existing tag)\n"
            + "Example: " + COMMAND_WORD + " t/EventHelper";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted tag: %1$s";
    public static final String MESSAGE_NON_EXISTENT_TAG = "This tag does not exist in Club Connect.";

    private Tag tagToDelete;

    public DeleteTagCommand(Tag tagToDelete) {
        this.tagToDelete = tagToDelete;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(tagToDelete);
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        try {
            model.deleteTag(tagToDelete);
            EventsCenter.getInstance().post(new UpdateSelectionPanelEvent(null, null, false, tagToDelete, false));
            return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete));
        } catch (TagNotFoundException tnfe) {
            throw new CommandException(MESSAGE_NON_EXISTENT_TAG);
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        List<Tag> lastShownList = model.getFilteredTagList();

        if (!getMasterTagList().contains(tagToDelete)) {
            throw new CommandException(MESSAGE_NON_EXISTENT_TAG);
        }

        int targetIndex = lastShownList.indexOf(tagToDelete);
        tagToDelete = lastShownList.get(targetIndex);
    }

    private List<Tag> getMasterTagList() {
        return new ArrayList<>(model.getClubBook().getTagList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && this.tagToDelete.equals(((DeleteTagCommand) other).tagToDelete));
    }
}
```
###### \java\seedu\club\logic\commands\ExportCommand.java
``` java
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Exports Club Connect's members' information to the file specified.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_FORMAT = "export FILE_PATH";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "exp")
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the members' information to the specified CSV file.\n"
            + "Parameters: FILE_PATH (must be an absolute path to a CSV file)\n"
            + "Example: " + COMMAND_WORD + " C:/Users/Jane Doe/Desktop/Club Connect Members.csv";

    public static final String MESSAGE_EXPORT_SUCCESS = "Successfully exported details of members to %1$s";
    public static final String MESSAGE_EXPORT_FAILURE = "Error occurred while exporting to the file: %1$s";

    private final File exportFile;

    /**
     * @param exportFile CSV file to be exported to.
     */
    public ExportCommand(File exportFile) {
        requireNonNull(exportFile);
        this.exportFile = exportFile;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        try {
            model.exportClubConnectMembers(exportFile);
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_EXPORT_FAILURE, exportFile));
        }

        return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, exportFile));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.exportFile.equals(((ExportCommand) other).exportFile)); // state check
    }
}
```
###### \java\seedu\club\logic\commands\ImportCommand.java
``` java
package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Exports members' information from the specified file into Club Connect.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_FORMAT = "import FILE_PATH";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "imp")
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports the members' information from the specified CSV file into Club Connect.\n"
            + "Parameters: FILE_PATH (must be an absolute path to a CSV file)\n"
            + "Example: " + COMMAND_WORD + " C:/Users/John Doe/Downloads/new_members.csv";

    public static final String MESSAGE_IMPORT_SUCCESS = "Successfully imported %1$d member(s) from %2$s";
    public static final String MESSAGE_IMPORT_FAILURE = "Error occurred while importing from %1$s";
    public static final String MESSAGE_MEMBERS_NOT_IMPORTED = "0 members imported from %1$s. This may be due to "
            + "incorrect format of the data or duplicate members in the file.";

    private final File importFile;

    /**
     * @param importFile CSV file to import members from.
     */
    public ImportCommand(File importFile) {
        requireNonNull(importFile);
        this.importFile = importFile;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        try {
            int numberImported = model.importMembers(importFile);
            if (numberImported == 0) {
                return new CommandResult(String.format(MESSAGE_MEMBERS_NOT_IMPORTED, importFile));
            }
            return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, numberImported, importFile));
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_IMPORT_FAILURE, importFile));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.importFile.equals(((ImportCommand) other).importFile)); // state check
    }
}
```
###### \java\seedu\club\logic\commands\RemoveProfilePhotoCommand.java
``` java
package seedu.club.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.events.ui.UpdateSelectionPanelEvent;
import seedu.club.logic.commands.exceptions.CommandException;

/**
 * Removes the profile photo of the currently logged in member and sets it to the default image.
 */
public class RemoveProfilePhotoCommand extends Command {

    public static final String COMMAND_WORD = "removepic";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "rmpic", "defaultpic", "delpic")
    );
    public static final String COMMAND_FORMAT = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes your profile photo.\n";
    public static final String MESSAGE_REMOVE_PROFILE_PHOTO_SUCCESS = "Your profile photo has been removed.";

    @Override
    public CommandResult execute() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        model.removeProfilePhoto();
        EventsCenter.getInstance().post(new UpdateSelectionPanelEvent(model.getLoggedInMember(), null,
                false, null, false));
        return new CommandResult(MESSAGE_REMOVE_PROFILE_PHOTO_SUCCESS);
    }

}
```
###### \java\seedu\club\logic\parser\ChangeProfilePhotoCommandParser.java
``` java
package seedu.club.logic.parser;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.ChangeProfilePhotoCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.member.ProfilePhoto;

/**
 * Parses input arguments and creates a new ChangeProfilePhotoCommand object
 */
public class ChangeProfilePhotoCommandParser implements Parser<ChangeProfilePhotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeProfilePhotoCommand
     * and returns a ChangeProfilePhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeProfilePhotoCommand parse(String args) throws ParseException {

        try {
            ProfilePhoto profilePhoto = ParserUtil.parseProfilePhoto(args);
            return new ChangeProfilePhotoCommand(profilePhoto);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}
```
###### \java\seedu\club\logic\parser\DeleteTagCommandParser.java
``` java
package seedu.club.logic.parser;

import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.club.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;

import java.util.stream.Stream;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.DeleteTagCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteTagCommand object
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns an DeleteTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_TAG)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        try {
            Tag tag = ParserUtil.parseTag(argumentMultimap.getValue(PREFIX_TAG).get());
            return new DeleteTagCommand(tag);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_TAG_CONSTRAINTS, DeleteTagCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty values in the given
     * {@code ArgumentMultimap}
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\club\logic\parser\ExportCommandParser.java
``` java
package seedu.club.logic.parser;

import java.io.File;
import java.io.IOException;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.ExportCommand;
import seedu.club.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    public static final String MESSAGE_FILE_CREATION_ERROR = "Error while creating file %1$s";

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        try {
            File exportFile = ParserUtil.parseExportPath(args);
            return new ExportCommand(exportFile);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage());
        } catch (IOException ioe) {
            throw new ParseException(String.format(MESSAGE_FILE_CREATION_ERROR, args));
        }
    }

}
```
###### \java\seedu\club\logic\parser\ImportCommandParser.java
``` java
package seedu.club.logic.parser;

import java.io.File;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.logic.commands.ImportCommand;
import seedu.club.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        try {
            File importFile = ParserUtil.parseImportPath(args);
            return new ImportCommand(importFile);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage());
        }
    }

}
```
###### \java\seedu\club\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String photo} into an {@code ProfilePhoto}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code photo} is invalid.
     */
    public static ProfilePhoto parseProfilePhoto(String photoPath) throws IllegalValueException {
        requireNonNull(photoPath);
        String trimmedPath = photoPath.trim();
        File photoFile = new File(trimmedPath);

        if (!FileUtil.isAbsoluteFilePath(photoFile) || !ProfilePhoto.isValidPhotoFile(trimmedPath)) {
            throw new IllegalValueException(ProfilePhoto.MESSAGE_PHOTO_PATH_CONSTRAINTS);
        }
        return new ProfilePhoto(trimmedPath);
    }

    /**
     * Parses a {@code path} into a {@code File}.
     *
     * @throws IllegalValueException if the given {@code path} is is not an absolute file path or does not exist.
     */
    public static File parseImportPath(String path) throws IllegalValueException {
        File file = FileUtil.parsePath(path);

        if (!FileUtil.isAbsoluteFilePath(file) || !CsvUtil.isValidCsvFileName(path)) {
            throw new IllegalValueException(MESSAGE_INVALID_CSV_PATH);
        }

        return file;
    }

    /**
     * Parses a {@code path} into a {@code File}.
     *
     * @throws IllegalValueException if the given {@code path} is not absolute or is a directory.
     */
    public static File parseExportPath(String path) throws IllegalValueException, IOException {
        File file = FileUtil.parsePath(path);

        if (!FileUtil.isAbsoluteFilePath(file) || !CsvUtil.isValidCsvFileName(path)) {
            throw new IllegalValueException(MESSAGE_INVALID_CSV_PATH);
        }

        FileUtil.createIfMissing(file);
        return file;
    }



```
###### \java\seedu\club\model\ClubBook.java
``` java
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any member
        // in the member list.
        try {
            members.add(member);
        } catch (DuplicateMatricNumberException dmne) {
            deleteTagsUniqueToMember(m);
            throw dmne;
        }
    }

```
###### \java\seedu\club\model\ClubBook.java
``` java
    /**
     * Removes tags from master {@code tags} list that are unique to {@code member}.
     *
     * @param member Member whose tags may be removed from {@code tags}.
     */
    private void deleteTagsUniqueToMember(Member member) {
        List<Tag> tagsToCheck = new ArrayList<>(getTagList());
        Set<Tag> newTags = tagsToCheck.stream()
                .filter(t -> !isTagUniqueToMember(t, member))
                .collect(Collectors.toSet());
        tags.setTags(newTags);
    }
    /**
     * Returns true if only {@code member} is tagged with {@code tag}.
     *
     * @param tag Tag that is to be checked.
     * @param member Member whose tags are to be checked.
     */
    private boolean isTagUniqueToMember(Tag tag, Member member) {
        for (Member m : members) {
            if (m.hasTag(tag) && !m.equals(member)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes {@code tagToDelete} for all members in this {@code ClubBook}.
     *
     * @param tagToDelete Tag to be removed
     * @throws TagNotFoundException if the list of {@code tags} does not contain {@code tagToDelete}.
     */
    public void deleteTag(Tag tagToDelete) throws TagNotFoundException {
        //Update tags list
        List<Tag> tags = new ArrayList<Tag>(getTagList());
        if (!tags.contains(tagToDelete)) {
            throw new TagNotFoundException();
        }
        setTags(getListWithoutTag(tagToDelete));

        //Update members list
        try {
            for (Member member : members) {
                if (member.hasTag(tagToDelete)) {
                    deleteTagFromMember(tagToDelete, member);
                }
            }
        } catch (MemberNotFoundException mnfe) {
            throw new AssertionError("Impossible: original member is obtained from the club book.");
        }
    }

    /**
     * Returns a list of tags which does not contain {@code tagToDelete}.
     *
     * @param tagToDelete Tag which should not be included in the tag list
     */
    private Set<Tag> getListWithoutTag(Tag tagToDelete) {
        List<Tag> tags = new ArrayList<Tag>(this.getTagList());
        return tags.stream()
                .filter(t -> !t.equals(tagToDelete))
                .collect(Collectors.toSet());
    }

    /**
     * Removes {@code tag} from {@code member} in this {@code ClubBook}.
     *
     * @param tag Tag which is to be removed from {@code member}.
     * @param member Member from whom {@code tag} is to be removed.
     * @throws MemberNotFoundException if the {@code member} is not in this {@code ClubBook}.
     */
    private void deleteTagFromMember(Tag tag, Member member) throws MemberNotFoundException {
        Set<Tag> memberTags = new HashSet<>(member.getTags());
        if (!memberTags.remove(tag)) {
            return;
        }

        Member newMember = new Member(member.getName(), member.getPhone(), member.getEmail(), member.getMatricNumber(),
                member.getGroup(), memberTags, member.getCredentials(), member.getProfilePhoto());
        try {
            updateMember(member, newMember);
        } catch (DuplicateMatricNumberException dme) {
            throw new AssertionError("Modifying a member's tags only should not result in a duplicate. "
                    + "See member#equals(Object).");
        }
    }

```
###### \java\seedu\club\model\member\exceptions\DuplicateMatricNumberException.java
``` java
package seedu.club.model.member.exceptions;

import seedu.club.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in a member object with a non-unique matric number.
 */
public class DuplicateMatricNumberException extends DuplicateDataException {
    public DuplicateMatricNumberException() {
        super("Operation would result in a member with duplicate matric number");
    }
}
```
###### \java\seedu\club\model\member\Member.java
``` java

    /**
     * Adds {@code memberTags} to {@code this} member's {@code tags}.
     *
     * @param memberTags Tags to be added to {@code this} member.
     */
    private void setTags(Set<Tag> memberTags) {
        Iterator itr = memberTags.iterator();

        while (itr.hasNext()) {
            Tag tag = (Tag) itr.next();
            tags.put(tag.tagName, tag);
        }
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        Set<Tag> memberTags = new HashSet<Tag>();

        Set<String> tagNames = tags.keySet();
        Iterator itr = tagNames.iterator();

        while (itr.hasNext()) {
            String key = (String) itr.next();
            memberTags.add(tags.get(key));
        }

        return Collections.unmodifiableSet(memberTags);
    }

    public boolean hasTag(Tag tag) {
        return getTags().contains(tag);
    }

    public ProfilePhoto getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhotoPath(String newPath) {
        profilePhoto.setNewPhotoPath(newPath);
    }

```
###### \java\seedu\club\model\member\ProfilePhoto.java
``` java
package seedu.club.model.member;

import static java.util.Objects.requireNonNull;

/**
 * Represents a member's profile photo in the club book.
 */
public class ProfilePhoto {

    public static final String EMPTY_STRING = "";
    public static final String DEFAULT_PHOTO_PATH = "/images/defaultProfilePhoto.png";
    public static final String MESSAGE_PHOTO_PATH_CONSTRAINTS =
            "The photo path should be an absolute path to a JPG or PNG image file.";

    private static final String[] validFileExtensions = {".jpg", ".png"};
    private static final int JPG_INDEX = 0;
    private static final int PNG_INDEX = 1;

    private String profilePhotoPath;

    /**
     * Constructs a {@code ProfilePhoto}.
     */
    public ProfilePhoto(String path) {
        this.profilePhotoPath = path;
    }

    /**
     * Returns true if {@code path} represents the path of a JPG (.jpg) or PNG (.png) file.
     *
     * @param path Path whose validity is to be checked.
     */
    public static boolean isValidPhotoFile(String path) {
        int length = path.length();
        String fileExtension = path.substring(length - validFileExtensions[JPG_INDEX].length());

        return fileExtension.compareToIgnoreCase(validFileExtensions[JPG_INDEX]) == 0
                || fileExtension.compareToIgnoreCase(validFileExtensions[PNG_INDEX]) == 0;
    }

    /**
     * Sets the {@code photoFilePath} to the specified {@code path}.
     * @param path A valid image path.
     */
    public void setNewPhotoPath(String path) {
        requireNonNull(path);
        this.profilePhotoPath = path;
    }

    public String getPhotoPath() {
        return profilePhotoPath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePhoto // instanceof handles nulls
                && this.getPhotoPath().equals(((ProfilePhoto) other).getPhotoPath())); // state check
    }

    @Override
    public String toString() {
        return profilePhotoPath;
    }

    @Override
    public int hashCode() {
        return profilePhotoPath.hashCode();
    }

}
```
###### \java\seedu\club\model\member\UniqueMemberList.java
``` java
    /**
     * Returns true if {@code internalList} of members contains a member with the same {@code MatricNumber}.
     *
     * @param toCheck Matric Number that is to be checked for uniqueness.
     */
    private boolean containsMatricNumber(MatricNumber toCheck) {
        requireNonNull(toCheck);
        for (Member member: internalList) {
            if (member.getMatricNumber().equals(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a member to the list.
     *
     * @throws DuplicateMatricNumberException if a member with the same matriculation number as member to add exists.
     */
    public void add(Member toAdd) throws DuplicateMatricNumberException {
        requireNonNull(toAdd);
        if (this.containsMatricNumber(toAdd.getMatricNumber())) {
            throw new DuplicateMatricNumberException();
        }
```
###### \java\seedu\club\model\member\UniqueMemberList.java
``` java
        if (!target.equals(editedMember) && this.containsMatricNumber(editedMember.getMatricNumber())
                && !target.getMatricNumber().equals(editedMember.getMatricNumber())) {
            throw new DuplicateMatricNumberException();
        }
```
###### \java\seedu\club\model\Model.java
``` java

    /**
     * Removes the given tag {@code tag} for all members in the club book.
     */
    void deleteTag(Tag tag) throws TagNotFoundException;

    /**
     * Changes profile photo for the currently logged in member.
     *
     * @param originalPhotoPath Absolute file path of the original photo.
     * @throws PhotoReadException if the {@code originalPhotoPath} is invalid.
     */
    void addProfilePhoto(String originalPhotoPath) throws PhotoReadException;

    /**
     * Removes the current profile photo of the logged in member and sets it back to the default image.
     */
    void removeProfilePhoto();

    /**
     * Exports Club Connect's members' details to the specified file.
     *
     * @param exportFile File to which data is exported.
     * @throws IOException if there was an error writing to file.
     */
    void exportClubConnectMembers(File exportFile) throws IOException;

    /**
     * Imports details of members from the specified file.
     *
     * @param importFile File from which data is imported.
     * @return Number of members added from the import file.
     * @throws IOException if there was an error reading from file.
     */
    int importMembers(File importFile) throws IOException;

    /**
     * Returns an unmodifiable view of the filtered tag list
     */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Updates the filter of the filtered tag list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTagList(Predicate<Tag> predicate);

```
###### \java\seedu\club\model\ModelManager.java
``` java
    /** Raises an event to indicate the profile photo of a member has changed */
    private void indicateProfilePhotoChanged(String originalPath, String newFileName) throws PhotoReadException {
        ProfilePhotoChangedEvent profilePhotoChangedEvent = new ProfilePhotoChangedEvent(originalPath, newFileName);
        raise(profilePhotoChangedEvent);
        if (!profilePhotoChangedEvent.isPhotoChanged()) {
            throw new PhotoReadException();
        }
    }

    @Override
    public void addProfilePhoto(String originalPhotoPath) throws PhotoReadException {
        requireNonNull(originalPhotoPath);

        String newFileName = getLoggedInMember().getMatricNumber().toString();
        indicateProfilePhotoChanged(originalPhotoPath, newFileName);
        String newProfilePhotoPath = SAVE_PHOTO_DIRECTORY + newFileName + PHOTO_FILE_EXTENSION;

        clubBook.changeLoggedInMemberProfilePhoto(newProfilePhotoPath);
        updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        indicateClubBookChanged();
        logger.fine("Member's profile photo has been set to: "
                + getLoggedInMember().getProfilePhoto().getPhotoPath());
    }

    @Override
    public void removeProfilePhoto() {
        clubBook.changeLoggedInMemberProfilePhoto(ProfilePhoto.DEFAULT_PHOTO_PATH);
        updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        indicateClubBookChanged();
        logger.fine("Member's profile photo has been set to default image");
    }

```
###### \java\seedu\club\model\ModelManager.java
``` java
    @Override
    public void deleteTag(Tag tag) throws TagNotFoundException {
        clubBook.deleteTag(tag);
        updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        indicateClubBookChanged();
    }

```
###### \java\seedu\club\model\ModelManager.java
``` java
    @Override
    public int importMembers(File importFile) throws IOException {
        CsvClubBookStorage storage = new CsvClubBookStorage(importFile);
        UniqueMemberList importedMembers = storage.readClubBook();
        int numberMembers = 0;

        for (Member member: importedMembers) {
            try {
                clubBook.addMember(member);
                numberMembers++;
            } catch (DuplicateMatricNumberException dmne) {
                logger.info("DuplicateMemberException encountered due to " + member);
            }
        }
        indicateClubBookChanged();
        return numberMembers;
    }

    /**
     * Raises a {@code NewMemberAvailableEvent} to indicate that {@code data} is to be written to {@code exportFile}.
     *
     * @param exportFile CSV file to be exported to.
     * @param data Member data to be added to the file.
     * @throws IOException if there was an error writing to file.
     */
    private void indicateNewExport(File exportFile, String data) throws IOException {
        NewExportDataAvailableEvent newExportDataAvailableEvent = new NewExportDataAvailableEvent(exportFile, data);
        raise(newExportDataAvailableEvent);
        if (!newExportDataAvailableEvent.isDataExported()) {
            throw new IOException();
        }
    }

    @Override
    public void exportClubConnectMembers(File exportFile) throws IOException {
        requireNonNull(exportFile);

        List<Member> members = new ArrayList<>(clubBook.getMemberList());
        StringBuilder csvMemberList = new StringBuilder();
        /*for (Member member: members) {
            csvMemberList.append(getMemberDataToExport(member));
        }*/
        members.forEach(member -> csvMemberList.append(getMemberDataToExport(member)));
        indicateNewExport(exportFile, csvMemberList.toString());
    }

    /**
     * Returns the CSV representation of the data of a {@code member} that is to be exported.
     *
     * @param member Member whose data is to be exported.
     * @return Member data in CSV format.
     */
    private String getMemberDataToExport(Member member) {
        return CsvUtil.toCsvFormat(member);
    }

```
###### \java\seedu\club\model\ModelManager.java
``` java
    //=========== Filtered Tag List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Tag} backed by the internal list of
     * {@code clubBook}
     */
    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return FXCollections.unmodifiableObservableList(filteredTags);
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredTags.setPredicate(predicate);
    }

```
###### \java\seedu\club\model\tag\exceptions\TagNotFoundException.java
``` java
package seedu.club.model.tag.exceptions;

/**
 * Signals that the operation is unable to find the specified tag.
 */
public class TagNotFoundException extends Exception {}
```
###### \java\seedu\club\model\UserPrefs.java
``` java
        double width = 500;
        double height = 500;
        double taskbarHeight = 50;

        if (!GraphicsEnvironment.isHeadless()) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            width = screenSize.getWidth();
            height = screenSize.getHeight() - taskbarHeight; //To prevent application window from going beyond taskbar
        }

        this.setGuiSettings(width, height, 0, 0);
```
###### \java\seedu\club\storage\CsvClubBookStorage.java
``` java
package seedu.club.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.util.CsvUtil;
import seedu.club.model.member.UniqueMemberList;

/**
 * A class to manage storage of ClubBook data as a csv file on the hard disk.
 */
public class CsvClubBookStorage {

    private static final Logger logger = LogsCenter.getLogger(CsvClubBookStorage.class);

    private File file;

    public CsvClubBookStorage() {
        this.file = null;
    }

    public CsvClubBookStorage(File file) {
        setClubBookFile(file);
    }

    public File getClubBookFile() {
        return this.file;
    }

    public void setClubBookFile(File file) {
        this.file = file;
    }

    /**
     * Returns data from the file as a {@link UniqueMemberList}.
     *
     * @throws IOException if there was any problem when reading from the storage.
     */
    public UniqueMemberList readClubBook() throws IOException {
        return readClubBook(file);
    }

    /**
     * Similar to {@link #readClubBook()}
     *
     * @param importFile location of the data. Cannot be null.
     */
    public UniqueMemberList readClubBook(File importFile) throws IOException {

        requireNonNull(importFile);

        if (!importFile.exists()) {
            logger.info("ClubBook import file "  + importFile + " not found");
            throw new FileNotFoundException();
        }

        return CsvFileStorage.readClubBook(importFile);
    }

    public void saveData(String data) throws IOException {
        saveData(data, file);
    }

    /**
     * @param file location of the data. Cannot be null
     */
    public void saveData(String data, File file) throws IOException {
        requireNonNull(data);
        requireNonNull(file);

        assert file.exists() : "ClubBook export file " + file + " must have been created";

        String columnHeaders = CsvUtil.getHeaders();
        String dataToExport = columnHeaders + data;
        CsvFileStorage.saveDataToFile(file, dataToExport);
    }
}
```
###### \java\seedu\club\storage\CsvFileStorage.java
``` java
package seedu.club.storage;

import java.io.File;
import java.io.IOException;

import seedu.club.commons.util.CsvUtil;
import seedu.club.model.member.UniqueMemberList;

/**
 * Stores ClubBook data in a CSV file.
 */
public class CsvFileStorage {

    /**
     * Saves the given clubBook {@code data} to the specified file.
     */
    public static void saveDataToFile(File file, String data) throws IOException {
        try {
            CsvUtil.saveDataToFile(file, data);
        } catch (IOException ioe) {
            throw new IOException("Unexpected error " + ioe.getMessage());
        }
    }

    /**
     * Returns club book in the file or an empty club book
     */
    public static UniqueMemberList readClubBook(File file) throws IOException {
        return CsvUtil.getDataFromFile(file);
    }
}
```
###### \java\seedu\club\storage\PhotoStorage.java
``` java
package seedu.club.storage;

import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.commons.exceptions.PhotoWriteException;

/**
 * Represents a storage for the file specfied by {@link seedu.club.model.member.ProfilePhoto}.
 */
public interface PhotoStorage {

    /**
     * Returns UserPrefs data from storage.
     *
     * @param originalPhotoPath The absolute file path of the {@link seedu.club.model.member.ProfilePhoto}.
     * @param newPhotoName The file name of the copy of the photo specified by {@code originalPhotoPath}.
     *
     * @throws PhotoReadException if the {@code originalPhotoPath} is invalid.
     * @throws PhotoWriteException if there was an error while copying the photo.
     */
    void copyOriginalPhotoFile(String originalPhotoPath, String newPhotoName)
            throws PhotoReadException, PhotoWriteException;

}
```
###### \java\seedu\club\storage\ProfilePhotoStorage.java
``` java
package seedu.club.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import seedu.club.commons.core.LogsCenter;
import seedu.club.commons.exceptions.PhotoReadException;
import seedu.club.commons.exceptions.PhotoWriteException;
import seedu.club.commons.util.FileUtil;

/**
 * To copy the profile photo to this application's resources.
 */
public class ProfilePhotoStorage implements  PhotoStorage {

    public static final String PHOTO_FILE_EXTENSION = ".bmp";
    public static final String SAVE_PHOTO_DIRECTORY = "photos/";

    private static final String URL_PREFIX = "file:///";

    private static final Logger logger = LogsCenter.getLogger(ProfilePhotoStorage.class);

    @Override
    public void copyOriginalPhotoFile(String originalPhotoPath, String newPhotoName)
            throws PhotoReadException, PhotoWriteException {
        String newPath = null;
        try {
            logger.info("Profile Photo is being read from " + originalPhotoPath);

            URL photoUrl = new URL(URL_PREFIX + originalPhotoPath);
            newPath = SAVE_PHOTO_DIRECTORY + newPhotoName + PHOTO_FILE_EXTENSION;
            InputStream photoStream = photoUrl.openStream();

            createPhotoFileCopy(photoStream, newPath);
        } catch (PhotoWriteException pwe) {
            logger.info("Error while writing photo file");
            throw new PhotoWriteException(newPath);
        } catch (IOException ioe) {
            logger.info("Error while reading photo file");
            throw new PhotoReadException(originalPhotoPath);
        }
    }

    /**
     * Creates a copy the given {@code originalPhoto} in the application's resources.
     * @throws PhotoWriteException if there was any problem writing to the file.
     */
    public void createPhotoFileCopy(InputStream photoStream, String newPath) throws PhotoWriteException {
        logger.info("Profile Photo is being copied to " + newPath);
        try {
            FileUtil.createDirs(new File(SAVE_PHOTO_DIRECTORY));
            Files.copy(photoStream, Paths.get(newPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new PhotoWriteException(newPath);
        }
        logger.info("Profile Photo copying successful");
    }
}
```
###### \java\seedu\club\storage\Storage.java
``` java
    void copyOriginalPhotoFile(String originalPhotoPath, String newPhotoName)
            throws PhotoReadException, PhotoWriteException;

    /**
     * Saves a copy of the newly added photo to Club Connect's resources.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleProfilePictureChangedEvent(ProfilePhotoChangedEvent event);

    /**
     * Writes data to a CSV file on the hard disk.
     * Raises {@link DataSavingExceptionEvent} if there was an error during writing.
     */
    void handleExportDataEvent(NewExportDataAvailableEvent event);
```
###### \java\seedu\club\storage\StorageManager.java
``` java
    // ================ ProfilePhoto methods ==============================

    @Override
    public void copyOriginalPhotoFile(String originalPath, String newPhotoName)
            throws PhotoReadException, PhotoWriteException {
        logger.fine("Attempting to read photo from file: " + originalPath);
        photoStorage.copyOriginalPhotoFile(originalPath, newPhotoName);
    }

    @Override
    @Subscribe
    public void handleProfilePictureChangedEvent(ProfilePhotoChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Profile photo changed, copying file"));
        try {
            copyOriginalPhotoFile(event.originalPhotoPath, event.newFileName);
        } catch (PhotoReadException pre) {
            event.setPhotoChanged(false);
            raise(new DataReadingExceptionEvent(pre));
        } catch (PhotoWriteException pwe) {
            raise(new DataSavingExceptionEvent(pwe));
        }
    }


    // ================ CSV Storage methods ==============================

    @Override
    @Subscribe
    public void handleExportDataEvent(NewExportDataAvailableEvent event) {
        assert event.exportFile != null : "exportFile should be pointing to a valid file";
        csvClubBookStorage.setClubBookFile(event.exportFile);

        logger.fine("Attempting to export data to file: " + csvClubBookStorage.getClubBookFile());
        try {
            csvClubBookStorage.saveData(event.data);
        } catch (IOException e) {
            event.setDataExported(false);
            raise(new DataSavingExceptionEvent(e));
        }
    }
```
###### \java\seedu\club\ui\MemberCard.java
``` java
    private static final Integer PHOTO_WIDTH = 100;
    private static final Integer PHOTO_HEIGHT = 130;

```
###### \java\seedu\club\ui\MemberCard.java
``` java
    /**
     * Sets the profile photo of {@code member} to the displayed photo shape.
     */
    private void setProfilePhoto(Member member) {
        Image photo;
        String photoPath = member.getProfilePhoto().getPhotoPath();
        if (photoPath.equals(EMPTY_STRING) || photoPath.equals(DEFAULT_PHOTO_PATH)) {
            photo = new Image(MainApp.class.getResourceAsStream(DEFAULT_PHOTO_PATH), PHOTO_WIDTH, PHOTO_HEIGHT,
                    false, true);
        } else {
            photo = new Image("file:" + photoPath, PHOTO_WIDTH, PHOTO_HEIGHT, false, true);
        }
        profilePhoto.setImage(photo);
    }

```
###### \java\seedu\club\ui\MemberOverviewPanel.java
``` java
    /**
     * Sets the profile photo to the displayed photo shape.
     */
    private void setProfilePhoto(Member member) {
        Image photo;
        String photoPath = member.getProfilePhoto().getPhotoPath();
        if (photoPath.equals(EMPTY_STRING) || photoPath.equals(DEFAULT_PHOTO_PATH)) {
            photo = new Image(MainApp.class.getResourceAsStream(DEFAULT_PHOTO), PHOTO_WIDTH, PHOTO_HEIGHT,
                    false, true);
        } else {
            photo = new Image("file:" + photoPath, PHOTO_WIDTH, PHOTO_HEIGHT, false, true);
        }
        profilePhoto.setImage(photo);
    }

```
###### \java\seedu\club\ui\StatusBarFooter.java
``` java
        try {
            String saveFileLocation = new File(location).getCanonicalPath();
            Platform.runLater(() -> this.saveLocationStatus.setText(String.format(SAVE_LOCATION, saveFileLocation)));
        } catch (IOException ioe) {
            String saveFileLocation = new File(location).getAbsolutePath();
            Platform.runLater(() -> this.saveLocationStatus.setText(String.format(SAVE_LOCATION, saveFileLocation)));
        }
```
###### \resources\view\CompressedMemberListCard.fxml
``` fxml
  <HBox alignment="CENTER_LEFT">
    <ImageView fx:id="profilePhoto" fitWidth="57" fitHeight="75">
      <HBox.margin>
        <Insets left="5" bottom="2.5" right="5.0" top="2.5" />
      </HBox.margin>
    </ImageView>
  </HBox>
```
###### \resources\view\MemberListCard.fxml
``` fxml
  <HBox alignment="CENTER_LEFT">
    <ImageView fx:id="profilePhoto" fitWidth="100" fitHeight="130">
      <HBox.margin>
        <Insets left="7.5" bottom="7.5" right="5.0" top="7.5" />
      </HBox.margin>
    </ImageView>
  </HBox>
```
###### \resources\view\MemberListPanel.fxml
``` fxml
  <TextFlow styleClass="text-panel-header" textAlignment="CENTER">
    <Text text="CLUB MEMBERS" fill="darkblue" VBox.vgrow="NEVER" />
    <VBox.margin>
      <Insets bottom="5.0" />
    </VBox.margin>
  </TextFlow>
```
###### \resources\view\PollListPanel.fxml
``` fxml
  <TextFlow styleClass="text-panel-header" textAlignment="CENTER">
    <Text text="POLLS" fill="darkblue" VBox.vgrow="NEVER" />
    <VBox.margin>
      <Insets bottom="5.0" />
    </VBox.margin>
  </TextFlow>
```
###### \resources\view\TaskListPanel.fxml
``` fxml
    <TextFlow styleClass="text-panel-header" textAlignment="CENTER">
        <Text text="TASKS" fill="darkblue" VBox.vgrow="NEVER" />
        <VBox.margin>
            <Insets bottom="5.0" />
        </VBox.margin>
    </TextFlow>
```
