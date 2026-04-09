package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_URL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.ApplicationContainsKeywordsPredicate;
import seedu.address.model.application.ApplicationDate;
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
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY, PREFIX_ROLE, PREFIX_APPLICATION_DATE, PREFIX_URL,
                        PREFIX_STATUS);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COMPANY, PREFIX_ROLE, PREFIX_APPLICATION_DATE, PREFIX_URL,
                PREFIX_STATUS);

        List<String> companyKeywords = new ArrayList<>();
        List<String> roleKeywords = new ArrayList<>();
        LocalDate startDate = null;
        LocalDate endDate = null;
        List<String> urlKeywords = new ArrayList<>();
        List<String> statusKeywords = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            String companyArg = argMultimap.getValue(PREFIX_COMPANY).get().trim();
            if (companyArg.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            companyKeywords = Arrays.asList(companyArg.split("\\s+"));
        }

        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            String roleArg = argMultimap.getValue(PREFIX_ROLE).get().trim();
            if (roleArg.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            roleKeywords = Arrays.asList(roleArg.split("\\s+"));
        }

        if (argMultimap.getValue(PREFIX_APPLICATION_DATE).isPresent()) {
            String dateArg = argMultimap.getValue(PREFIX_APPLICATION_DATE).get().trim();
            if (dateArg.isEmpty()) {
                throw new ParseException(ApplicationDate.MESSAGE_CONSTRAINTS);
            }
            if (dateArg.contains(":")) {
                String[] dates = dateArg.split(":");
                if (dates.length != 2) {
                    throw new ParseException("Date range should be in the format START_DATE:END_DATE");
                }
                if (!ApplicationDate.isValidApplicationDate(dates[0])
                        || !ApplicationDate.isValidApplicationDate(dates[1])) {
                    throw new ParseException(ApplicationDate.MESSAGE_CONSTRAINTS);
                }
                startDate = new ApplicationDate(dates[0]).getValue();
                endDate = new ApplicationDate(dates[1]).getValue();
                if (startDate.isAfter(endDate)) {
                    throw new ParseException("Start date cannot be after end date.");
                }
            } else {
                if (!ApplicationDate.isValidApplicationDate(dateArg)) {
                    throw new ParseException(ApplicationDate.MESSAGE_CONSTRAINTS);
                }
                startDate = new ApplicationDate(dateArg).getValue();
                endDate = new ApplicationDate(dateArg).getValue();
            }
        }

        if (argMultimap.getValue(PREFIX_URL).isPresent()) {
            String urlArg = argMultimap.getValue(PREFIX_URL).get().trim();
            if (urlArg.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            urlKeywords = Arrays.asList(urlArg.split("\\s+"));
        }

        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            String statusArg = argMultimap.getValue(PREFIX_STATUS).get().trim();
            if (statusArg.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            statusKeywords = Arrays.asList(statusArg.split("\\s+"));
            for (String status : statusKeywords) {
                try {
                    Status.fromUserInput(status);
                } catch (IllegalArgumentException e) {
                    throw new ParseException(Status.MESSAGE_CONSTRAINTS);
                }
            }
        }

        if (companyKeywords.isEmpty() && roleKeywords.isEmpty() && startDate == null && endDate == null
                && urlKeywords.isEmpty() && statusKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new ApplicationContainsKeywordsPredicate(companyKeywords, roleKeywords,
                startDate, endDate, urlKeywords, statusKeywords));
    }

}
