package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        CommandResult result = new ClearCommand().execute(model);

        assert result.getFeedbackToUser().contains("Cleared 0 application(s)");
        assert model.getFilteredApplicationList().isEmpty();
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        int initialSize = model.getFilteredApplicationList().size();

        CommandResult result = new ClearCommand().execute(model);

        assert result.getFeedbackToUser().contains("Cleared " + initialSize + " application(s)");
        assert model.getFilteredApplicationList().isEmpty();
    }

}
