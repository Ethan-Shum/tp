package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.application.Application;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX =
            "The application index provided is invalid";
    public static final String MESSAGE_APPLICATIONS_LISTED_OVERVIEW = "%1$d applications listed! "
            + "To restore the full list, use the command: 'list'";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code application} for display to the user.
     */
    public static String format(Application application) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Company: ")
                .append(application.getCompany())
                .append("; Role: ")
                .append(application.getRole())
                .append("; Status: ")
                .append(application.getStatus())
                .append("; Application Date: ")
                .append(application.getApplicationDate())
                .append("; URL: ")
                .append(application.getUrl().map(u -> u.value).orElse("-"))
                .append("; Note: ")
                .append(application.getNote().toString().isEmpty() ? "-" : application.getNote());
        return builder.toString();
    }

}
