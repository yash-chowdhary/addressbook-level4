package seedu.club.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.club.logic.commands.CommandTestUtil.INVALID_MATRIC_NUMBER;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_AMY;
import static seedu.club.logic.commands.CommandTestUtil.VALID_MATRIC_NUMBER_BOB;

import org.junit.Test;

import seedu.club.commons.exceptions.IllegalValueException;
import seedu.club.model.member.MatricNumber;
import seedu.club.testutil.Assert;

public class XmlAdaptedMatricNumberTest {
    @Test
    public void toModelType_validMatricNumber_returnsMatricNumber() throws Exception {
        MatricNumber matricNumber = new MatricNumber(VALID_MATRIC_NUMBER);
        XmlAdaptedMatricNumber xmlAdaptedMatricNumber = new XmlAdaptedMatricNumber(matricNumber);
        assertEquals(matricNumber, xmlAdaptedMatricNumber.toModelType());
    }

    @Test
    public void toModelType_invalidMatricNumber_throwsIllegalValueException() {
        XmlAdaptedMatricNumber matricNumber =
                new XmlAdaptedMatricNumber(INVALID_MATRIC_NUMBER);
        String expectedMessage = MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, matricNumber::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedMatricNumber xmlAdaptedMatricNumberAmy = new XmlAdaptedMatricNumber(VALID_MATRIC_NUMBER_AMY);
        XmlAdaptedMatricNumber xmlAdaptedMatricNumberBob = new XmlAdaptedMatricNumber(VALID_MATRIC_NUMBER_BOB);

        assertEquals(xmlAdaptedMatricNumberAmy, xmlAdaptedMatricNumberAmy);

        assertNotEquals(xmlAdaptedMatricNumberAmy, xmlAdaptedMatricNumberBob);
    }
}
