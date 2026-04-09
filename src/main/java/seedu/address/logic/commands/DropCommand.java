package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Application;

/**
 * Deletes all applications in the current filtered list with terminal statuses (Rejected or Withdrawn).
 */
public class DropCommand extends Command {

    public static final String COMMAND_WORD = "drop";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all applications with status Rejected or Withdrawn.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_DROP_APPLICATIONS_SUCCESS =
            "Dropped %1$d \"REJECTED\"/\"WITHDRAWN\" application(s).";
    public static final String MESSAGE_NO_REJECTED_WITHDRAWN_IN_CURRENT_LIST =
            "No \"REJECTED\" or \"WITHDRAWN\" applications in the current list.";

    private static final Logger logger = LogsCenter.getLogger(DropCommand.class);

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Application> applicationsToDrop = findApplicationsToDrop(model);
        if (applicationsToDrop.isEmpty()) {
            throw new CommandException(MESSAGE_NO_REJECTED_WITHDRAWN_IN_CURRENT_LIST);
        }

        deleteApplications(model, applicationsToDrop);
        logger.info(() -> String.format("Dropped %d terminal-status application(s).", applicationsToDrop.size()));

        return new CommandResult(buildDropMessage(applicationsToDrop));
    }

    private List<Application> findApplicationsToDrop(Model model) {
        requireNonNull(model);
        return model.getFilteredApplicationList().stream()
                .filter(Application::hasTerminalStatus)
                .toList();
    }

    private void deleteApplications(Model model, List<Application> applicationsToDrop) {
        requireNonNull(model);
        requireNonNull(applicationsToDrop);

        applicationsToDrop.forEach(model::deleteApplication);
    }

    private String buildDropMessage(List<Application> droppedApplications) {
        requireNonNull(droppedApplications);
        assert !droppedApplications.isEmpty()
                : "Dropped applications should not be empty when building success message";

        String summary = String.format(MESSAGE_DROP_APPLICATIONS_SUCCESS, droppedApplications.size());
        String details = droppedApplications.stream()
                .map(application -> "- " + Messages.format(application))
                .collect(Collectors.joining("\n"));
        return summary + "\n" + details;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return other instanceof DropCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
