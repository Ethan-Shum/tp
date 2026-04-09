package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.Company;
import seedu.address.model.application.Note;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.application.Url;

public class ParserUtilTest {
    private static final String INVALID_COMPANY = "Google😀";
    private static final String INVALID_ROLE = "工程师";
    private static final String INVALID_URL = "invalid_url";
    private static final String INVALID_APPLICATION_DATE = "2026/03/09";
    private static final String INVALID_STATUS = "Pending";
    private static final String INVALID_NOTE_TOO_LONG = "a".repeat(501);
    private static final String INVALID_NOTE_CHARACTERS = "hello🙂";

    private static final String VALID_COMPANY = "Rachel  Walker & Co.";
    private static final String VALID_ROLE = "QA   Engineer";
    private static final String VALID_URL = "https://www.rachelwalker.com";
    private static final String VALID_APPLICATION_DATE = "2026-03-09";
    private static final String VALID_STATUS = "Interview";
    private static final String VALID_NOTE = "Prepare for OA";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex(
                String.valueOf((long) Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        assertEquals(INDEX_FIRST_APPLICATION, ParserUtil.parseIndex("1"));
        assertEquals(INDEX_FIRST_APPLICATION, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseCompany_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCompany(null));
    }

    @Test
    public void parseCompany_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCompany(INVALID_COMPANY));
    }

    @Test
    public void parseCompany_validValueWithoutWhitespace_returnsCompany() throws Exception {
        Company expectedCompany = new Company(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(VALID_COMPANY));
    }

    @Test
    public void parseCompany_validValueWithWhitespace_returnsTrimmedCompany() throws Exception {
        String companyWithWhitespace = WHITESPACE + VALID_COMPANY + WHITESPACE;
        Company expectedCompany = new Company(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(companyWithWhitespace));
    }

    @Test
    public void parseCompany_invalidCharacters_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCompany("Google😀"));
    }

    @Test
    public void parseRole_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole(null));
    }

    @Test
    public void parseRole_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRole(INVALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithoutWhitespace_returnsRole() throws Exception {
        Role expectedRole = new Role(VALID_ROLE);
        assertEquals(expectedRole, ParserUtil.parseRole(VALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithWhitespace_returnsTrimmedRole() throws Exception {
        String roleWithWhitespace = WHITESPACE + VALID_ROLE + WHITESPACE;
        Role expectedRole = new Role(VALID_ROLE);
        assertEquals(expectedRole, ParserUtil.parseRole(roleWithWhitespace));
    }

    @Test
    public void parseRole_invalidCharacters_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRole("工程师"));
    }

    @Test
    public void parseUrl_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseUrl(null));
    }

    @Test
    public void parseUrl_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseUrl(INVALID_URL));
    }

    @Test
    public void parseUrl_validValueWithoutWhitespace_returnsUrl() throws Exception {
        Url expectedUrl = new Url(VALID_URL);
        assertEquals(expectedUrl, ParserUtil.parseUrl(VALID_URL));
    }

    @Test
    public void parseUrl_validValueWithWhitespace_returnsTrimmedUrl() throws Exception {
        String urlWithWhitespace = WHITESPACE + VALID_URL + WHITESPACE;
        Url expectedUrl = new Url(VALID_URL);
        assertEquals(expectedUrl, ParserUtil.parseUrl(urlWithWhitespace));
    }

    @Test
    public void parseApplicationDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseApplicationDate(null));
    }

    @Test
    public void parseApplicationDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseApplicationDate(INVALID_APPLICATION_DATE));
    }

    @Test
    public void parseApplicationDate_validValueWithoutWhitespace_returnsApplicationDate() throws Exception {
        ApplicationDate expectedApplicationDate = new ApplicationDate(VALID_APPLICATION_DATE);
        assertEquals(expectedApplicationDate, ParserUtil.parseApplicationDate(VALID_APPLICATION_DATE));
    }

    @Test
    public void parseApplicationDate_validValueWithWhitespace_returnsTrimmedApplicationDate() throws Exception {
        String applicationDateWithWhitespace = WHITESPACE + VALID_APPLICATION_DATE + WHITESPACE;
        ApplicationDate expectedApplicationDate = new ApplicationDate(VALID_APPLICATION_DATE);
        assertEquals(expectedApplicationDate, ParserUtil.parseApplicationDate(applicationDateWithWhitespace));
    }

    @Test
    public void parseStatus_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStatus(null));
    }

    @Test
    public void parseStatus_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStatus(INVALID_STATUS));
    }

    @Test
    public void parseStatus_validValueWithoutWhitespace_returnsStatus() throws Exception {
        assertEquals(Status.INTERVIEW, ParserUtil.parseStatus(VALID_STATUS));
    }

    @Test
    public void parseStatus_validValueWithWhitespace_returnsTrimmedStatus() throws Exception {
        String statusWithWhitespace = WHITESPACE + VALID_STATUS + WHITESPACE;
        assertEquals(Status.INTERVIEW, ParserUtil.parseStatus(statusWithWhitespace));
    }

    @Test
    public void parseNote_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNote(null));
    }

    @Test
    public void parseNote_invalidTooLongValue_throwsParseException() {
        assertThrows(ParseException.class, Note.MESSAGE_LENGTH_CONSTRAINTS, ()
                -> ParserUtil.parseNote(INVALID_NOTE_TOO_LONG));
    }

    @Test
    public void parseNote_invalidCharacters_throwsParseException() {
        assertThrows(ParseException.class, Note.MESSAGE_CONSTRAINTS, ()
                -> ParserUtil.parseNote(INVALID_NOTE_CHARACTERS));
    }

    @Test
    public void parseNote_validValueWithoutWhitespace_returnsNote() throws Exception {
        Note expectedNote = new Note(VALID_NOTE);
        assertEquals(expectedNote, ParserUtil.parseNote(VALID_NOTE));
    }

    @Test
    public void parseNote_validValueWithWhitespace_returnsTrimmedNote() throws Exception {
        String noteWithWhitespace = WHITESPACE + VALID_NOTE + WHITESPACE;
        Note expectedNote = new Note(VALID_NOTE);
        assertEquals(expectedNote, ParserUtil.parseNote(noteWithWhitespace));
    }
}
