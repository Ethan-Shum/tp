package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditApplicationDescriptor;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.Company;
import seedu.address.model.application.Role;
import seedu.address.model.application.Url;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditApplicationDescriptor objects.
 */
public class EditApplicationDescriptorBuilder {

    private EditApplicationDescriptor descriptor;

    public EditApplicationDescriptorBuilder() {
        descriptor = new EditApplicationDescriptor();
    }

    public EditApplicationDescriptorBuilder(EditApplicationDescriptor descriptor) {
        this.descriptor = new EditApplicationDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditApplicationDescriptor} with fields containing {@code application}'s details
     */
    public EditApplicationDescriptorBuilder(Application application) {
        descriptor = new EditApplicationDescriptor();
        descriptor.setCompany(application.getCompany());
        descriptor.setRole(application.getRole());
        descriptor.setApplicationDate(application.getApplicationDate());
        application.getUrl().ifPresent(descriptor::setUrl);
        descriptor.setTags(application.getTags());
    }

    /**
     * Sets the {@code Company} of the {@code EditApplicationDescriptor} that we are building.
     */
    public EditApplicationDescriptorBuilder withCompany(String company) {
        descriptor.setCompany(new Company(company));
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code EditApplicationDescriptor} that we are building.
     */
    public EditApplicationDescriptorBuilder withRole(String role) {
        descriptor.setRole(new Role(role));
        return this;
    }

    /**
     * Sets the {@code ApplicationDate} of the {@code EditApplicationDescriptor} that we are building.
     */
    public EditApplicationDescriptorBuilder withApplicationDate(String applicationDate) {
        descriptor.setApplicationDate(new ApplicationDate(applicationDate));
        return this;
    }

    /**
     * Sets the {@code Url} of the {@code EditApplicationDescriptor} that we are building.
     */
    public EditApplicationDescriptorBuilder withUrl(String url) {
        descriptor.setUrl(new Url(url));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditApplicationDescriptor}
     * that we are building.
     */
    public EditApplicationDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditApplicationDescriptor build() {
        return descriptor;
    }
}
