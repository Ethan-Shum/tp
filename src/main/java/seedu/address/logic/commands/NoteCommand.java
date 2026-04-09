package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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

    private static final Logger logger = LogsCenter.getLogger(NoteCommand.class);

    private final Index targetIndex;
    private final Note note;
    private final boolean hasNoteText;

    /**
     * @param targetIndex of the application in the filtered application list to update the note
     * @param note of the application to be updated to
     */
    public NoteCommand(Index targetIndex, Note note) {
        requireNonNull(targetIndex);
        requireNonNull(note);
        this.targetIndex = targetIndex;
        this.note = note;
        this.hasNoteText = true;
    }

    /**
     * Creates a command with missing note text so execute() can validate index first.
     */
    public static NoteCommand withoutNote(Index targetIndex) {
        requireNonNull(targetIndex);
        return new NoteCommand(targetIndex, Note.EMPTY, false);
    }

    private NoteCommand(Index targetIndex, Note note, boolean hasNoteText) {
        this.targetIndex = targetIndex;
        this.note = note;
        this.hasNoteText = hasNoteText;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Application> displayedApplications = model.getFilteredApplicationList();
        Application applicationToUpdate = getTargetApplication(displayedApplications);

        if (!hasNoteText) {
            logger.info(() -> "Rejected note update due to missing note text at index "
                    + targetIndex.getOneBased());
            throw new CommandException(Note.MESSAGE_EMPTY_NOTE);
        }

        Application updatedApplication = createUpdatedApplication(applicationToUpdate);

        model.setApplication(applicationToUpdate, updatedApplication);
        logger.info(() -> "Updated note for application at index " + targetIndex.getOneBased());

        return new CommandResult(String.format(MESSAGE_ADD_NOTE_SUCCESS, Messages.format(updatedApplication)));
    }

    private Application getTargetApplication(List<Application> displayedApplications) throws CommandException {
        assert displayedApplications != null : "Displayed applications should never be null";

        if (isTargetIndexOutOfBounds(displayedApplications.size())) {
            logger.info(() -> "Rejected note update due to invalid index: " + targetIndex.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        return displayedApplications.get(targetIndex.getZeroBased());
    }

    private boolean isTargetIndexOutOfBounds(int displayedListSize) {
        return targetIndex.getZeroBased() >= displayedListSize;
    }

    private Application createUpdatedApplication(Application applicationToUpdate) {
        Application updatedApplication = new Application(
                applicationToUpdate.getCompany(),
                applicationToUpdate.getRole(),
                applicationToUpdate.getApplicationDate(),
                applicationToUpdate.getUrl(),
                applicationToUpdate.getStatus(),
                note);
        assert updatedApplication.getNote().equals(note) : "Updated application should carry the new note";
        return updatedApplication;
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
                && note.equals(otherNoteCommand.note)
                && hasNoteText == otherNoteCommand.hasNoteText;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("note", note)
                .add("hasNoteText", hasNoteText)
                .toString();
    }
}
