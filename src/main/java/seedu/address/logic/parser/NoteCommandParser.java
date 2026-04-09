package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.Note;

/**
 * Parses input arguments and creates a new {@code NoteCommand} object.
 */
public class NoteCommandParser implements Parser<NoteCommand> {

    private static final Logger logger = LogsCenter.getLogger(NoteCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the {@code NoteCommand}
     * and returns a {@code NoteCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.info("Rejected note command: empty arguments");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
        }

        String[] tokens = splitIntoIndexAndNote(trimmedArgs);
        Index index = parseIndexToken(tokens[0]);
        Note note = parseNoteToken(tokens);

        logger.info(() -> "Parsed note command for index " + index.getOneBased());
        return note == null ? NoteCommand.withoutNote(index) : new NoteCommand(index, note);
    }

    private String[] splitIntoIndexAndNote(String trimmedArgs) {
        return trimmedArgs.split("\\s+", 2);
    }

    private Index parseIndexToken(String indexToken) throws ParseException {
        return ParserUtil.parseIndex(indexToken);
    }

    private Note parseNoteToken(String[] tokens) throws ParseException {
        if (tokens.length < 2 || tokens[1].trim().isEmpty()) {
            return null;
        }
        return ParserUtil.parseNote(tokens[1]);
    }
}
