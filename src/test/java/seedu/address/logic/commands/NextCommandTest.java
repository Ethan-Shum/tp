package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showApplicationAtIndex;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.model.application.Status;
import seedu.address.testutil.ApplicationBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code NextCommand}.
 */
public class NextCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Application applicationToUpdate = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        Status nextStatus = applicationToUpdate.getStatus().getNextStatus();
        Application updatedApplication = new ApplicationBuilder(applicationToUpdate)
                .withStatus(nextStatus.toString())
                .build();
        NextCommand nextCommand = new NextCommand(INDEX_FIRST_APPLICATION);

        String expectedMessage = String.format(NextCommand.MESSAGE_NEXT_APPLICATION_SUCCESS,
                Messages.format(updatedApplication));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setApplication(applicationToUpdate, updatedApplication);

        assertCommandSuccess(nextCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApplicationList().size() + 1);
        NextCommand nextCommand = new NextCommand(outOfBoundIndex);

        assertCommandFailure(nextCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        Application applicationToUpdate = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        Status nextStatus = applicationToUpdate.getStatus().getNextStatus();
        Application updatedApplication = new ApplicationBuilder(applicationToUpdate)
                .withStatus(nextStatus.toString())
                .build();
        NextCommand nextCommand = new NextCommand(INDEX_FIRST_APPLICATION);

        String expectedMessage = String.format(NextCommand.MESSAGE_NEXT_APPLICATION_SUCCESS,
                Messages.format(updatedApplication));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setApplication(applicationToUpdate, updatedApplication);
        showApplicationAtIndex(expectedModel, INDEX_FIRST_APPLICATION);

        assertCommandSuccess(nextCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_APPLICATION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getApplicationList().size());

        NextCommand nextCommand = new NextCommand(outOfBoundIndex);

        assertCommandFailure(nextCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        NextCommand nextFirstCommand = new NextCommand(INDEX_FIRST_APPLICATION);
        NextCommand nextSecondCommand = new NextCommand(INDEX_SECOND_APPLICATION);

        // same object -> returns true
        assertTrue(nextFirstCommand.equals(nextFirstCommand));

        // same values -> returns true
        NextCommand nextFirstCommandCopy = new NextCommand(INDEX_FIRST_APPLICATION);
        assertTrue(nextFirstCommand.equals(nextFirstCommandCopy));

        // different types -> returns false
        assertFalse(nextFirstCommand.equals(1));

        // null -> returns false
        assertFalse(nextFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(nextFirstCommand.equals(nextSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index index = INDEX_FIRST_APPLICATION;
        NextCommand nextCommand = new NextCommand(index);
        String expected = NextCommand.class.getCanonicalName() + "{targetIndex=" + index + "}";
        assertEquals(expected, nextCommand.toString());
    }

}
