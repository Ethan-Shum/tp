package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showApplicationAtIndex;
import static seedu.address.testutil.TypicalApplications.APPLIED_APPLICATION;
import static seedu.address.testutil.TypicalApplications.REJECTED_APPLICATION;
import static seedu.address.testutil.TypicalApplications.WITHDRAWN_APPLICATION;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DropCommand}.
 */
public class DropCommandTest {

    @Test
    public void execute_containsRejectedAndWithdrawn_success() {
        Model model = new ModelManager(getAddressBookWithTerminalApplications(), new UserPrefs());
        Model expectedModel = new ModelManager(getAddressBookWithTerminalApplications(), new UserPrefs());
        expectedModel.deleteApplication(REJECTED_APPLICATION);
        expectedModel.deleteApplication(WITHDRAWN_APPLICATION);

        String expectedMessage = getExpectedDropMessage(List.of(REJECTED_APPLICATION, WITHDRAWN_APPLICATION));
        assertCommandSuccess(new DropCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noRejectedOrWithdrawnInCurrentList_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandFailure(new DropCommand(), model,
                DropCommand.MESSAGE_NO_REJECTED_WITHDRAWN_IN_CURRENT_LIST);
    }

    @Test
    public void execute_filteredListWithoutRejectedWithdrawn_throwsCommandException() {
        Model model = new ModelManager(getAddressBookWithOneRejectedApplication(), new UserPrefs());
        showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        assertCommandFailure(new DropCommand(), model,
                DropCommand.MESSAGE_NO_REJECTED_WITHDRAWN_IN_CURRENT_LIST);
    }

    @Test
    public void execute_filteredListWithTerminalStatuses_success() {
        Model model = new ModelManager(getAddressBookWithMixedApplications(), new UserPrefs());
        model.updateFilteredApplicationList(Application::hasTerminalStatus);

        Model expectedModel = new ModelManager(getAddressBookWithMixedApplications(), new UserPrefs());
        expectedModel.updateFilteredApplicationList(Application::hasTerminalStatus);
        expectedModel.deleteApplication(REJECTED_APPLICATION);
        expectedModel.deleteApplication(WITHDRAWN_APPLICATION);

        String expectedMessage = getExpectedDropMessage(List.of(REJECTED_APPLICATION, WITHDRAWN_APPLICATION));
        assertCommandSuccess(new DropCommand(), model, expectedMessage, expectedModel);
        assertTrue(model.hasApplication(APPLIED_APPLICATION));
    }

    @Test
    public void equals() {
        DropCommand firstDropCommand = new DropCommand();
        DropCommand secondDropCommand = new DropCommand();

        assertEquals(firstDropCommand, secondDropCommand);
        assertNotEquals(1, firstDropCommand);
        assertNotEquals(null, firstDropCommand);
    }

    @Test
    public void toStringMethod() {
        DropCommand dropCommand = new DropCommand();
        String expected = DropCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, dropCommand.toString());
    }

    /**
     * Returns an address book with rejected and withdrawn applications.
     */
    private AddressBook getAddressBookWithTerminalApplications() {
        AddressBook addressBook = getTypicalAddressBook();
        addressBook.addApplication(REJECTED_APPLICATION);
        addressBook.addApplication(WITHDRAWN_APPLICATION);
        return addressBook;
    }

    /**
     * Returns an address book with one rejected application.
     */
    private AddressBook getAddressBookWithOneRejectedApplication() {
        AddressBook addressBook = getTypicalAddressBook();
        addressBook.addApplication(REJECTED_APPLICATION);
        return addressBook;
    }

    /**
     * Returns an address book with both terminal and non-terminal applications.
     */
    private AddressBook getAddressBookWithMixedApplications() {
        AddressBook addressBook = getTypicalAddressBook();
        addressBook.addApplication(REJECTED_APPLICATION);
        addressBook.addApplication(WITHDRAWN_APPLICATION);
        addressBook.addApplication(APPLIED_APPLICATION);
        return addressBook;
    }

    private static String getExpectedDropMessage(List<Application> droppedApplications) {
        String message = String.format(DropCommand.MESSAGE_DROP_APPLICATIONS_SUCCESS, droppedApplications.size());
        String details = droppedApplications.stream()
                .map(application -> "- " + Messages.format(application))
                .collect(Collectors.joining("\n"));
        return message + "\n" + details;
    }
}
