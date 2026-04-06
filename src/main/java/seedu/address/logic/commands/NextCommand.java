package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.Status;

/**
 * Increments the status of an application identified using its displayed index from the address book.
 */
public class NextCommand extends Command {

    public static final String COMMAND_WORD = "next";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Increments the status of the application identified by the index number "
            + "used in the displayed application list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NEXT_APPLICATION_SUCCESS = "Updated Application Status: %1$s";

    private final Index targetIndex;

    public NextCommand(Index targetIndex) {
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
        Status nextStatus = applicationToUpdate.getStatus().getNextStatus();
        Application updatedApplication = new Application(applicationToUpdate.getCompany(),
                applicationToUpdate.getRole(), applicationToUpdate.getApplicationDate(),
                applicationToUpdate.getUrl(), nextStatus, applicationToUpdate.getNote());

        model.setApplication(applicationToUpdate, updatedApplication);
        return new CommandResult(String.format(MESSAGE_NEXT_APPLICATION_SUCCESS,
                Messages.format(updatedApplication)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NextCommand)) {
            return false;
        }

        NextCommand otherNextCommand = (NextCommand) other;
        return targetIndex.equals(otherNextCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
