package seedu.address.logic.parser;

import seedu.address.logic.commands.DropCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DropCommand object
 */
public class DropCommandParser implements Parser<DropCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DropCommand
     * and returns a DropCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DropCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException("Drop command will delete all applications with "
                    + "WITHDRAWN and REJECTED status in the current list, "
                    + "and it does not take any arguments.");
        }
        return new DropCommand();
    }
}
