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
        assertFalse(Role.isValidRole(""));
        assertFalse(Role.isValidRole(" "));
        assertFalse(Role.isValidRole("Software😀"));
        assertFalse(Role.isValidRole("工程师"));
        assertFalse(Role.isValidRole("𱁬"));

        // valid roles
        assertTrue(Role.isValidRole("@Engineer"));
        assertTrue(Role.isValidRole("Dev/Ops"));
        assertTrue(Role.isValidRole("C++ Developer"));
        assertTrue(Role.isValidRole("Front-End Engineer"));
        assertTrue(Role.isValidRole("SWE Intern (AI/ML)"));
        assertTrue(Role.isValidRole("QA & Testing"));
        assertTrue(Role.isValidRole("Software   Engineer"));
        assertTrue(Role.isValidRole(" Software Engineer"));
        assertTrue(Role.isValidRole("Software Engineer "));
    }

    @Test
    public void isValidRole_lengthLimit() {
        String withinLimitChars = "A".repeat(100);
        String outsideLimitChars = "A".repeat(101);

        assertTrue(Role.isValidRole(withinLimitChars));
        assertFalse(Role.isValidRole(outsideLimitChars));
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
