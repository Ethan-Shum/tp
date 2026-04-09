package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import seedu.address.model.application.Note;
import seedu.address.testutil.ApplicationBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ClearNoteCommand}.
 */
public class ClearNoteCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Application applicationToClear = model.getFilteredApplicationList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        Application applicationWithNote = new ApplicationBuilder(applicationToClear).withNote("follow up soon")
            .build();
        model.setApplication(applicationToClear, applicationWithNote);
        ClearNoteCommand clearNoteCommand = new ClearNoteCommand(INDEX_FIRST_APPLICATION);

        Application updatedApplication = new Application(
            applicationWithNote.getCompany(),
            applicationWithNote.getRole(),
            applicationWithNote.getApplicationDate(),
            applicationWithNote.getUrl(),
            applicationWithNote.getStatus(),
                Note.EMPTY);

        String expectedMessage = String.format(ClearNoteCommand.MESSAGE_CLEAR_NOTE_SUCCESS,
                Messages.format(updatedApplication));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setApplication(applicationWithNote, updatedApplication);

        assertCommandSuccess(clearNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyNote_throwsCommandException() {
        ClearNoteCommand clearNoteCommand = new ClearNoteCommand(INDEX_FIRST_APPLICATION);

        assertCommandFailure(clearNoteCommand, model, ClearNoteCommand.MESSAGE_APPLICATION_NOTE_ALREADY_EMPTY);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApplicationList().size() + 1);
        ClearNoteCommand clearNoteCommand = new ClearNoteCommand(outOfBoundIndex);

        assertCommandFailure(clearNoteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Application applicationToClear = model.getFilteredApplicationList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        Application applicationWithNote = new ApplicationBuilder(applicationToClear).withNote("follow up soon")
            .build();
        model.setApplication(applicationToClear, applicationWithNote);
        CommandTestUtil.showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);
        ClearNoteCommand clearNoteCommand = new ClearNoteCommand(INDEX_FIRST_APPLICATION);

        Application updatedApplication = new Application(
            applicationWithNote.getCompany(),
            applicationWithNote.getRole(),
            applicationWithNote.getApplicationDate(),
            applicationWithNote.getUrl(),
            applicationWithNote.getStatus(),
                Note.EMPTY);

        String expectedMessage = String.format(ClearNoteCommand.MESSAGE_CLEAR_NOTE_SUCCESS,
                Messages.format(updatedApplication));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        CommandTestUtil.showApplicationAtIndex(expectedModel, INDEX_FIRST_APPLICATION);
        expectedModel.setApplication(applicationWithNote, updatedApplication);

        assertCommandSuccess(clearNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        ClearNoteCommand clearNoteCommand = new ClearNoteCommand(INDEX_SECOND_APPLICATION);
        assertCommandFailure(clearNoteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ClearNoteCommand firstCommand = new ClearNoteCommand(INDEX_FIRST_APPLICATION);
        ClearNoteCommand secondCommand = new ClearNoteCommand(INDEX_SECOND_APPLICATION);

        assertEquals(firstCommand, firstCommand);
        assertEquals(firstCommand, new ClearNoteCommand(INDEX_FIRST_APPLICATION));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
        assertNotEquals(firstCommand, secondCommand);
    }

    @Test
    public void toStringMethod() {
        ClearNoteCommand clearNoteCommand = new ClearNoteCommand(INDEX_FIRST_APPLICATION);
        String expected = ClearNoteCommand.class.getCanonicalName()
                + "{targetIndex=" + INDEX_FIRST_APPLICATION + "}";
        assertEquals(expected, clearNoteCommand.toString());
    }
}
