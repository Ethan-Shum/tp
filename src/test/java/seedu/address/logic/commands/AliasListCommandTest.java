package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class AliasListCommandTest {

    @Test
    public void execute_noAliases_showsNoAliasesMessage() throws Exception {
        Model model = new ModelManager();
        AliasListCommand command = new AliasListCommand();

        CommandResult result = command.execute(model);

        assertEquals(new CommandResult(AliasListCommand.MESSAGE_NO_ALIASES), result);
    }

    @Test
    public void execute_withAliases_listsAliases() throws Exception {
        Model model = new ModelManager();
        model.setAlias("ls", "list");
        model.setAlias("q", "exit");

        AliasListCommand command = new AliasListCommand();
        CommandResult result = command.execute(model);

        String expectedMessage = "Current aliases:\n"
                + "ls -> list\n"
                + "q -> exit\n"
                + "Total: 2 aliases";

        assertEquals(new CommandResult(expectedMessage), result);
    }
}
