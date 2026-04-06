package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.application.Application;

/**
 * Clears the current list of applications.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Cleared %1$d application(s) from the list!";
    public static final String MESSAGE_CLEARED_APPLICATIONS_HEADER =
            "Cleared applications:";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Application> applicationsToClear = new ArrayList<>(model.getFilteredApplicationList());

        applicationsToClear.forEach(model::deleteApplication);

        return new CommandResult(buildClearMessage(applicationsToClear));
    }

    private String buildClearMessage(List<Application> clearedApplications) {
        String summary = String.format(MESSAGE_SUCCESS, clearedApplications.size());
        String details = clearedApplications.stream()
                .map(application -> "- " + Messages.format(application))
                .collect(Collectors.joining("\n"));
        return summary + "\n" + MESSAGE_CLEARED_APPLICATIONS_HEADER + "\n" + details;
    }
}
