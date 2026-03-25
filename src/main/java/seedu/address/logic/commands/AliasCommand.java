package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Creates an alias for an existing command word.
 */
public class AliasCommand extends Command {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates an alias for an existing command word.\n"
            + "Parameters: ALIAS COMMAND_WORD\n"
            + "Example: " + COMMAND_WORD + " ls list";

    public static final String MESSAGE_SUCCESS = "Alias set: %1$s -> %2$s";
    public static final String MESSAGE_INVALID_TARGET =
            "You can only alias existing command words.";
    public static final String MESSAGE_INVALID_ALIAS =
            "Alias must be a single word and cannot be an existing command word.";

    private static final Set<String> SUPPORTED_COMMAND_WORDS = Set.of(
            AddCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD,
            NextCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            CopyCommand.COMMAND_WORD,
            COMMAND_WORD
    );

    private final String alias;
    private final String commandWord;

    /**
     * @param alias The shortcut word to be created.
     * @param commandWord The existing command word that the alias should map to.
     */
    public AliasCommand(String alias, String commandWord) {
        this.alias = alias;
        this.commandWord = commandWord;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!isSupportedCommandWord(commandWord)) {
            throw new CommandException(MESSAGE_INVALID_TARGET);
        }

        if (isSupportedCommandWord(alias)) {
            throw new CommandException(MESSAGE_INVALID_ALIAS);
        }

        model.setAlias(alias, commandWord);
        return new CommandResult(String.format(MESSAGE_SUCCESS, alias, commandWord));
    }

    /**
     * Returns true if the given word is a valid built-in command word supported by the application.
     *
     * @param word The word to check.
     * @return True if the word is a supported command word, false otherwise.
     */
    private boolean isSupportedCommandWord(String word) {
        return SUPPORTED_COMMAND_WORDS.contains(word);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AliasCommand)) {
            return false;
        }

        AliasCommand otherCommand = (AliasCommand) other;
        return alias.equals(otherCommand.alias)
                && commandWord.equals(otherCommand.commandWord);
    }
}
