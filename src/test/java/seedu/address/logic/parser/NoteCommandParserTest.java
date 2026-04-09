package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_AMAZON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NoteCommand;
import seedu.address.model.application.Note;

public class NoteCommandParserTest {

    private final NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_validArgs_returnsNoteCommand() {
        assertParseSuccess(parser, "1" + NOTE_DESC_AMAZON,
                new NoteCommand(INDEX_FIRST_APPLICATION, new Note("Follow up next Monday.")));
    }

    @Test
    public void parse_missingNoteValue_throwsParseException() {
        assertParseFailure(parser, "1", Note.MESSAGE_EMPTY_NOTE);
    }

    @Test
    public void parse_whitespaceOnlyNoteValue_throwsParseException() {
        assertParseFailure(parser, "1   ", Note.MESSAGE_EMPTY_NOTE);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a hello", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noteWithSpaces_returnsNoteCommand() {
        assertParseSuccess(parser, "1 Prepare for OA tomorrow",
                new NoteCommand(INDEX_FIRST_APPLICATION, new Note("Prepare for OA tomorrow")));
    }

    @Test
    public void parse_noteTooLong_throwsParseException() {
        String longNote = "a".repeat(201);
        assertParseFailure(parser, "1 " + longNote, Note.MESSAGE_LENGTH_CONSTRAINTS);
    }

    @Test
    public void parse_invalidNoteCharacters_throwsParseException() {
        assertParseFailure(parser, "1 hello🙂", Note.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        assertParseFailure(parser, "0 hello", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertParseFailure(parser, "-1 hello", ParserUtil.MESSAGE_INVALID_INDEX);
    }
}
