package seedu.club.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.club.model.Member.Member;
import seedu.club.testutil.PersonBuilder;

public class MemberCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Member memberWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(memberWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, memberWithNoTags, 1);

        // with tags
        Member memberWithTags = new PersonBuilder().build();
        personCard = new PersonCard(memberWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, memberWithTags, 2);
    }

    @Test
    public void equals() {
        Member member = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(member, 0);

        // same Member, same index -> returns true
        PersonCard copy = new PersonCard(member, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different Member, same index -> returns false
        Member differentMember = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentMember, 0)));

        // same Member, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(member, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedMember} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Member expectedMember, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify Member details are displayed correctly
        assertCardDisplaysPerson(expectedMember, personCardHandle);
    }
}
