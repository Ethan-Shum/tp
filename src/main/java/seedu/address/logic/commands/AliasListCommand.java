package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.TreeMap;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all saved aliases in alphabetical order.
 */
public class AliasListCommand extends Command {

    public static final String COMMAND_WORD = "alias-list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all saved aliases.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_NO_ALIASES = "You have no alias saved.";
    public static final String MESSAGE_HEADER = "Current aliases:\n";

    /**
     * Lists all aliases currently stored in the model.
     *
     * @param model Model containing the saved aliases.
     * @return CommandResult containing the formatted alias list.
     * @throws CommandException If execution fails unexpectedly.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Map<String, String> aliases = new TreeMap<>(model.getAliases());

        if (aliases.isEmpty()) {
            return new CommandResult(MESSAGE_NO_ALIASES);
        }

        StringBuilder sb = new StringBuilder(MESSAGE_HEADER);

        for (Map.Entry<String, String> entry : aliases.entrySet()) {
            sb.append(entry.getKey())
                    .append(" -> ")
                    .append(entry.getValue())
                    .append("\n");
        }

        sb.append("Total: ").append(aliases.size()).append(" aliases");

        return new CommandResult(sb.toString().trim());
    }

    /**
     * Returns true if both commands are of the same type.
     *
     * @param other Object to compare against.
     * @return True if {@code other} is this command or another AliasListCommand.
     */
    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof AliasListCommand;
    }
}
