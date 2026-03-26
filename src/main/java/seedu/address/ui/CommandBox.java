package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final List<String> commandHistory = new ArrayList<>();
    private int historyIndex;
    private String currentCommand = "";

    @FXML
    private TextField commandTextField;

    @FXML
    private Label commandPrefixLabel;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.UP) {
                handleHistoryUp();
                event.consume();
            } else if (event.getCode() == KeyCode.DOWN) {
                handleHistoryDown();
                event.consume();
            }
        });
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();

        if (commandText.trim().isEmpty()) {
            return;
        }

        boolean isDuplicate = !commandHistory.isEmpty()
                && commandHistory.get(commandHistory.size() - 1).equals(commandText);

        if (!isDuplicate) {
            commandHistory.add(commandText);
        }

        historyIndex = commandHistory.size();
        currentCommand = "";


        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
            historyIndex = commandHistory.size() - 1;
        }
    }

    /**
     * Handles the up arrow key pressed event for command history.
     */
    private void handleHistoryUp() {
        if (commandHistory.isEmpty() || historyIndex == 0) {
            return;
        }

        if (historyIndex == commandHistory.size()) {
            currentCommand = commandTextField.getText();
        }

        historyIndex--;
        commandTextField.setText(commandHistory.get(historyIndex));
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Handles the down arrow key pressed event for command history.
     */
    private void handleHistoryDown() {
        if (commandHistory.isEmpty() || historyIndex == commandHistory.size()) {
            return;
        }

        historyIndex++;
        if (historyIndex == commandHistory.size()) {
            commandTextField.setText(currentCommand);
        } else {
            commandTextField.setText(commandHistory.get(historyIndex));
        }
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
        commandPrefixLabel.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
        commandPrefixLabel.getStyleClass().add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
