package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_URL;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.Company;
import seedu.address.model.application.Note;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.application.Url;

/**
 * Edits the details of an existing application in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the application identified "
            + "by the index number used in the displayed application list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_COMPANY + "COMPANY] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_APPLICATION_DATE + "APPLICATION_DATE] "
            + "[" + PREFIX_URL + "URL] "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ROLE + "Software Engineer Intern "
            + PREFIX_APPLICATION_DATE + "2025-12-22 "
            + PREFIX_URL + "https://www.example.com";

    public static final String MESSAGE_EDIT_APPLICATION_SUCCESS = "Edited Application: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_APPLICATION = "This application already exists in the address book.";

    private final Index index;
    private final EditApplicationDescriptor editApplicationDescriptor;
    private final String deferredErrorMessage;

    /**
     * @param index of the application in the filtered application list to edit
     * @param editApplicationDescriptor details to edit the application with
     */
    public EditCommand(Index index, EditApplicationDescriptor editApplicationDescriptor) {
        this(index, editApplicationDescriptor, null);
    }

    /**
     * @param index of the application in the filtered application list to edit
     * @param editApplicationDescriptor details to edit the application with
     * @param deferredErrorMessage error message to throw after bounds check, or null if none
     */
    public EditCommand(Index index, EditApplicationDescriptor editApplicationDescriptor, String deferredErrorMessage) {
        requireNonNull(index);
        requireNonNull(editApplicationDescriptor);

        this.index = index;
        this.editApplicationDescriptor = new EditApplicationDescriptor(editApplicationDescriptor);
        this.deferredErrorMessage = deferredErrorMessage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredApplicationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        if (deferredErrorMessage != null) {
            throw new CommandException(deferredErrorMessage);
        }

        Application applicationToEdit = lastShownList.get(index.getZeroBased());
        Application editedApplication = createEditedApplication(applicationToEdit, editApplicationDescriptor);

        if (!applicationToEdit.isSameApplication(editedApplication) && model.hasApplication(editedApplication)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPLICATION);
        }

        model.setApplication(applicationToEdit, editedApplication);
        return new CommandResult(String.format(MESSAGE_EDIT_APPLICATION_SUCCESS, Messages.format(editedApplication)));
    }

    /**
     * Creates and returns a {@code Application} with the details of {@code applicationToEdit}
     * edited with {@code editApplicationDescriptor}.
     */
    private static Application createEditedApplication(Application applicationToEdit,
                                                       EditApplicationDescriptor editApplicationDescriptor) {
        assert applicationToEdit != null;

        Company updatedCompany = editApplicationDescriptor.getCompany().orElse(applicationToEdit.getCompany());
        Role updatedRole = editApplicationDescriptor.getRole().orElse(applicationToEdit.getRole());
        ApplicationDate updatedApplicationDate = editApplicationDescriptor.getApplicationDate().orElse(
                applicationToEdit.getApplicationDate());
        Optional<Url> updatedUrl = editApplicationDescriptor.getUrl().isPresent()
                ? editApplicationDescriptor.getUrl()
                : applicationToEdit.getUrl();
        Status updatedStatus = editApplicationDescriptor.getStatus().orElse(applicationToEdit.getStatus());
        Note updatedNote = applicationToEdit.getNote(); //edit command does not allow editing notes

        return new Application(updatedCompany, updatedRole, updatedApplicationDate, updatedUrl, updatedStatus,
                updatedNote);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editApplicationDescriptor.equals(otherEditCommand.editApplicationDescriptor)
                && Objects.equals(deferredErrorMessage, otherEditCommand.deferredErrorMessage);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editApplicationDescriptor", editApplicationDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the application with. Each non-empty field value will replace the
     * corresponding field value of the application.
     */
    public static class EditApplicationDescriptor {
        private Company company;
        private Role role;
        private ApplicationDate applicationDate;
        private Url url;
        private Status status;

        public EditApplicationDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditApplicationDescriptor(EditApplicationDescriptor toCopy) {
            setCompany(toCopy.company);
            setRole(toCopy.role);
            setApplicationDate(toCopy.applicationDate);
            setUrl(toCopy.url);
            setStatus(toCopy.status);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(company, role, applicationDate, url, status);
        }

        public void setCompany(Company company) {
            this.company = company;
        }

        public Optional<Company> getCompany() {
            return Optional.ofNullable(company);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        public void setApplicationDate(ApplicationDate applicationDate) {
            this.applicationDate = applicationDate;
        }

        public Optional<ApplicationDate> getApplicationDate() {
            return Optional.ofNullable(applicationDate);
        }

        public void setUrl(Url url) {
            this.url = url;
        }

        public Optional<Url> getUrl() {
            return Optional.ofNullable(url);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditApplicationDescriptor)) {
                return false;
            }

            EditApplicationDescriptor otherEditApplicationDescriptor = (EditApplicationDescriptor) other;
            return Objects.equals(company, otherEditApplicationDescriptor.company)
                    && Objects.equals(role, otherEditApplicationDescriptor.role)
                    && Objects.equals(applicationDate, otherEditApplicationDescriptor.applicationDate)
                    && Objects.equals(url, otherEditApplicationDescriptor.url)
                    && Objects.equals(status, otherEditApplicationDescriptor.status);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("company", company)
                    .add("role", role)
                    .add("applicationDate", applicationDate)
                    .add("url", url)
                    .add("status", status)
                    .toString();
        }
    }
}
