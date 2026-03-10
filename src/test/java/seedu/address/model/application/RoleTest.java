package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole() {
        // null role
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // invalid roles
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only
        assertFalse(Role.isValidRole("@Engineer")); // special character at start
        assertFalse(Role.isValidRole("Dev/Ops")); // slash not allowed
        assertFalse(Role.isValidRole("C++ Developer")); // '+' not allowed

        // valid roles
        assertTrue(Role.isValidRole("Software Engineer")); // alphanumeric with spaces
        assertTrue(Role.isValidRole("Intern")); // single word
        assertTrue(Role.isValidRole("Data Analyst Intern")); // multiple words
        assertTrue(Role.isValidRole("DevOps Engineer")); // alphanumeric mixed
    }

    @Test
    public void equals() {
        Role role = new Role("Software Engineer");

        // same values -> returns true
        assertTrue(role.equals(new Role("Software Engineer")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("Data Analyst")));
    }
}
