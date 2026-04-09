package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.APPLICATION_DATE_DESC_AMAZON;
import static seedu.address.logic.commands.CommandTestUtil.APPLICATION_DATE_DESC_BYTEDANCE;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMAZON;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPLICATION_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_URL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMAZON;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BYTEDANCE;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_AMAZON;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_BYTEDANCE;
import static seedu.address.logic.commands.CommandTestUtil.URL_DESC_AMAZON;
import static seedu.address.logic.commands.CommandTestUtil.URL_DESC_BYTEDANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPLICATION_DATE_AMAZON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMAZON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMAZON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BYTEDANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_AMAZON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BYTEDANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_AMAZON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_URL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditApplicationDescriptor;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.Company;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.application.Url;
import seedu.address.testutil.EditApplicationDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, COMPANY_DESC_AMAZON, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + COMPANY_DESC_AMAZON, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + COMPANY_DESC_AMAZON, MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_noFieldSpecified_returnsDeferredNotEditedError() {
        // No field provided: parser cannot know bounds, so defers MESSAGE_NOT_EDITED to execute
        EditCommand expected = new EditCommand(INDEX_FIRST_APPLICATION,
                new EditApplicationDescriptor(), EditCommand.MESSAGE_NOT_EDITED);
        assertParseSuccess(parser, "1", expected);
    }

    @Test
    public void parse_extraPreambleText_returnsDeferredInvalidFormatError() {
        // Extra random text after valid index: defers invalid-format error so bounds check runs first
        EditCommand expected = new EditCommand(INDEX_FIRST_APPLICATION,
                new EditApplicationDescriptor(), MESSAGE_INVALID_FORMAT);

        // random text after index
        assertParseSuccess(parser, "1 some random string", expected);

        // unrecognized prefix treated as preamble text
        assertParseSuccess(parser, "1 i/ string", expected);
    }

    @Test
    public void parse_extraPreambleTextWithValidField_returnsDeferredInvalidFormatError() {
        // Extra text in preamble takes priority even when a valid field is also supplied
        EditApplicationDescriptor descriptor = new EditApplicationDescriptorBuilder()
                .withCompany(VALID_COMPANY_AMAZON).build();
        EditCommand expected = new EditCommand(INDEX_FIRST_APPLICATION, descriptor, MESSAGE_INVALID_FORMAT);

        assertParseSuccess(parser, "1 garbage" + COMPANY_DESC_AMAZON, expected);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_COMPANY_DESC, Company.MESSAGE_CONSTRAINTS); // invalid company
        assertParseFailure(parser, "1" + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS); // invalid role
        assertParseFailure(parser, "1" + INVALID_APPLICATION_DATE_DESC, ApplicationDate.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_URL_DESC, Url.MESSAGE_CONSTRAINTS); // invalid url
        assertParseFailure(parser, "1" + INVALID_STATUS_DESC, Status.MESSAGE_CONSTRAINTS); // invalid status

        // invalid role followed by valid application date
        assertParseFailure(parser, "1" + INVALID_ROLE_DESC + APPLICATION_DATE_DESC_AMAZON, Role.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                "1" + INVALID_COMPANY_DESC + INVALID_APPLICATION_DATE_DESC + URL_DESC_AMAZON + ROLE_DESC_AMAZON,
                Company.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_APPLICATION;
        String userInput = targetIndex.getOneBased() + ROLE_DESC_BYTEDANCE + STATUS_DESC_BYTEDANCE
                + APPLICATION_DATE_DESC_AMAZON + URL_DESC_AMAZON + COMPANY_DESC_AMAZON;

        EditApplicationDescriptor descriptor = new EditApplicationDescriptorBuilder().withCompany(VALID_COMPANY_AMAZON)
                .withRole(VALID_ROLE_BYTEDANCE).withApplicationDate(VALID_APPLICATION_DATE_AMAZON)
                .withUrl(VALID_URL_AMAZON)
                .withStatus(VALID_STATUS_BYTEDANCE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_APPLICATION;
        String userInput = targetIndex.getOneBased() + ROLE_DESC_BYTEDANCE + APPLICATION_DATE_DESC_AMAZON;

        EditApplicationDescriptor descriptor = new EditApplicationDescriptorBuilder().withRole(VALID_ROLE_BYTEDANCE)
                .withApplicationDate(VALID_APPLICATION_DATE_AMAZON).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // company
        Index targetIndex = INDEX_THIRD_APPLICATION;
        String userInput = targetIndex.getOneBased() + COMPANY_DESC_AMAZON;
        EditApplicationDescriptor descriptor = new EditApplicationDescriptorBuilder().withCompany(VALID_COMPANY_AMAZON)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // role
        userInput = targetIndex.getOneBased() + ROLE_DESC_AMAZON;
        descriptor = new EditApplicationDescriptorBuilder().withRole(VALID_ROLE_AMAZON).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // application date
        userInput = targetIndex.getOneBased() + APPLICATION_DATE_DESC_AMAZON;
        descriptor = new EditApplicationDescriptorBuilder().withApplicationDate(VALID_APPLICATION_DATE_AMAZON).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // url
        userInput = targetIndex.getOneBased() + URL_DESC_AMAZON;
        descriptor = new EditApplicationDescriptorBuilder().withUrl(VALID_URL_AMAZON).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // status
        userInput = targetIndex.getOneBased() + STATUS_DESC_AMAZON;
        descriptor = new EditApplicationDescriptorBuilder().withStatus(VALID_STATUS_AMAZON).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_APPLICATION;
        String userInput = targetIndex.getOneBased() + INVALID_ROLE_DESC + ROLE_DESC_BYTEDANCE;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + ROLE_DESC_BYTEDANCE + INVALID_ROLE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + ROLE_DESC_AMAZON + URL_DESC_AMAZON + APPLICATION_DATE_DESC_AMAZON
                + STATUS_DESC_AMAZON + ROLE_DESC_AMAZON + URL_DESC_AMAZON + APPLICATION_DATE_DESC_AMAZON
                + STATUS_DESC_AMAZON
                + ROLE_DESC_BYTEDANCE + URL_DESC_BYTEDANCE + APPLICATION_DATE_DESC_BYTEDANCE + STATUS_DESC_BYTEDANCE;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE, PREFIX_APPLICATION_DATE,
                        PREFIX_URL, PREFIX_STATUS));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_ROLE_DESC + INVALID_URL_DESC
                + INVALID_APPLICATION_DATE_DESC + INVALID_STATUS_DESC + INVALID_ROLE_DESC + INVALID_URL_DESC
                + INVALID_APPLICATION_DATE_DESC + INVALID_STATUS_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE, PREFIX_APPLICATION_DATE,
                        PREFIX_URL, PREFIX_STATUS));
    }
}
