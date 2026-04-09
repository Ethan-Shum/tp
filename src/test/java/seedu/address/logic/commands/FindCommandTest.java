package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_APPLICATIONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalApplications.BENSON;
import static seedu.address.testutil.TypicalApplications.CARL;
import static seedu.address.testutil.TypicalApplications.ELLE;
import static seedu.address.testutil.TypicalApplications.FIONA;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.ApplicationContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ApplicationContainsKeywordsPredicate firstPredicate =
                new ApplicationContainsKeywordsPredicate(Collections.singletonList("first"), new ArrayList<>(),
                        null, null, new ArrayList<>(), new ArrayList<>());
        ApplicationContainsKeywordsPredicate secondPredicate =
                new ApplicationContainsKeywordsPredicate(Collections.singletonList("second"), new ArrayList<>(),
                        null, null, new ArrayList<>(), new ArrayList<>());

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        assertTrue(findFirstCommand.equals(findFirstCommand));

        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        assertFalse(findFirstCommand.equals(1));

        assertFalse(findFirstCommand.equals(null));

        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noApplicationFound() {
        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 0);
        ApplicationContainsKeywordsPredicate predicate = new ApplicationContainsKeywordsPredicate(
                Collections.emptyList(), Collections.emptyList(), null, null, Collections.emptyList(),
                Collections.emptyList());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredApplicationList());
    }

    @Test
    public void execute_multipleKeywords_multipleApplicationsFound() {
        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 3);
        ApplicationContainsKeywordsPredicate predicate = preparePredicate("Google Tencent Foodpanda");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredApplicationList());
    }

    @Test
    public void execute_roleKeywords_applicationFound() {
        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 1);
        ApplicationContainsKeywordsPredicate predicate = new ApplicationContainsKeywordsPredicate(
                new ArrayList<>(), Collections.singletonList("Data"), null, null, new ArrayList<>(),
                new ArrayList<>());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredApplicationList());
    }

    @Test
    public void execute_applicationDateKeywords_applicationFound() {
        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 1);
        LocalDate date = LocalDate.parse("2026-01-02");
        ApplicationContainsKeywordsPredicate predicate = new ApplicationContainsKeywordsPredicate(
                new ArrayList<>(), new ArrayList<>(), date, date, new ArrayList<>(), new ArrayList<>());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredApplicationList());
    }

    @Test
    public void execute_urlKeywords_applicationFound() {
        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 1);
        ApplicationContainsKeywordsPredicate predicate = new ApplicationContainsKeywordsPredicate(
                new ArrayList<>(), new ArrayList<>(), null, null, Collections.singletonList("http://benson.example.com"),
                new ArrayList<>());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredApplicationList());
    }

    @Test
    public void execute_statusKeywords_applicationFound() {
        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 1);
        ApplicationContainsKeywordsPredicate predicate = new ApplicationContainsKeywordsPredicate(
                new ArrayList<>(), new ArrayList<>(), null, null, new ArrayList<>(),
                Collections.singletonList("Interview"));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredApplicationList());
    }

    @Test
    public void toStringMethod() {
        ApplicationContainsKeywordsPredicate predicate = new ApplicationContainsKeywordsPredicate(
                Arrays.asList("keyword"), new ArrayList<>(), null, null, new ArrayList<>(), new ArrayList<>());
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code ApplicationContainsKeywordsPredicate}.
     */
    private ApplicationContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ApplicationContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")), new ArrayList<>(),
                null, null, new ArrayList<>(), new ArrayList<>());
    }
}
