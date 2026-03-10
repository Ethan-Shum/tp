package seedu.address.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.Company;
import seedu.address.model.application.Role;
import seedu.address.model.application.Url;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Application[] getSampleApplications() {
        return new Application[] {
            new Application(new Company("Google"), new Role("AI Research Intern"),
                    new ApplicationDate("2025-02-14"),
                    Optional.of(new Url("https://www.alexyeoh.com")),
                    getTagSet("friends")),
            new Application(new Company("Tencent"), new Role("Software Engineer Intern"),
                    new ApplicationDate("2025-12-25"),
                    Optional.of(new Url("https://www.berniceyu.com")),
                    getTagSet("colleagues", "friends")),
            new Application(new Company("Meta"), new Role("AI Research Intern"),
                    new ApplicationDate("2025-01-01"),
                    Optional.of(new Url("https://www.google.com")),
                    getTagSet("neighbours")),
            new Application(new Company("Optiver"), new Role("AI Research Intern"),
                    new ApplicationDate("2025-04-01"),
                    Optional.of(new Url("https://www.lidavid.com")),
                    getTagSet("family")),
            new Application(new Company("NUS"), new Role("AI Research Intern"),
                    new ApplicationDate("2025-10-31"),
                    Optional.of(new Url("https://www.irfan.com")),
                    getTagSet("classmates")),
            new Application(new Company("Apple"), new Role("AI Research Intern"),
                    new ApplicationDate("2025-05-20"),
                    Optional.of(new Url("https://www.roybalakrishnan.com")),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Application sampleApplication : getSampleApplications()) {
            sampleAb.addApplication(sampleApplication);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
