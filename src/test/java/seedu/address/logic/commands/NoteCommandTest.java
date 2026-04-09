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

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code NoteCommand}.
 */
public class NoteCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Application applicationToNote = model.getFilteredApplicationList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        Note note = new Note("Follow up next Monday");
        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_APPLICATION, note);

        Application notedApplication = new Application(
                applicationToNote.getCompany(),
                applicationToNote.getRole(),
                applicationToNote.getApplicationDate(),
                applicationToNote.getUrl(),
                applicationToNote.getStatus(),
                note);

        String expectedMessage = String.format(NoteCommand.MESSAGE_ADD_NOTE_SUCCESS, Messages.format(notedApplication));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setApplication(applicationToNote, notedApplication);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApplicationList().size() + 1);
        NoteCommand noteCommand = new NoteCommand(outOfBoundIndex, new Note("hello"));

        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        CommandTestUtil.showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        Application applicationToNote = model.getFilteredApplicationList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        Note note = new Note("Follow up next Monday");
        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_APPLICATION, note);

        Application notedApplication = new Application(
                applicationToNote.getCompany(),
                applicationToNote.getRole(),
                applicationToNote.getApplicationDate(),
                applicationToNote.getUrl(),
                applicationToNote.getStatus(),
                note);

        String expectedMessage = String.format(NoteCommand.MESSAGE_ADD_NOTE_SUCCESS, Messages.format(notedApplication));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        CommandTestUtil.showApplicationAtIndex(expectedModel, INDEX_FIRST_APPLICATION);
        expectedModel.setApplication(applicationToNote, notedApplication);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        NoteCommand noteCommand = new NoteCommand(INDEX_SECOND_APPLICATION, new Note("hello"));
        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        NoteCommand firstCommand = new NoteCommand(INDEX_FIRST_APPLICATION, new Note("a"));
        NoteCommand secondCommand = new NoteCommand(INDEX_SECOND_APPLICATION, new Note("b"));

        assertEquals(firstCommand, firstCommand);
        assertEquals(firstCommand, new NoteCommand(INDEX_FIRST_APPLICATION, new Note("a")));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
        assertNotEquals(firstCommand, secondCommand);
    }

    @Test
    public void toStringMethod() {
        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_APPLICATION, new Note("Prep"));
        String expected = NoteCommand.class.getCanonicalName()
                + "{targetIndex=" + INDEX_FIRST_APPLICATION + ", note=Prep}";
        assertEquals(expected, noteCommand.toString());
    }

    @Test
    public void execute_replaceExistingNote_success() {
        Application originalApplication = model.getFilteredApplicationList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        Application applicationWithOldNote = copyWithNote(originalApplication, "old");
        model.setApplication(originalApplication, applicationWithOldNote);

        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_APPLICATION, new Note("new"));
        Application updatedApplication = copyWithNote(applicationWithOldNote, "new");

        String expectedMessage = String.format(
                NoteCommand.MESSAGE_ADD_NOTE_SUCCESS, Messages.format(updatedApplication));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setApplication(applicationWithOldNote, updatedApplication);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a copy of {@code source} with all fields preserved except note.
     */
    private static Application copyWithNote(Application source, String noteValue) {
        return new Application(
                source.getCompany(),
                source.getRole(),
                source.getApplicationDate(),
                source.getUrl(),
                source.getStatus(),
                new Note(noteValue));
    }
}
