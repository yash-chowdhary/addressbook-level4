package seedu.club.logic;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.club.commons.core.ComponentManager;
import seedu.club.commons.core.LogsCenter;
import seedu.club.logic.commands.Command;
import seedu.club.logic.commands.CommandResult;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.logic.parser.ClubBookParser;
import seedu.club.logic.parser.CommandList;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.Model;
import seedu.club.model.member.Member;
import seedu.club.model.poll.Poll;
import seedu.club.model.task.Task;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    public static final List<String> commandList = new CommandList().getCommandList();

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final ClubBookParser clubBookParser;
    private final UndoRedoStack undoRedoStack;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        clubBookParser = new ClubBookParser();
        undoRedoStack = new UndoRedoStack();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = clubBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Member> getFilteredMemberList() {
        return model.getFilteredMemberList();
    }

    @Override
    public ObservableList<Poll> getFilteredPollList() {
        return model.getFilteredPollList();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
