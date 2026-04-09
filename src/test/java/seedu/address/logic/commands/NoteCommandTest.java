package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NoteCommand(null, new Note("x")));
    }

    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NoteCommand(INDEX_FIRST_APPLICATION, null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_APPLICATION, new Note("x"));
        assertThrows(NullPointerException.class, () -> noteCommand.execute(null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        assertExecuteNoteSuccess(model, "Follow up next Monday", false);
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
        assertExecuteNoteSuccess(model, "Follow up next Monday", true);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        CommandTestUtil.showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        NoteCommand noteCommand = new NoteCommand(INDEX_SECOND_APPLICATION, new Note("hello"));
        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_missingNoteTextValidIndex_throwsCommandException() {
        NoteCommand noteCommand = NoteCommand.withoutNote(INDEX_FIRST_APPLICATION);
        assertCommandFailure(noteCommand, model, Note.MESSAGE_EMPTY_NOTE);
    }

    @Test
    public void execute_missingNoteTextInvalidIndex_prioritizesInvalidIndex() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApplicationList().size() + 1);
        NoteCommand noteCommand = NoteCommand.withoutNote(outOfBoundIndex);

        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        NoteCommand firstCommand = new NoteCommand(INDEX_FIRST_APPLICATION, new Note("a"));
        NoteCommand secondCommand = new NoteCommand(INDEX_SECOND_APPLICATION, new Note("b"));

        assertEquals(firstCommand, firstCommand);
        assertEquals(new NoteCommand(INDEX_FIRST_APPLICATION, new Note("a")), firstCommand);
        Object differentType = 1;
        assertNotEquals(firstCommand, differentType);
        assertNotEquals(firstCommand, null);
        assertNotEquals(firstCommand, secondCommand);
    }

    @Test
    public void toStringMethod() {
        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_APPLICATION, new Note("Prep"));
        String expected = NoteCommand.class.getCanonicalName()
                + "{targetIndex=" + INDEX_FIRST_APPLICATION + ", note=Prep, hasNoteText=true}";
        assertEquals(expected, noteCommand.toString());
    }

    @Test
    public void execute_replaceExistingNote_success() {
        Application originalApplication =
                model.getFilteredApplicationList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        Application applicationWithOldNote = copyWithNote(originalApplication, "old");
        model.setApplication(originalApplication, applicationWithOldNote);

        assertExecuteNoteSuccess(model, "new", false);
    }

    private void assertExecuteNoteSuccess(Model actualModel, String noteValue, boolean isFilteredContext) {
        Application applicationToNote =
                actualModel.getFilteredApplicationList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        Note note = new Note(noteValue);
        NoteCommand noteCommand = new NoteCommand(INDEX_FIRST_APPLICATION, note);

        Application updatedApplication = copyWithNote(applicationToNote, noteValue);
        String expectedMessage = String.format(
                NoteCommand.MESSAGE_ADD_NOTE_SUCCESS, Messages.format(updatedApplication));

        Model expectedModel = new ModelManager(actualModel.getAddressBook(), new UserPrefs());
        if (isFilteredContext) {
            CommandTestUtil.showApplicationAtIndex(expectedModel, INDEX_FIRST_APPLICATION);
        }
        expectedModel.setApplication(applicationToNote, updatedApplication);

        assertCommandSuccess(noteCommand, actualModel, expectedMessage, expectedModel);
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
