package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.Note;

/**
 * Adds or updates the note for an application identified using its displayed index.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds/updates a note for the application identified by the index number "
            + "used in the displayed application list.\n"
            + "Parameters: INDEX (must be a positive integer) NOTE\n"
            + "Example: " + COMMAND_WORD + " 1 Interview at 10am on 2025-12-22.";

    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Set note for Application: %1$s";

    private final Index targetIndex;
    private final Note note;

    /**
     * @param targetIndex of the application in the filtered application list to update the note
     * @param note of the application to be updated to
     */
    public NoteCommand(Index targetIndex, Note note) {
        requireNonNull(targetIndex);
        requireNonNull(note);

        this.targetIndex = targetIndex;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredApplicationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        Application applicationToUpdate = lastShownList.get(targetIndex.getZeroBased());
        Application updatedApplication = new Application(
                applicationToUpdate.getCompany(),
                applicationToUpdate.getRole(),
                applicationToUpdate.getApplicationDate(),
                applicationToUpdate.getUrl(),
                applicationToUpdate.getStatus(),
                note);

        model.setApplication(applicationToUpdate, updatedApplication);

        return new CommandResult(String.format(MESSAGE_ADD_NOTE_SUCCESS, Messages.format(updatedApplication)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NoteCommand otherNoteCommand)) {
            return false;
        }

        return targetIndex.equals(otherNoteCommand.targetIndex)
                && note.equals(otherNoteCommand.note);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("note", note)
                .toString();
    }
}
