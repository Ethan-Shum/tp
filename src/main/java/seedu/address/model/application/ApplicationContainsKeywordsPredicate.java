package seedu.address.model.application;

import java.time.LocalDate;
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
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<String> urlKeywords;
    private final List<String> statusKeywords;

    /**
     * Constructs a predicate that checks if an application matches the given company, role, application date,
     * url and status keywords.
     * At least one list of keywords must be non-empty.
     *
     * @param companyKeywords list of keywords to match against the company name
     * @param roleKeywords list of keywords to match against the role
     * @param startDate start date to match against the application date
     * @param endDate end date to match against the application date
     * @param urlKeywords list of keywords to match against the url
     * @param statusKeywords list of keywords to match against the status
     */
    public ApplicationContainsKeywordsPredicate(List<String> companyKeywords, List<String> roleKeywords,
                                                LocalDate startDate, LocalDate endDate, List<String> urlKeywords,
                                                List<String> statusKeywords) {
        this.companyKeywords = companyKeywords;
        this.roleKeywords = roleKeywords;
        this.startDate = startDate;
        this.endDate = endDate;
        this.urlKeywords = urlKeywords;
        this.statusKeywords = statusKeywords;
    }

    @Override
    public boolean test(Application application) {
        if (companyKeywords.isEmpty() && roleKeywords.isEmpty() && startDate == null && endDate == null
                && urlKeywords.isEmpty() && statusKeywords.isEmpty()) {
            return false;
        }

        boolean matchesCompany = companyKeywords.isEmpty() || companyKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(application.getCompany().value, keyword));

        boolean matchesRole = roleKeywords.isEmpty() || roleKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(application.getRole().value, keyword));

        boolean matchesApplicationDate = true;
        LocalDate appDate = application.getApplicationDate().getValue();
        if (startDate != null && appDate.isBefore(startDate)) {
            matchesApplicationDate = false;
        }
        if (endDate != null && appDate.isAfter(endDate)) {
            matchesApplicationDate = false;
        }

        boolean matchesUrl = urlKeywords.isEmpty() || (application.getUrl().isPresent()
                && urlKeywords.stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(application.getUrl().get().value, keyword)));

        boolean matchesStatus = statusKeywords.isEmpty() || statusKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(application.getStatus().toString(), keyword));

        return matchesCompany && matchesRole && matchesApplicationDate && matchesUrl && matchesStatus;
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
                && ((startDate == null && otherPredicate.startDate == null)
                    || (startDate != null && startDate.equals(otherPredicate.startDate)))
                && ((endDate == null && otherPredicate.endDate == null)
                    || (endDate != null && endDate.equals(otherPredicate.endDate)))
                && urlKeywords.equals(otherPredicate.urlKeywords)
                && statusKeywords.equals(otherPredicate.statusKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("companyKeywords", companyKeywords)
                .add("roleKeywords", roleKeywords)
                .add("startDate", startDate)
                .add("endDate", endDate)
                .add("urlKeywords", urlKeywords)
                .add("statusKeywords", statusKeywords)
                .toString();
    }
}
