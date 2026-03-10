package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.Company;
import seedu.address.model.application.Role;
import seedu.address.model.application.Url;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_COMPANY = "R@chel";
    private static final String INVALID_ROLE = "+651234";
    private static final String INVALID_URL = "invalid_url";
    private static final String INVALID_APPLICATION_DATE = "2026/03/09";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_COMPANY = "Rachel Walker";
    private static final String VALID_ROLE = "123456";
    private static final String VALID_URL = "https://www.rachelwalker.com";
    private static final String VALID_APPLICATION_DATE = "2026-03-09";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex(
                Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        assertEquals(INDEX_FIRST_APPLICATION, ParserUtil.parseIndex("1"));
        assertEquals(INDEX_FIRST_APPLICATION, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseCompany_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCompany((String) null));
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
    public void parseRole_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole((String) null));
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
    public void parseUrl_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseUrl((String) null));
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
        assertThrows(NullPointerException.class, () -> ParserUtil.parseApplicationDate((String) null));
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
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
