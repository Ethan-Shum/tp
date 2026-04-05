package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPLICATION_DATE_BYTEDANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BYTEDANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BYTEDANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BYTEDANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_BYTEDANCE;
import static seedu.address.testutil.TypicalApplications.ALICE;
import static seedu.address.testutil.TypicalApplications.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ApplicationBuilder;

public class ApplicationTest {

    @Test
    public void isSameApplication() {
        // same object -> returns true
        assertTrue(ALICE.isSameApplication(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameApplication(null));

        // same company and role, different application date -> returns false
        Application editedAlice = new ApplicationBuilder(ALICE).withApplicationDate(VALID_APPLICATION_DATE_BYTEDANCE)
                .withUrl(VALID_URL_BYTEDANCE).withStatus(VALID_STATUS_BYTEDANCE).build();
        assertFalse(ALICE.isSameApplication(editedAlice));

        // different company, all other attributes same -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withCompany(VALID_COMPANY_BYTEDANCE).build();
        assertFalse(ALICE.isSameApplication(editedAlice));

        // name differs in case, all other attributes same -> returns true (case-insensitive)
        Application editedBob = new ApplicationBuilder(BOB).withCompany(VALID_COMPANY_BYTEDANCE.toLowerCase()).build();
        assertTrue(BOB.isSameApplication(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_COMPANY_BYTEDANCE + " ";
        editedBob = new ApplicationBuilder(BOB).withCompany(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameApplication(editedBob));

        // same company and role, note differs -> returns true (note is not part of identity)
        editedAlice = new ApplicationBuilder(ALICE).withNote("new note").build();
        assertTrue(ALICE.isSameApplication(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Application aliceCopy = new ApplicationBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different application -> returns false
        assertFalse(ALICE.equals(BOB));

        // different company -> returns false
        Application editedAlice = new ApplicationBuilder(ALICE).withCompany(VALID_COMPANY_BYTEDANCE).build();
        assertFalse(ALICE.equals(editedAlice));

        // different role -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withRole(VALID_ROLE_BYTEDANCE).build();
        assertFalse(ALICE.equals(editedAlice));

        // different application date -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withApplicationDate(VALID_APPLICATION_DATE_BYTEDANCE).build();
        assertFalse(ALICE.equals(editedAlice));

        // different url -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withUrl(VALID_URL_BYTEDANCE).build();
        assertFalse(ALICE.equals(editedAlice));

        // different status -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withStatus(VALID_STATUS_BYTEDANCE).build();
        assertFalse(ALICE.equals(editedAlice));

        // different note -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withNote("follow up soon").build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Application.class.getCanonicalName() + "{company=" + ALICE.getCompany()
                + ", role=" + ALICE.getRole()
                + ", applicationDate=" + ALICE.getApplicationDate() + ", url=" + ALICE.getUrl()
                + ", status=" + ALICE.getStatus() + ", note=" + ALICE.getNote() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hasTerminalStatus() {
        // application with Rejected status -> returns true
        Application rejectedApp = new ApplicationBuilder(ALICE).withStatus("Rejected").build();
        assertTrue(rejectedApp.hasTerminalStatus());

        // application with Withdrawn status -> returns true
        Application withdrawnApp = new ApplicationBuilder(ALICE).withStatus("Withdrawn").build();
        assertTrue(withdrawnApp.hasTerminalStatus());

        // application with Applied status -> returns false
        Application appliedApp = new ApplicationBuilder(ALICE).withStatus("Applied").build();
        assertFalse(appliedApp.hasTerminalStatus());

        // application with Interview status -> returns false
        Application interviewApp = new ApplicationBuilder(ALICE).withStatus("Interview").build();
        assertFalse(interviewApp.hasTerminalStatus());

        // application with Offered status -> returns false
        Application offeredApp = new ApplicationBuilder(ALICE).withStatus("Offered").build();
        assertFalse(offeredApp.hasTerminalStatus());
    }
}
