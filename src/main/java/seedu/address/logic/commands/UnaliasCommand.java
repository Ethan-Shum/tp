package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Removes an existing alias.
 */
public class UnaliasCommand extends Command {

    public static final String COMMAND_WORD = "unalias";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes an existing alias.\n"
            + "Parameters: ALIAS\n"
            + "Example: " + COMMAND_WORD + " ls";

    public static final String MESSAGE_SUCCESS = "Alias removed: %1$s";
    public static final String MESSAGE_ALIAS_NOT_FOUND = "Alias does not exist.";

    private final String alias;

    /**
     * @param alias The alias to be removed.
     */
    public UnaliasCommand(String alias) {
        this.alias = alias;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasAlias(alias)) {
            throw new CommandException(MESSAGE_ALIAS_NOT_FOUND);
        }

        model.removeAlias(alias);
        return new CommandResult(String.format(MESSAGE_SUCCESS, alias));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnaliasCommand)) {
            return false;
        }

        UnaliasCommand otherCommand = (UnaliasCommand) other;
        return alias.equals(otherCommand.alias);
    }
}
