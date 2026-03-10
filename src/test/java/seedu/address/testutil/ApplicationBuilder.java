package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.Company;
import seedu.address.model.application.Role;
import seedu.address.model.application.Url;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Application objects.
 */
public class ApplicationBuilder {

    public static final String DEFAULT_COMPANY = "Amazon";
    public static final String DEFAULT_ROLE = "Software Engineer Intern";
    public static final String DEFAULT_APPLICATION_DATE = "2026-03-09";

    private Company company;
    private Role role;
    private ApplicationDate applicationDate;
    private Optional<Url> url;
    private Set<Tag> tags;

    /**
     * Creates a {@code ApplicationBuilder} with the default details.
     */
    public ApplicationBuilder() {
        company = new Company(DEFAULT_COMPANY);
        role = new Role(DEFAULT_ROLE);
        applicationDate = new ApplicationDate(DEFAULT_APPLICATION_DATE);
        url = Optional.empty();
        tags = new HashSet<>();
    }

    /**
     * Initializes the ApplicationBuilder with the data of {@code applicationToCopy}.
     */
    public ApplicationBuilder(Application applicationToCopy) {
        company = applicationToCopy.getCompany();
        role = applicationToCopy.getRole();
        applicationDate = applicationToCopy.getApplicationDate();
        url = applicationToCopy.getUrl();
        tags = new HashSet<>(applicationToCopy.getTags());
    }

    /**
     * Sets the {@code Company} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Application} that we are building.
     */
    public ApplicationBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Url} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withUrl(String url) {
        this.url = (url != null) ? Optional.of(new Url(url)) : Optional.empty();
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code ApplicationDate} of the {@code Application} that we are building.
     */
    public ApplicationBuilder withApplicationDate(String applicationDate) {
        this.applicationDate = new ApplicationDate(applicationDate);
        return this;
    }

    public Application build() {
        return new Application(company, role, applicationDate, url, tags);
    }

}
