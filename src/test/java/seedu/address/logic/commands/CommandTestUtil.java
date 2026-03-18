package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_URL;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationContainsKeywordsPredicate;
import seedu.address.testutil.EditApplicationDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_COMPANY_AMY = "Amazon";
    public static final String VALID_COMPANY_BOB = "ByteDance";
    public static final String VALID_ROLE_AMY = "Software Engineer Intern";
    public static final String VALID_ROLE_BOB = "Data Analyst Intern";
    public static final String VALID_APPLICATION_DATE_AMY = "2026-03-09";
    public static final String VALID_APPLICATION_DATE_BOB = "2026-03-10";
    public static final String VALID_URL_AMY = "http://amy.example.com";
    public static final String VALID_URL_BOB = "http://bob.example.com";
    public static final String VALID_STATUS_AMY = "Applied";
    public static final String VALID_STATUS_BOB = "Interview";

    public static final String COMPANY_DESC_AMY = " " + PREFIX_COMPANY + VALID_COMPANY_AMY;
    public static final String COMPANY_DESC_BOB = " " + PREFIX_COMPANY + VALID_COMPANY_BOB;
    public static final String ROLE_DESC_AMY = " " + PREFIX_ROLE + VALID_ROLE_AMY;
    public static final String ROLE_DESC_BOB = " " + PREFIX_ROLE + VALID_ROLE_BOB;
    public static final String APPLICATION_DATE_DESC_AMY =
            " " + PREFIX_APPLICATION_DATE + VALID_APPLICATION_DATE_AMY;
    public static final String APPLICATION_DATE_DESC_BOB =
            " " + PREFIX_APPLICATION_DATE + VALID_APPLICATION_DATE_BOB;
    public static final String URL_DESC_AMY = " " + PREFIX_URL + VALID_URL_AMY;
    public static final String URL_DESC_BOB = " " + PREFIX_URL + VALID_URL_BOB;
    public static final String STATUS_DESC_AMY = " " + PREFIX_STATUS + VALID_STATUS_AMY;
    public static final String STATUS_DESC_BOB = " " + PREFIX_STATUS + VALID_STATUS_BOB;

    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY + "James&";
    // '&' not allowed in company names
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + "@invalid"; // '@' not allowed in roles
    public static final String INVALID_APPLICATION_DATE_DESC =
            " " + PREFIX_APPLICATION_DATE + "2026/03/09"; // '/' symbol not allowed
    public static final String INVALID_URL_DESC = " " + PREFIX_URL + "invalid-url"; // invalid URL format
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS + "UnknownStatus*"; // invalid status

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditApplicationDescriptor DESC_AMY;
    public static final EditCommand.EditApplicationDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditApplicationDescriptorBuilder().withCompany(VALID_COMPANY_AMY)
                .withRole(VALID_ROLE_AMY).withApplicationDate(VALID_APPLICATION_DATE_AMY)
                .withUrl(VALID_URL_AMY).withStatus(VALID_STATUS_AMY).build();
        DESC_BOB = new EditApplicationDescriptorBuilder().withCompany(VALID_COMPANY_BOB)
                .withRole(VALID_ROLE_BOB).withApplicationDate(VALID_APPLICATION_DATE_BOB)
                .withUrl(VALID_URL_BOB).withStatus(VALID_STATUS_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Application> expectedFilteredList = new ArrayList<>(actualModel.getFilteredApplicationList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredApplicationList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the application at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showApplicationAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredApplicationList().size());

        Application application = model.getFilteredApplicationList().get(targetIndex.getZeroBased());
        final String[] splitName = application.getCompany().value.split("\\s+");
        model.updateFilteredApplicationList(new ApplicationContainsKeywordsPredicate(Arrays.asList(splitName[0]),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        assertEquals(1, model.getFilteredApplicationList().size());
    }

}
