package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class AliasCommandTest {

    @Test
    public void execute_validAlias_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        AliasCommand aliasCommand = new AliasCommand("ls", "list");

        String expectedMessage = String.format(AliasCommand.MESSAGE_SUCCESS, "ls", "list");
        expectedModel.setAlias("ls", "list");

        assertCommandSuccess(aliasCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTargetCommand_throwsCommandException() {
        Model model = new ModelManager();

        AliasCommand aliasCommand = new AliasCommand("ls", "notACommand");

        assertCommandFailure(aliasCommand, model, AliasCommand.MESSAGE_INVALID_TARGET);
    }

    @Test
    public void execute_aliasIsExistingCommandWord_throwsCommandException() {
        Model model = new ModelManager();

        AliasCommand aliasCommand = new AliasCommand("list", "delete");

        assertCommandFailure(aliasCommand, model, AliasCommand.MESSAGE_INVALID_ALIAS);
    }

    @Test
    public void execute_existingAlias_overwritesAlias() throws Exception {
        Model model = new ModelManager();
        model.setAlias("ls", "list");

        AliasCommand command = new AliasCommand("ls", "copy");
        CommandResult result = command.execute(model);

        assertEquals(String.format(AliasCommand.MESSAGE_OVERWRITE_SUCCESS,
                "ls", "copy", "list"), result.getFeedbackToUser());
        assertEquals("copy", model.getAliases().get("ls"));
    }

    @Test
    public void equals() {
        AliasCommand firstCommand = new AliasCommand("ls", "list");
        AliasCommand secondCommand = new AliasCommand("ls", "copy");
        AliasCommand firstCommandCopy = new AliasCommand("ls", "list");

        assertEquals(firstCommand, firstCommand);
        assertEquals(firstCommand, firstCommandCopy);

        assertNotEquals(firstCommand, 1);
        assertNotEquals(firstCommand, null);
        assertNotEquals(firstCommand, secondCommand);
        assertNotEquals(firstCommand, new ClearCommand());
    }
}
