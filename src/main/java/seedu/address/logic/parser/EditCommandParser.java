package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_URL;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditApplicationDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY, PREFIX_ROLE, PREFIX_APPLICATION_DATE, PREFIX_URL,
                        PREFIX_STATUS);

        String preamble = argMultimap.getPreamble().trim();

        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        // Split preamble into index token and any extra trailing text
        int spacePos = preamble.indexOf(' ');
        String indexStr = spacePos == -1 ? preamble : preamble.substring(0, spacePos);
        String extraText = spacePos == -1 ? "" : preamble.substring(spacePos + 1).trim();

        // Validate index syntax; throws MESSAGE_INVALID_INDEX for non-positive or non-numeric values
        Index index = ParserUtil.parseIndex(indexStr);

        // Extra text in preamble means invalid format; defer the error so bounds check runs first
        String deferredErrorMessage = extraText.isEmpty()
                ? null
                : String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COMPANY, PREFIX_ROLE, PREFIX_APPLICATION_DATE, PREFIX_URL,
                PREFIX_STATUS);

        EditApplicationDescriptor editApplicationDescriptor = new EditApplicationDescriptor();

        if (argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            editApplicationDescriptor.setCompany(ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get()));
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            editApplicationDescriptor.setRole(ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get()));
        }
        if (argMultimap.getValue(PREFIX_APPLICATION_DATE).isPresent()) {
            editApplicationDescriptor.setApplicationDate(ParserUtil.parseApplicationDate(
                    argMultimap.getValue(PREFIX_APPLICATION_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_URL).isPresent()) {
            editApplicationDescriptor.setUrl(ParserUtil.parseUrl(argMultimap.getValue(PREFIX_URL).get()));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            editApplicationDescriptor.setStatus(ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()));
        }

        // No fields edited and no prior deferred error: defer MESSAGE_NOT_EDITED so bounds check runs first
        if (!editApplicationDescriptor.isAnyFieldEdited() && deferredErrorMessage == null) {
            deferredErrorMessage = EditCommand.MESSAGE_NOT_EDITED;
        }

        return new EditCommand(index, editApplicationDescriptor, deferredErrorMessage);
    }

}
