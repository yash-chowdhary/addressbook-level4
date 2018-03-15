package seedu.club.ui;

import static org.junit.Assert.assertEquals;
import static seedu.club.testutil.EventsUtil.postNow;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.club.testutil.TypicalPersons.getTypicalPersons;
import static seedu.club.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.club.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.club.commons.events.ui.JumpToListRequestEvent;
import seedu.club.model.Member.Member;

public class MemberListPanelTest extends GuiUnitTest {
    private static final ObservableList<Member> TYPICAL_MEMBERS =
            FXCollections.observableList(getTypicalPersons());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private PersonListPanelHandle personListPanelHandle;

    @Before
    public void setUp() {
        PersonListPanel personListPanel = new PersonListPanel(TYPICAL_MEMBERS);
        uiPartRule.setUiPart(personListPanel);

        personListPanelHandle = new PersonListPanelHandle(getChildNode(personListPanel.getRoot(),
                PersonListPanelHandle.PERSON_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_MEMBERS.size(); i++) {
            personListPanelHandle.navigateToCard(TYPICAL_MEMBERS.get(i));
            Member expectedMember = TYPICAL_MEMBERS.get(i);
            PersonCardHandle actualCard = personListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedMember, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        PersonCardHandle expectedCard = personListPanelHandle.getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        PersonCardHandle selectedCard = personListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
