package seedu.address.logic.parser;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns an ClearCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException("Clear command will delete all applications in the current list, "
                    + "and it does not take any arguments.");
        }
        return new ClearCommand();
    }
}
