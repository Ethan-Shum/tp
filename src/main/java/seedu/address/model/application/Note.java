package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a note attached to an application.
 * Guarantees: immutable; length is at most 500 characters.
 */
public class Note {

    public static final int MAX_LENGTH = 500;
    public static final String MESSAGE_LENGTH_CONSTRAINTS =
            "Note must be at most 500 characters.";
    public static final String MESSAGE_CONSTRAINTS =
            "Note can only contain English letters, numbers, spaces, "
                    + "and these symbols: ` ~ ! @ # $ % ^ & * ( ) - _ = + [ { ] } \\ | ; : ' \" , < . > / ?";
    public static final String MESSAGE_EMPTY_NOTE =
            "Note cannot be empty.";

    public static final String VALIDATION_REGEX =
            "[A-Za-z0-9`~!@#$%^&*()\\-_=+\\[\\]{}\\\\|;:'\",<.>/? ]+";

    public static final Note EMPTY = new Note("");

    public final String value;

    /**
     * Constructs a {@code Note}.
     *
     * @param note A valid note.
     */
    public Note(String note) {
        requireNonNull(note);
        checkArgument(note.length() <= MAX_LENGTH, MESSAGE_LENGTH_CONSTRAINTS);
        checkArgument(note.isEmpty() || hasValidCharacters(note), MESSAGE_CONSTRAINTS);
        value = note;
    }

    /**
     * Returns true if a given string is a valid note.
     */
    public static boolean isValidNote(String test) {
        requireNonNull(test);
        return test.isEmpty() || (test.length() <= MAX_LENGTH && hasValidCharacters(test));
    }

    /**
     * Returns true if a given string contains only valid note characters.
     */
    public static boolean hasValidCharacters(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Note otherNote)) {
            return false;
        }

        return value.equals(otherNote.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
