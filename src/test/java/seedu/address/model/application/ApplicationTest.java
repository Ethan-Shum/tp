package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static seedu.address.logic.commands.CommandTestUtil.VALID_APPLICATION_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_BOB;
import seedu.address.testutil.ApplicationBuilder;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.ALICE;
import static seedu.address.testutil.TypicalApplications.BOB;

public class ApplicationTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Application application = new ApplicationBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> application.getTags().remove(0));
    }

    @Test
    public void isSameApplication() {
        // same object -> returns true
        assertTrue(ALICE.isSameApplication(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameApplication(null));

        // same company and role, all other attributes different -> returns true
        Application editedAlice = new ApplicationBuilder(ALICE).withApplicationDate(VALID_APPLICATION_DATE_BOB)
                .withUrl(VALID_URL_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameApplication(editedAlice));

        // different company, all other attributes same -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(ALICE.isSameApplication(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Application editedBob = new ApplicationBuilder(BOB).withCompany(VALID_COMPANY_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameApplication(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_COMPANY_BOB + " ";
        editedBob = new ApplicationBuilder(BOB).withCompany(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameApplication(editedBob));
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
        Application editedAlice = new ApplicationBuilder(ALICE).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different role -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withRole(VALID_ROLE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different application date -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withApplicationDate(VALID_APPLICATION_DATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different url -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withUrl(VALID_URL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ApplicationBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Application.class.getCanonicalName() + "{company=" + ALICE.getCompany()
                + ", role=" + ALICE.getRole()
                + ", applicationDate=" + ALICE.getApplicationDate() + ", url=" + ALICE.getUrl()
                + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
