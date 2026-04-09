package seedu.address.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.Company;
import seedu.address.model.application.Note;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.application.Url;

/**
 * Jackson-friendly version of {@link Application}.
 */
class JsonAdaptedApplication {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Application's %s field is missing!";

    private final String company;
    private final String role;
    private final String applicationDate;
    private final String url;
    private final String status;
    private final String note;

    /**
     * Constructs a {@code JsonAdaptedApplication} with the given application details.
     */
    @JsonCreator
    public JsonAdaptedApplication(@JsonProperty("company") String company, @JsonProperty("role") String role,
                                  @JsonProperty("applicationDate") String applicationDate,
                                  @JsonProperty("url") String url,
                                  @JsonProperty("status") String status,
                                  @JsonProperty("note") String note) {
        this.company = company;
        this.role = role;
        this.applicationDate = applicationDate;
        this.url = url;
        this.status = status;
        this.note = note;
    }

    /**
     * Converts a given {@code Application} into this class for Jackson use.
     */
    public JsonAdaptedApplication(Application source) {
        company = source.getCompany().value;
        role = source.getRole().value;
        applicationDate = source.getApplicationDate().toString();
        url = source.getUrl().map(u -> u.value).orElse(null);
        status = source.getStatus().toString();
        note = source.getNote().value;
    }

    /**
     * Converts this Jackson-friendly adapted application object into the model's {@code Application} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted application.
     */
    public Application toModelType() throws IllegalValueException {
        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName()));
        }
        if (!Company.isValidCompany(company)) {
            throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
        }
        final Company modelCompany = new Company(company);

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }
        if (!Role.isValidRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        final Role modelRole = new Role(role);

        if (applicationDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ApplicationDate.class.getSimpleName()));
        }
        if (!ApplicationDate.isValidApplicationDate(applicationDate)) {
            throw new IllegalValueException(ApplicationDate.MESSAGE_CONSTRAINTS);
        }
        final ApplicationDate modelApplicationDate = new ApplicationDate(applicationDate);

        Optional<Url> modelUrl = Optional.empty();
        if (url != null) {
            if (!Url.isValidUrl(url)) {
                throw new IllegalValueException(Url.MESSAGE_CONSTRAINTS);
            }
            modelUrl = Optional.of(new Url(url));
        }

        final Status modelStatus;
        if (status == null) {
            modelStatus = Status.DEFAULT;
        } else {
            try {
                modelStatus = Status.fromUserInput(status);
            } catch (IllegalArgumentException e) {
                throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
            }
        }

        final Note modelNote;
        if (note == null) {
            modelNote = Note.EMPTY;
        } else {
            if (note.length() > Note.MAX_LENGTH) {
                throw new IllegalValueException(Note.MESSAGE_LENGTH_CONSTRAINTS);
            }
            if (!note.isEmpty() && !Note.hasValidCharacters(note)) {
                throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
            }
            modelNote = new Note(note);
        }

        return new Application(modelCompany, modelRole, modelApplicationDate, modelUrl, modelStatus, modelNote);
    }

}
