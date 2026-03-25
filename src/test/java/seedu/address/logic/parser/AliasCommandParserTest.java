package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AliasCommand;

public class AliasCommandParserTest {

    private final AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_validArgs_returnsAliasCommand() {
        assertParseSuccess(parser, "ls list", new AliasCommand("ls", "list"));
    }

    @Test
    public void parse_extraWhitespace_returnsAliasCommand() {
        assertParseSuccess(parser, "   ls    list   ", new AliasCommand("ls", "list"));
    }

    @Test
    public void parse_missingArguments_throwsParseException() {
        assertParseFailure(parser, "ls",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyArguments_throwsParseException() {
        assertParseFailure(parser, "ls list extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
    }
}