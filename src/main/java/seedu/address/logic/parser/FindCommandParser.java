package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.ApplicationContainsKeywordsPredicate;
import seedu.address.model.application.Status;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY, PREFIX_ROLE, PREFIX_APPLICATION_DATE, PREFIX_STATUS);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COMPANY, PREFIX_ROLE, PREFIX_APPLICATION_DATE, PREFIX_STATUS);

        List<String> companyKeywords = new ArrayList<>();
        List<String> roleKeywords = new ArrayList<>();
        List<String> applicationDateKeywords = new ArrayList<>();
        List<String> statusKeywords = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            companyKeywords = Arrays.asList(argMultimap.getValue(PREFIX_COMPANY).get().split("\\s+"));
        }

        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            roleKeywords = Arrays.asList(argMultimap.getValue(PREFIX_ROLE).get().split("\\s+"));
        }

        if (argMultimap.getValue(PREFIX_APPLICATION_DATE).isPresent()) {
            applicationDateKeywords = Arrays.asList(argMultimap.getValue(PREFIX_APPLICATION_DATE).get().split("\\s+"));
        }

        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            statusKeywords = Arrays.asList(argMultimap.getValue(PREFIX_STATUS).get().split("\\s+"));
            for (String status : statusKeywords) {
                try {
                    Status.fromUserInput(status);
                } catch (IllegalArgumentException e) {
                    throw new ParseException(Status.MESSAGE_CONSTRAINTS);
                }
            }
        }

        if (companyKeywords.isEmpty() && roleKeywords.isEmpty() && applicationDateKeywords.isEmpty()
                && statusKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new ApplicationContainsKeywordsPredicate(companyKeywords, roleKeywords,
                applicationDateKeywords, statusKeywords));
    }

}
