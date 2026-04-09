package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedApplication.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.Company;
import seedu.address.model.application.Note;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.application.Url;

public class JsonAdaptedApplicationTest {
    private static final String INVALID_COMPANY = "";
    private static final String INVALID_ROLE = "";
    private static final String INVALID_URL = "invalid_url";
    private static final String INVALID_APPLICATION_DATE = "2026/03/09";
    private static final String INVALID_STATUS = "Pending";
    private static final String INVALID_NOTE = "a".repeat(501);
    private static final String INVALID_NOTE_CHARACTERS = "hello🙂";

    private static final String VALID_COMPANY = BENSON.getCompany().toString();
    private static final String VALID_ROLE = BENSON.getRole().toString();
    private static final String VALID_APPLICATION_DATE = BENSON.getApplicationDate().toString();
    private static final String VALID_URL = BENSON.getUrl().map(Url::toString).orElse(null);
    private static final String VALID_STATUS = BENSON.getStatus().toString();
    private static final String VALID_NOTE = BENSON.getNote().toString();

    @Test
    public void toModelType_validApplicationDetails_returnsApplication() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(BENSON);
        assertEquals(BENSON, application.toModelType());
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(INVALID_COMPANY, VALID_ROLE, VALID_APPLICATION_DATE,
                        VALID_URL, VALID_STATUS, VALID_NOTE);
        String expectedMessage = Company.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(null, VALID_ROLE, VALID_APPLICATION_DATE,
                VALID_URL, VALID_STATUS, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY, INVALID_ROLE, VALID_APPLICATION_DATE,
                        VALID_URL, VALID_STATUS, VALID_NOTE);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_COMPANY, null, VALID_APPLICATION_DATE,
                VALID_URL, VALID_STATUS, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidApplicationDate_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY, VALID_ROLE, INVALID_APPLICATION_DATE,
                        VALID_URL, VALID_STATUS, VALID_NOTE);
        String expectedMessage = ApplicationDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullApplicationDate_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_COMPANY, VALID_ROLE, null,
                VALID_URL, VALID_STATUS, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ApplicationDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidUrl_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY, VALID_ROLE, VALID_APPLICATION_DATE,
                        INVALID_URL, VALID_STATUS, VALID_NOTE);
        String expectedMessage = Url.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY, VALID_ROLE, VALID_APPLICATION_DATE,
                        VALID_URL, INVALID_STATUS, VALID_NOTE);
        assertThrows(IllegalValueException.class, Status.MESSAGE_CONSTRAINTS, application::toModelType);
    }

    @Test
    public void toModelType_invalidNote_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY, VALID_ROLE, VALID_APPLICATION_DATE,
                        VALID_URL, VALID_STATUS, INVALID_NOTE);
        assertThrows(IllegalValueException.class, Note.MESSAGE_LENGTH_CONSTRAINTS, application::toModelType);
    }

    @Test
    public void toModelType_invalidNoteCharacters_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY, VALID_ROLE, VALID_APPLICATION_DATE,
                        VALID_URL, VALID_STATUS, INVALID_NOTE_CHARACTERS);
        assertThrows(IllegalValueException.class, Note.MESSAGE_CONSTRAINTS, application::toModelType);
    }

    @Test
    public void toModelType_nullStatus_defaultsToApplied() throws Exception {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY, VALID_ROLE, VALID_APPLICATION_DATE,
                        VALID_URL, null, VALID_NOTE);
        assertEquals(Status.DEFAULT, application.toModelType().getStatus());
    }

    @Test
    public void toModelType_nullNote_defaultsToEmpty() throws Exception {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY, VALID_ROLE, VALID_APPLICATION_DATE,
                        VALID_URL, VALID_STATUS, null);
        assertEquals(Note.EMPTY, application.toModelType().getNote());
    }
}
