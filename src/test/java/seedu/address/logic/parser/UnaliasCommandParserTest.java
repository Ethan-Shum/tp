package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnaliasCommand;

public class UnaliasCommandParserTest {

    private final UnaliasCommandParser parser = new UnaliasCommandParser();

    @Test
    public void parse_validArgs_returnsUnaliasCommand() {
        assertParseSuccess(parser, "ls", new UnaliasCommand("ls"));
    }

    @Test
    public void parse_extraWhitespace_returnsUnaliasCommand() {
        assertParseSuccess(parser, "   ls   ", new UnaliasCommand("ls"));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyArguments_throwsParseException() {
        assertParseFailure(parser, "ls extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
    }
}
