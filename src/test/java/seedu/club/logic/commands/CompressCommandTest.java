package seedu.club.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import seedu.club.commons.events.ui.CompressMembersRequestEvent;
import seedu.club.ui.testutil.EventsCollectorRule;

public class CompressCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new CompressCommand().execute();
        assertEquals(CompressCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof CompressMembersRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
