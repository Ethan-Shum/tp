package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents an Application's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidApplicationDate(String)}
 */
public class ApplicationDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Application date should be a valid date in the format yyyy-MM-dd, "
                    + "for example, 2026-03-09";

    public final String value;

    /**
     * Constructs an {@code ApplicationDate}.
     *
     * @param applicationDate A valid application date.
     */
    public ApplicationDate(String applicationDate) {
        requireNonNull(applicationDate);
        checkArgument(isValidApplicationDate(applicationDate), MESSAGE_CONSTRAINTS);
        value = applicationDate;
    }

    /**
     * Returns true if a given string is a valid application date.
     */
    public static boolean isValidApplicationDate(String test) {
        requireNonNull(test);

        try {
            LocalDate parsedDate = LocalDate.parse(test);
            return parsedDate.toString().equals(test);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ApplicationDate
                && value.equals(((ApplicationDate) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
