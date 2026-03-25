package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class UnaliasCommandTest {

    @Test
    public void execute_existingAlias_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        model.setAlias("ls", "list");
        expectedModel.setAlias("ls", "list");

        UnaliasCommand unaliasCommand = new UnaliasCommand("ls");

        String expectedMessage = String.format(UnaliasCommand.MESSAGE_SUCCESS, "ls");
        expectedModel.removeAlias("ls");

        assertCommandSuccess(unaliasCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistingAlias_throwsCommandException() {
        Model model = new ModelManager();

        UnaliasCommand unaliasCommand = new UnaliasCommand("ls");

        assertCommandFailure(unaliasCommand, model, UnaliasCommand.MESSAGE_ALIAS_NOT_FOUND);
    }

    @Test
    public void equals() {
        UnaliasCommand firstCommand = new UnaliasCommand("ls");
        UnaliasCommand secondCommand = new UnaliasCommand("rm");

        org.junit.jupiter.api.Assertions.assertEquals(firstCommand, firstCommand);
        org.junit.jupiter.api.Assertions.assertNotEquals(firstCommand, secondCommand);
        org.junit.jupiter.api.Assertions.assertNotEquals(firstCommand, null);
        org.junit.jupiter.api.Assertions.assertNotEquals(firstCommand, new ClearCommand());
    }
}
