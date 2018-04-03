package seedu.club.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.club.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.club.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.club.logic.commands.CommandTestUtil.GROUP_DESC_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_CLIENT;
import static seedu.club.logic.commands.CommandTestUtil.VALID_CLIENT_DESC;
import static seedu.club.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_POLL;
import static seedu.club.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.club.logic.commands.AddCommand;
import seedu.club.logic.commands.AddPollCommand;
import seedu.club.logic.commands.AddTaskCommand;
import seedu.club.logic.commands.ClearCommand;
import seedu.club.logic.commands.CompressCommand;
import seedu.club.logic.commands.DecompressCommand;
import seedu.club.logic.commands.DeleteCommand;
import seedu.club.logic.commands.DeletePollCommand;
import seedu.club.logic.commands.DeleteTaskCommand;
import seedu.club.logic.commands.EditCommand;
import seedu.club.logic.commands.EditCommand.EditMemberDescriptor;
import seedu.club.logic.commands.EmailCommand;
import seedu.club.logic.commands.ExitCommand;
import seedu.club.logic.commands.FindCommand;
import seedu.club.logic.commands.HelpCommand;
import seedu.club.logic.commands.HideResultsCommand;
import seedu.club.logic.commands.HistoryCommand;
import seedu.club.logic.commands.ListCommand;
import seedu.club.logic.commands.RedoCommand;
import seedu.club.logic.commands.RemoveGroupCommand;
import seedu.club.logic.commands.SelectCommand;
import seedu.club.logic.commands.ShowResultsCommand;
import seedu.club.logic.commands.UndoCommand;
import seedu.club.logic.commands.ViewAllTasksCommand;
import seedu.club.logic.commands.ViewMyTasksCommand;
import seedu.club.logic.parser.exceptions.ParseException;
import seedu.club.model.email.Body;
import seedu.club.model.email.Client;
import seedu.club.model.email.Subject;
import seedu.club.model.group.Group;
import seedu.club.model.member.FieldContainsKeywordsPredicate;
import seedu.club.model.member.Member;
import seedu.club.model.poll.Poll;
import seedu.club.model.task.Task;
import seedu.club.testutil.EditMemberDescriptorBuilder;
import seedu.club.testutil.MemberBuilder;
import seedu.club.testutil.MemberUtil;
import seedu.club.testutil.PollBuilder;
import seedu.club.testutil.PollUtil;
import seedu.club.testutil.TaskBuilder;
import seedu.club.testutil.TaskUtil;

public class ClubBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ClubBookParser parser = new ClubBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Member member = new MemberBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(MemberUtil.getAddCommand(member));
        assertEquals(new AddCommand(member), command);
    }

    @Test
    public void parseCommand_addPoll() throws Exception {
        Poll poll = new PollBuilder().build();
        AddPollCommand command = (AddPollCommand) parser.parseCommand(PollUtil.getAddPollCommand(poll));
        assertEquals(new AddPollCommand(poll), command);
    }

    @Test
    public void parseCommand_addTask() throws Exception {
        Task task = new TaskBuilder().build();
        AddTaskCommand command = (AddTaskCommand) parser.parseCommand(TaskUtil.getAddTaskCommand(task));
        assertEquals(new AddTaskCommand(task), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_MEMBER.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_MEMBER), command);
    }

    @Test
    public void parseCommand_deletePoll() throws Exception {
        DeletePollCommand command = (DeletePollCommand) parser.parseCommand(
                DeletePollCommand.COMMAND_WORD + " " + INDEX_FIRST_POLL.getOneBased());
        assertEquals(new DeletePollCommand(INDEX_FIRST_POLL), command);
    }

    @Test
    public void parseCommand_deleteTask() throws Exception {
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(
                DeleteTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new DeleteTaskCommand(INDEX_FIRST_TASK), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Member member = new MemberBuilder().build();
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(member).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_MEMBER.getOneBased() + " " + MemberUtil.getMemberDetails(member));
        assertEquals(new EditCommand(INDEX_FIRST_MEMBER, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        Prefix prefix = PREFIX_NAME;
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + PREFIX_NAME + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new FieldContainsKeywordsPredicate(keywords, prefix)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_viewAllTasks() throws Exception {
        assertTrue(parser.parseCommand(ViewAllTasksCommand.COMMAND_WORD) instanceof ViewAllTasksCommand);
        assertTrue(parser.parseCommand(ViewAllTasksCommand.COMMAND_WORD + " 1") instanceof ViewAllTasksCommand);
    }

    @Test
    public void parseCommand_viewMyTasks() throws Exception {
        assertTrue(parser.parseCommand(ViewMyTasksCommand.COMMAND_WORD) instanceof ViewMyTasksCommand);
        assertTrue(parser.parseCommand(ViewMyTasksCommand.COMMAND_WORD + " 1") instanceof ViewMyTasksCommand);
    }

    @Test
    public void parseCommand_email() throws Exception {
        EmailCommand command = (EmailCommand) parser.parseCommand(
                EmailCommand.COMMAND_WORD + GROUP_DESC_AMY + VALID_CLIENT_DESC);
        assertEquals(new EmailCommand(new Group(VALID_GROUP_AMY), null, new Client(VALID_CLIENT),
                new Subject(Subject.TEST_SUBJECT_STRING), new Body(Body.TEST_BODY_STRING)), command);
    }

    @Test
    public void parseCommand_removeGroup() throws Exception {
        RemoveGroupCommand removeGroupCommand = (RemoveGroupCommand) parser.parseCommand(
                RemoveGroupCommand.COMMAND_WORD + GROUP_DESC_AMY);
        assertEquals(new RemoveGroupCommand(new Group(VALID_GROUP_AMY)), removeGroupCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_MEMBER.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_MEMBER), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }

    @Test
    public void parseCommand_compress() throws Exception {
        assertTrue(parser.parseCommand(CompressCommand.COMMAND_WORD) instanceof CompressCommand);
    }

    @Test
    public void parseCommand_decompress() throws Exception {
        assertTrue(parser.parseCommand(DecompressCommand.COMMAND_WORD) instanceof DecompressCommand);
    }

    @Test
    public void parseCommand_showResults() throws Exception {
        assertTrue(parser.parseCommand(ShowResultsCommand.COMMAND_WORD) instanceof ShowResultsCommand);
    }

    @Test
    public void parseCommand_hideResults() throws Exception {
        assertTrue(parser.parseCommand(HideResultsCommand.COMMAND_WORD) instanceof HideResultsCommand);
    }
}
