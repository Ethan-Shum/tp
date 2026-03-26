package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.application.ApplicationContainsKeywordsPredicate;
import seedu.address.model.application.Status;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(new ApplicationContainsKeywordsPredicate(
                Arrays.asList("Google", "Meta"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        assertParseSuccess(parser, " " + PREFIX_COMPANY + "Google Meta", expectedFindCommand);

        expectedFindCommand = new FindCommand(new ApplicationContainsKeywordsPredicate(
                new ArrayList<>(), Arrays.asList("Intern", "Developer"), new ArrayList<>(), new ArrayList<>()));
        assertParseSuccess(parser, " " + PREFIX_ROLE + "Intern Developer", expectedFindCommand);

        expectedFindCommand = new FindCommand(new ApplicationContainsKeywordsPredicate(
                new ArrayList<>(), new ArrayList<>(), Arrays.asList("2022-12-12"), new ArrayList<>()));
        assertParseSuccess(parser, " " + PREFIX_APPLICATION_DATE + "2022-12-12", expectedFindCommand);

        expectedFindCommand = new FindCommand(new ApplicationContainsKeywordsPredicate(
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Arrays.asList("Applied")));
        assertParseSuccess(parser, " " + PREFIX_STATUS + "Applied", expectedFindCommand);

        expectedFindCommand = new FindCommand(new ApplicationContainsKeywordsPredicate(
                Arrays.asList("Google"), Arrays.asList("Intern"), Arrays.asList("2022-12-12"),
                Arrays.asList("Applied")));
        assertParseSuccess(parser, " " + PREFIX_COMPANY + "Google " + PREFIX_ROLE + "Intern "
                + PREFIX_APPLICATION_DATE + "2022-12-12 " + PREFIX_STATUS + "Applied", expectedFindCommand);
    }

    @Test
    public void parse_noPrefix_throwsParseException() {
        assertParseFailure(parser, "Alice Bob", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStatus_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_STATUS + "hi", Status.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_COMPANY + "Google " + PREFIX_STATUS + "hi",
                Status.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_COMPANY + "Google " + PREFIX_COMPANY + "Meta",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));
        assertParseFailure(parser, " " + PREFIX_COMPANY + "Google random " + PREFIX_COMPANY + "Meta",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));
    }

}
