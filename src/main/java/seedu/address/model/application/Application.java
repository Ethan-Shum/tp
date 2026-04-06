package seedu.address.model.application;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Application in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Application {

    // Identity fields
    private final Company company;
    private final Role role;
    private final ApplicationDate applicationDate;

    // Data fields
    private final Optional<Url> url;
    private final Status status;
    private final Note note;

    /**
     * Creates an {@code Application} with an empty note.
     */
    public Application(Company company, Role role, ApplicationDate applicationDate, Optional<Url> url, Status status) {
        this(company, role, applicationDate, url, status, Note.EMPTY);
    }

    /**
     * Creates an {@code Application} with all fields specified.
     */
    public Application(Company company, Role role, ApplicationDate applicationDate,
            Optional<Url> url, Status status, Note note) {
        requireAllNonNull(company, role, applicationDate, url, status, note);
        this.company = company;
        this.role = role;
        this.applicationDate = applicationDate;
        this.url = url;
        this.status = status;
        this.note = note;
    }

    public Company getCompany() {
        return company;
    }

    public Role getRole() {
        return role;
    }

    public ApplicationDate getApplicationDate() {
        return applicationDate;
    }

    public Optional<Url> getUrl() {
        return url;
    }

    public Status getStatus() {
        return status;
    }

    public Note getNote() {
        return note;
    }

    /**
     * Returns true if the application has a terminal status (Rejected or Withdrawn).
     * Terminal statuses indicate the application process has ended.
     */
    public boolean hasTerminalStatus() {
        return status == Status.REJECTED || status == Status.WITHDRAWN;
    }

    /**
     * Returns true if both applications share the same identity fields.
     * Identity is defined by company (case-insensitive) and role (case-insensitive).
     */
    public boolean isSameApplication(Application otherApplication) {
        if (otherApplication == this) {
            return true;
        }

        return otherApplication != null
                && otherApplication.getCompany().value.equalsIgnoreCase(getCompany().value)
                && otherApplication.getRole().value.equalsIgnoreCase(getRole().value)
                && applicationDate.equals(otherApplication.applicationDate);
    }

    /**
     * Returns true if both applications have the same identity and data fields.
     * This defines a stronger notion of equality than {@link #isSameApplication(Application)}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Application)) {
            return false;
        }

        Application otherApplication = (Application) other;
        return company.value.equalsIgnoreCase(otherApplication.company.value)
                && role.value.equalsIgnoreCase(otherApplication.role.value)
                && applicationDate.equals(otherApplication.applicationDate)
                && url.equals(otherApplication.url)
                && status.equals(otherApplication.status)
                && note.equals(otherApplication.note);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(company, role, applicationDate, url, status, note);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("company", company)
                .add("role", role)
                .add("applicationDate", applicationDate)
                .add("url", url)
                .add("status", status)
                .add("note", note)
                .toString();
    }

}
