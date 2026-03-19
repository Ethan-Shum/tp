package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showApplicationAtIndex;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.testutil.ApplicationBuilder;

/**
 * Contains unit tests for {@code CopyCommand}.
 */
public class CopyCommandTest {

    private static final String MESSAGE_COPY_APPLICATION_WITHOUT_URL = "This application does not have a URL to copy.";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Application applicationToCopy = model.getFilteredApplicationList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        CopyCommand copyCommand = new CopyCommandStub(INDEX_FIRST_APPLICATION);

        CommandResult result = copyCommand.execute(model);

        String expectedMessage = String.format(CopyCommand.MESSAGE_COPY_APPLICATION_SUCCESS,
                Messages.format(applicationToCopy));
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);
        Application applicationToCopy = model.getFilteredApplicationList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        CopyCommand copyCommand = new CopyCommandStub(INDEX_FIRST_APPLICATION);

        CommandResult result = copyCommand.execute(model);

        String expectedMessage = String.format(CopyCommand.MESSAGE_COPY_APPLICATION_SUCCESS,
                Messages.format(applicationToCopy));
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApplicationList().size() + 1);
        CopyCommand copyCommand = new CopyCommand(outOfBoundIndex);

        assertCommandFailure(copyCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_APPLICATION;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getApplicationList().size());

        CopyCommand copyCommand = new CopyCommand(outOfBoundIndex);

        assertCommandFailure(copyCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_applicationWithoutUrl_throwsCommandException() {
        AddressBook addressBook = new AddressBook();
        addressBook.addApplication(new ApplicationBuilder().build());
        Model model = new ModelManager(addressBook, new UserPrefs());
        CopyCommand copyCommand = new CopyCommand(INDEX_FIRST_APPLICATION);

        assertCommandFailure(copyCommand, model, MESSAGE_COPY_APPLICATION_WITHOUT_URL);
    }

    @Test
    public void equals() {
        CopyCommand copyFirstCommand = new CopyCommand(INDEX_FIRST_APPLICATION);
        CopyCommand copySecondCommand = new CopyCommand(INDEX_SECOND_APPLICATION);

        assertTrue(copyFirstCommand.equals(copyFirstCommand));
        CopyCommand copyFirstCommandCopy = new CopyCommand(INDEX_FIRST_APPLICATION);
        assertTrue(copyFirstCommand.equals(copyFirstCommandCopy));
        assertFalse(copyFirstCommand.equals(1));
        assertFalse(copyFirstCommand.equals(null));
        assertFalse(copyFirstCommand.equals(copySecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        CopyCommand copyCommand = new CopyCommand(targetIndex);
        String expected = CopyCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, copyCommand.toString());
    }

    private static class CopyCommandStub extends CopyCommand {
        CopyCommandStub(Index targetIndex) {
            super(targetIndex);
        }

        @Override
        public CommandResult execute(Model model) throws CommandException {
            Application applicationToCopy = model.getFilteredApplicationList()
                    .get(INDEX_FIRST_APPLICATION.getZeroBased());
            return new CommandResult(String.format(MESSAGE_COPY_APPLICATION_SUCCESS,
                    Messages.format(applicationToCopy)));
        }
    }
}
