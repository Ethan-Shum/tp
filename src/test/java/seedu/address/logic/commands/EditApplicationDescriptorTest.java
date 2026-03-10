package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPLICATION_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditApplicationDescriptor;
import seedu.address.testutil.EditApplicationDescriptorBuilder;


public class EditApplicationDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditApplicationDescriptor descriptorWithSameValues = new EditApplicationDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different company -> returns false
        EditApplicationDescriptor editedAmy = new EditApplicationDescriptorBuilder(DESC_AMY)
                .withCompany(VALID_COMPANY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different role -> returns false
        editedAmy = new EditApplicationDescriptorBuilder(DESC_AMY).withRole(VALID_ROLE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different application date -> returns false
        editedAmy = new EditApplicationDescriptorBuilder(DESC_AMY)
                .withApplicationDate(VALID_APPLICATION_DATE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different url -> returns false
        editedAmy = new EditApplicationDescriptorBuilder(DESC_AMY).withUrl(VALID_URL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditApplicationDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditApplicationDescriptor editApplicationDescriptor = new EditApplicationDescriptor();
        String expected = EditApplicationDescriptor.class.getCanonicalName() + "{company="
                + editApplicationDescriptor.getCompany().orElse(null) + ", role="
                + editApplicationDescriptor.getRole().orElse(null) + ", applicationDate="
                + editApplicationDescriptor.getApplicationDate().orElse(null) + ", url="
                + editApplicationDescriptor.getUrl().orElse(null) + ", tags="
                + editApplicationDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editApplicationDescriptor.toString());
    }
}
