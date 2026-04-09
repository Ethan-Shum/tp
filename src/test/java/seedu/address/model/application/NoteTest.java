package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constructor_invalidNote_throwsIllegalArgumentException() {
        String invalidNote = "a".repeat(501);
        assertThrows(IllegalArgumentException.class, Note.MESSAGE_LENGTH_CONSTRAINTS, ()
                -> new Note(invalidNote));
    }

    @Test
    public void constructor_invalidCharacters_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, Note.MESSAGE_CONSTRAINTS, ()
                -> new Note("hello🙂"));
    }

    @Test
    public void isValidNote() {
        assertThrows(NullPointerException.class, () -> Note.isValidNote(null));

        assertTrue(Note.isValidNote(""));
        assertTrue(Note.isValidNote("prepare for behavioral round"));
        assertTrue(Note.isValidNote("a".repeat(500)));
        assertTrue(Note.isValidNote("Interview at 10am on 2025-12-22."));

        assertFalse(Note.isValidNote("a".repeat(501)));
        assertFalse(Note.isValidNote("hello🙂"));
    }

    @Test
    public void equals() {
        Note note = new Note("Valid note");

        assertEquals(new Note("Valid note"), note);
        assertFalse(note.equals(null));
        assertFalse(note.equals(5.0f));
        assertFalse(note.equals(new Note("Other note")));
    }
}
