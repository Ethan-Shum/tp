package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path lockedInFilePath = Paths.get("data" , "lockedin.json");
    private Map<String, String> aliases = new HashMap<>();

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
        setAliases(newUserPrefs.getAliases());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAddressBookFilePath() {
        return lockedInFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.lockedInFilePath = addressBookFilePath;
    }

    /**
     * Returns an unmodifiable view of the aliases stored in user preferences.
     *
     * @return A map containing alias-command word pairs.
     */
    public Map<String, String> getAliases() {
        return Collections.unmodifiableMap(aliases);
    }

    /**
     * Replaces the current aliases with the given aliases.
     *
     * @param aliases A map of alias-command word pairs to set.
     */
    public void setAliases(Map<String, String> aliases) {
        requireNonNull(aliases);
        this.aliases = new HashMap<>(aliases);
    }

    /**
     * Adds or updates an alias for a command word.
     *
     * @param alias The alias to be added or updated.
     * @param commandWord The command word that the alias maps to.
     */
    public void setAlias(String alias, String commandWord) {
        requireNonNull(alias);
        requireNonNull(commandWord);
        aliases.put(alias, commandWord);
    }

    /**
     * Removes the given alias.
     *
     * @param alias The alias to be removed.
     */
    public void removeAlias(String alias) {
        requireNonNull(alias);
        aliases.remove(alias);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserPrefs)) {
            return false;
        }

        UserPrefs otherUserPrefs = (UserPrefs) other;
        return guiSettings.equals(otherUserPrefs.guiSettings)
                && lockedInFilePath.equals(otherUserPrefs.lockedInFilePath)
                && aliases.equals(otherUserPrefs.aliases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, lockedInFilePath, aliases);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + lockedInFilePath);
        sb.append("\nAliases : " + aliases);
        return sb.toString();
    }

}
