package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedApplication.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.Company;
import seedu.address.model.application.Role;
import seedu.address.model.application.Url;


public class JsonAdaptedApplicationTest {
    private static final String INVALID_COMPANY = "R@chel";
    private static final String INVALID_ROLE = "@Engineer";
    private static final String INVALID_URL = "invalid_url";
    private static final String INVALID_APPLICATION_DATE = "2026/03/09";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_COMPANY = BENSON.getCompany().toString();
    private static final String VALID_ROLE = BENSON.getRole().toString();
    private static final String VALID_APPLICATION_DATE = BENSON.getApplicationDate().toString();
    private static final String VALID_URL = BENSON.getUrl().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validApplicationDetails_returnsApplication() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(BENSON);
        assertEquals(BENSON, application.toModelType());
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(INVALID_COMPANY, VALID_ROLE, VALID_APPLICATION_DATE,
                        VALID_URL, VALID_TAGS);
        String expectedMessage = Company.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(null, VALID_ROLE, VALID_APPLICATION_DATE,
                VALID_URL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY, INVALID_ROLE, VALID_APPLICATION_DATE,
                        VALID_URL, VALID_TAGS);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_COMPANY, null, VALID_APPLICATION_DATE,
                VALID_URL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidApplicationDate_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY, VALID_ROLE, INVALID_APPLICATION_DATE,
                        VALID_URL, VALID_TAGS);
        String expectedMessage = ApplicationDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullApplicationDate_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_COMPANY, VALID_ROLE, null,
                VALID_URL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ApplicationDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidUrl_throwsIllegalValueException() {
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY, VALID_ROLE, VALID_APPLICATION_DATE,
                        INVALID_URL, VALID_TAGS);
        String expectedMessage = Url.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedApplication application =
                new JsonAdaptedApplication(VALID_COMPANY, VALID_ROLE, VALID_APPLICATION_DATE,
                        VALID_URL, invalidTags);
        assertThrows(IllegalValueException.class, application::toModelType);
    }

}
