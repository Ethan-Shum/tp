package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.AliasListCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditApplicationDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationContainsKeywordsPredicate;
import seedu.address.testutil.ApplicationBuilder;
import seedu.address.testutil.ApplicationUtil;
import seedu.address.testutil.EditApplicationDescriptorBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser(new HashMap<>());

    @Test
    public void parseCommand_add() throws Exception {
        Application application = new ApplicationBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(ApplicationUtil.getAddCommand(application));
        assertEquals(new AddCommand(application), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_APPLICATION.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_APPLICATION), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Application application = new ApplicationBuilder().build();
        EditApplicationDescriptor descriptor = new EditApplicationDescriptorBuilder(application).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_APPLICATION.getOneBased() + " "
                + ApplicationUtil.getEditApplicationDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_APPLICATION, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " n/" + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new ApplicationContainsKeywordsPredicate(keywords, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>())), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_alias() throws Exception {
        AliasCommand command = (AliasCommand) parser.parseCommand("alias ls list");
        assertEquals(new AliasCommand("ls", "list"), command);
    }

    @Test
    public void parseCommand_aliasResolution() throws Exception {
        java.util.Map<String, String> aliases = new java.util.HashMap<>();
        aliases.put("ls", "list");
        AddressBookParser aliasParser = new AddressBookParser(aliases);

        assertTrue(aliasParser.parseCommand("ls") instanceof ListCommand);
    }

    @Test
    public void parseCommand_aliasRemoved_noLongerWorks() throws Exception {
        java.util.Map<String, String> aliases = new java.util.HashMap<>();
        aliases.put("ls", "list");
        aliases.remove("ls");
        AddressBookParser aliasParser = new AddressBookParser(aliases);

        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> aliasParser.parseCommand("ls"));
    }

    @Test
    public void parseCommand_unalias() throws Exception {
        UnaliasCommand command = (UnaliasCommand) parser.parseCommand("unalias ls");
        assertEquals(new UnaliasCommand("ls"), command);
    }

    @Test
    public void parseCommand_aliasList() throws Exception {
        assertTrue(parser.parseCommand(AliasListCommand.COMMAND_WORD) instanceof AliasListCommand);
        assertTrue(parser.parseCommand(AliasListCommand.COMMAND_WORD + " 3") instanceof AliasListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
