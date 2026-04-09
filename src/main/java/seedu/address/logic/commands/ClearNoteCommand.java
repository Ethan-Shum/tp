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
 * Clears the note for an application identified using its displayed index.
 */
public class ClearNoteCommand extends Command {

    public static final String COMMAND_WORD = "clearnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears the note for the application identified by the index number "
            + "used in the displayed application list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_CLEAR_NOTE_SUCCESS = "Cleared note for Application: %1$s";

    public static final String MESSAGE_APPLICATION_NOTE_ALREADY_EMPTY = "The note for this application is empty.";

    private final Index targetIndex;

    /**
     * @param targetIndex of the application in the filtered application list to clear the note for
     */
    public ClearNoteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredApplicationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        Application applicationToUpdate = lastShownList.get(targetIndex.getZeroBased());

        if (applicationToUpdate.getNote().equals(Note.EMPTY)) {
            throw new CommandException(MESSAGE_APPLICATION_NOTE_ALREADY_EMPTY);
        }

        Application updatedApplication = new Application(
                applicationToUpdate.getCompany(),
                applicationToUpdate.getRole(),
                applicationToUpdate.getApplicationDate(),
                applicationToUpdate.getUrl(),
                applicationToUpdate.getStatus(),
                Note.EMPTY);

        model.setApplication(applicationToUpdate, updatedApplication);

        return new CommandResult(String.format(MESSAGE_CLEAR_NOTE_SUCCESS, Messages.format(updatedApplication)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ClearNoteCommand)) {
            return false;
        }

        ClearNoteCommand otherClearNoteCommand = (ClearNoteCommand) other;
        return targetIndex.equals(otherClearNoteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
