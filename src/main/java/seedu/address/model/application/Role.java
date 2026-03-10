package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's company name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    public static final String MESSAGE_CONSTRAINTS =
            "Role should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code Company}.
     *
     * @param role A valid company name.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        value = role;
    }

    /**
     * Returns true if a given string is a valid company name.
     */
    public static boolean isValidRole(String test) {
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

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return value.equals(otherRole.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
