package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.core.Messages;
import seedu.club.commons.events.ui.ClearMemberSelectPanelEvent;
import seedu.club.commons.events.ui.UpdateCurrentlyLogInMemberEvent;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.ClubBook;
//@@author th14thmusician
/**
 * Clears the club book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Club Connect has been cleared.";
    public static final String MESSAGE_FAILURE = "Action to clear Club Connect data has been cancelled.";
    public static final String MESSAGE_CONFRIMATION = "Are you sure that you want to clear all data in Club Connect?"
            + " Enter 'clear Y' to confirm and 'clear N' to cancel."
            + " \nWARNING: THIS CANNOT BE UNDONE!";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " Y/N";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "c", "erase")
    );
    private String args;

    public ClearCommand() {
    }

    public ClearCommand(String args) {
        this.args = args;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        if (!model.getClearConfirmation()) {
            model.setClearConfirmation(true);
            return new CommandResult(MESSAGE_CONFRIMATION);
        } else {
            if (args == null) {
                return new CommandResult(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, COMMAND_FORMAT));
            } else if (args.equalsIgnoreCase(" Y")) {
                model.resetData(new ClubBook());
                model.clearClubBook();
                EventsCenter.getInstance().post(new ClearMemberSelectPanelEvent(true));
                EventsCenter.getInstance().post(new UpdateCurrentlyLogInMemberEvent(null));
                return new CommandResult(MESSAGE_SUCCESS);
            } else {
                model.setClearConfirmation(false);
                return new CommandResult(MESSAGE_FAILURE);
            }
        }
    }
}
//@@author
