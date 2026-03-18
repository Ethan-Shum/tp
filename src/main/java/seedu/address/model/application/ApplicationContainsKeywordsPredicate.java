package seedu.address.model.application;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Application}'s {@code Company}, {@code Role}, {@code ApplicationDate} and {@code Status}
 * matches any of the keywords given.
 */
public class ApplicationContainsKeywordsPredicate implements Predicate<Application> {
    private final List<String> companyKeywords;
    private final List<String> roleKeywords;
    private final List<String> applicationDateKeywords;
    private final List<String> statusKeywords;

    /**
     * Constructs a predicate that checks if an application matches the given company, role, application date
     * and status keywords.
     * At least one list of keywords must be non-empty.
     *
     * @param companyKeywords list of keywords to match against the company name
     * @param roleKeywords list of keywords to match against the role
     * @param applicationDateKeywords list of keywords to match against the application date
     * @param statusKeywords list of keywords to match against the status
     */
    public ApplicationContainsKeywordsPredicate(List<String> companyKeywords, List<String> roleKeywords,
                                                List<String> applicationDateKeywords, List<String> statusKeywords) {
        this.companyKeywords = companyKeywords;
        this.roleKeywords = roleKeywords;
        this.applicationDateKeywords = applicationDateKeywords;
        this.statusKeywords = statusKeywords;
    }

    @Override
    public boolean test(Application application) {
        if (companyKeywords.isEmpty() && roleKeywords.isEmpty() && applicationDateKeywords.isEmpty()
                && statusKeywords.isEmpty()) {
            return false;
        }

        boolean matchesCompany = companyKeywords.isEmpty() || companyKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(application.getCompany().value, keyword));

        boolean matchesRole = roleKeywords.isEmpty() || roleKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(application.getRole().value, keyword));

        boolean matchesApplicationDate = applicationDateKeywords.isEmpty() || applicationDateKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(application.getApplicationDate().value,
                        keyword));

        boolean matchesStatus = statusKeywords.isEmpty() || statusKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(application.getStatus().toString(), keyword));

        return matchesCompany && matchesRole && matchesApplicationDate && matchesStatus;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ApplicationContainsKeywordsPredicate)) {
            return false;
        }

        ApplicationContainsKeywordsPredicate otherPredicate = (ApplicationContainsKeywordsPredicate) other;
        return companyKeywords.equals(otherPredicate.companyKeywords)
                && roleKeywords.equals(otherPredicate.roleKeywords)
                && applicationDateKeywords.equals(otherPredicate.applicationDateKeywords)
                && statusKeywords.equals(otherPredicate.statusKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("companyKeywords", companyKeywords)
                .add("roleKeywords", roleKeywords)
                .add("applicationDateKeywords", applicationDateKeywords)
                .add("statusKeywords", statusKeywords)
                .toString();
    }
}
