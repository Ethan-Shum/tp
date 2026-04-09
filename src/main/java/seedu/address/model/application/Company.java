package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's company name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompany(String)}
 */
public class Company {

    public static final int MAX_LENGTH = 100;

    public static final String MESSAGE_CONSTRAINTS =
            "Company names can only contain English letters, numbers, spaces, "
                    + "and these symbols: ` ~ ! @ # $ % ^ & * ( ) - _ = + [ { ] } \\ | ; : ' \" , < . > / ? "
                    + "\n" + "Names must not exceed " + MAX_LENGTH + " characters.";

    public static final String VALIDATION_REGEX =
            "(?=.*[A-Za-z0-9`~!@#$%^&*()\\-_=+\\[\\{\\]\\}\\\\|;:'\\\",<.>/?])"
                    + "[A-Za-z0-9`~!@#$%^&*()\\-_=+\\[\\{\\]\\}\\\\|;:'\\\",<.>/? ]+";

    public final String value;

    /**
     * Constructs a {@code Company}.
     *
     * @param company A valid company name.
     */
    public Company(String company) {
        requireNonNull(company);
        checkArgument(isValidCompany(company), MESSAGE_CONSTRAINTS);
        value = company;
    }

    /**
     * Returns true if a given string is a valid company name.
     */
    public static boolean isValidCompany(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX)
                && test.length() <= MAX_LENGTH;
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

        if (!(other instanceof Company)) {
            return false;
        }

        Company otherCompany = (Company) other;
        return value.equals(otherCompany.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
