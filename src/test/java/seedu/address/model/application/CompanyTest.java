package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidCompany = "";
        assertThrows(IllegalArgumentException.class, () -> new Company(invalidCompany));
    }

    @Test
    public void isValidCompany() {
        // null company
        assertThrows(NullPointerException.class, () -> Company.isValidCompany(null));

        // invalid company
        assertFalse(Company.isValidCompany(""));
        assertFalse(Company.isValidCompany(" "));
        assertFalse(Company.isValidCompany("Google😀"));
        assertFalse(Company.isValidCompany("工程师"));
        assertFalse(Company.isValidCompany("𱁬"));

        // valid company
        assertTrue(Company.isValidCompany("^"));
        assertTrue(Company.isValidCompany("OpenAI"));
        assertTrue(Company.isValidCompany("ByteDance Pte. Ltd."));
        assertTrue(Company.isValidCompany("TikTok (SG)"));
        assertTrue(Company.isValidCompany("AT&T"));
        assertTrue(Company.isValidCompany("R&D Labs"));
        assertTrue(Company.isValidCompany("Google  Inc"));
        assertTrue(Company.isValidCompany(" Google"));
        assertTrue(Company.isValidCompany("Google "));
    }

    @Test
    public void isValidCompany_lengthLimit() {
        String withinLimitChars = "A".repeat(100);
        String outsideLimitChars = "A".repeat(101);

        assertTrue(Company.isValidCompany(withinLimitChars));
        assertFalse(Company.isValidCompany(outsideLimitChars));
    }

    @Test
    public void equals() {
        Company company = new Company("Valid Company");

        // same values -> returns true
        assertTrue(company.equals(new Company("Valid Company")));

        // same object -> returns true
        assertTrue(company.equals(company));

        // null -> returns false
        assertFalse(company.equals(null));

        // different types -> returns false
        assertFalse(company.equals(5.0f));

        // different values -> returns false
        assertFalse(company.equals(new Company("Other Valid Company")));
    }
}
