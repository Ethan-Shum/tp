package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void isValidStatus() {
        assertFalse(Status.isValidStatus(null));

        assertFalse(Status.isValidStatus(""));
        assertFalse(Status.isValidStatus("Pending"));

        assertTrue(Status.isValidStatus("Applied"));
        assertTrue(Status.isValidStatus("applied"));
        assertTrue(Status.isValidStatus(" OA "));
    }

    @Test
    public void fromUserInput_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Status.fromUserInput(null));
    }

    @Test
    public void fromUserInput_validInputs() {
        assertEquals(Status.APPLIED, Status.fromUserInput("Applied"));
        assertEquals(Status.OA, Status.fromUserInput("OA"));
        assertEquals(Status.INTERVIEW, Status.fromUserInput("Interview"));
        assertEquals(Status.OFFERED, Status.fromUserInput("Offered"));
        assertEquals(Status.REJECTED, Status.fromUserInput("Rejected"));
        assertEquals(Status.WITHDRAWN, Status.fromUserInput("Withdrawn"));
    }

    @Test
    public void fromUserInput_caseInsensitive() {
        assertEquals(Status.APPLIED, Status.fromUserInput("applied"));
        assertEquals(Status.APPLIED, Status.fromUserInput("APPLIED"));
        assertEquals(Status.APPLIED, Status.fromUserInput("ApPlIeD"));
        assertEquals(Status.OA, Status.fromUserInput("oa"));
        assertEquals(Status.OA, Status.fromUserInput("Oa"));
    }

    @Test
    public void fromUserInput_whitespaceHandling() {
        assertEquals(Status.APPLIED, Status.fromUserInput("  Applied  "));
        assertEquals(Status.INTERVIEW, Status.fromUserInput("\tInterview\n"));
    }

    @Test
    public void fromUserInput_invalidInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Status.fromUserInput("Invalid"));
        assertThrows(IllegalArgumentException.class, () -> Status.fromUserInput(""));
        assertThrows(IllegalArgumentException.class, () -> Status.fromUserInput("Pending"));
    }

    @Test
    public void toString_correctFormat() {
        assertEquals("Applied", Status.APPLIED.toString());
        assertEquals("OA", Status.OA.toString());
        assertEquals("Interview", Status.INTERVIEW.toString());
        assertEquals("Offered", Status.OFFERED.toString());
        assertEquals("Rejected", Status.REJECTED.toString());
        assertEquals("Withdrawn", Status.WITHDRAWN.toString());
    }

    @Test
    public void getNextStatus_validTransitions() {
        assertEquals(Status.OA, Status.APPLIED.getNextStatus());
        assertEquals(Status.INTERVIEW, Status.OA.getNextStatus());
        assertEquals(Status.OFFERED, Status.INTERVIEW.getNextStatus());
        assertEquals(Status.REJECTED, Status.OFFERED.getNextStatus());
        assertEquals(Status.WITHDRAWN, Status.REJECTED.getNextStatus());
        assertEquals(Status.APPLIED, Status.WITHDRAWN.getNextStatus());
    }

    @Test
    public void getNextStatus_cyclesCorrectly() {
        Status current = Status.APPLIED;
        current = current.getNextStatus();
        assertEquals(Status.OA, current);
        current = current.getNextStatus();
        assertEquals(Status.INTERVIEW, current);
        current = current.getNextStatus();
        assertEquals(Status.OFFERED, current);
        current = current.getNextStatus();
        assertEquals(Status.REJECTED, current);
        current = current.getNextStatus();
        assertEquals(Status.WITHDRAWN, current);
        current = current.getNextStatus();
        assertEquals(Status.APPLIED, current);
    }
}
