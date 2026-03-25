package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void equals() {
        AliasCommand firstCommand = new AliasCommand("ls", "list");
        AliasCommand secondCommand = new AliasCommand("rm", "delete");

        assertEquals(firstCommand, firstCommand);
        org.junit.jupiter.api.Assertions.assertNotEquals(firstCommand, secondCommand);
        org.junit.jupiter.api.Assertions.assertNotEquals(firstCommand, null);
        org.junit.jupiter.api.Assertions.assertNotEquals(firstCommand, new ClearCommand());
    }
}
