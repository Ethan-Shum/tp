package seedu.address.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.application.Application;
import seedu.address.model.application.Company;
import seedu.address.model.application.Email;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Url;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Application[] getSampleApplications() {
        return new Application[] {
            new Application(new Company("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                Optional.of(new Url("https://www.alexyeoh.com")),
                getTagSet("friends")),
            new Application(new Company("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                Optional.of(new Url("https://www.berniceyu.com")),
                getTagSet("colleagues", "friends")),
            new Application(new Company("Google"), new Phone("93210283"), new Email("charlotte@example.com"),
                Optional.of(new Url("https://www.google.com")),
                getTagSet("neighbours")),
            new Application(new Company("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                Optional.of(new Url("https://www.lidavid.com")),
                getTagSet("family")),
            new Application(new Company("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                Optional.of(new Url("https://www.irfan.com")),
                getTagSet("classmates")),
            new Application(new Company("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
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
