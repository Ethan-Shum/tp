package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_APPLICATION_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPLICATION_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.application.Application;

/**
 * A utility class containing a list of {@code Application} objects to be used in tests.
 */
public class TypicalApplications {

    public static final Application ALICE = new ApplicationBuilder().withCompany("Accenture")
            .withUrl("http://alice.example.com").withApplicationDate("2026-01-01")
            .withRole("Software Engineer")
            .withTags("friends").build();
    public static final Application BENSON = new ApplicationBuilder().withCompany("Meta")
            .withUrl("http://benson.example.com")
            .withApplicationDate("2026-01-02").withRole("Data Analyst")
            .withTags("owesMoney", "friends").build();
    public static final Application CARL = new ApplicationBuilder().withCompany("Google").withRole("Mobile Developer")
            .withApplicationDate("2026-01-03").withUrl("http://carl.example.com").build();
    public static final Application DANIEL = new ApplicationBuilder().withCompany("Dyson").withRole("Backend Developer")
            .withApplicationDate("2026-01-04").withUrl("http://daniel.example.com").withTags("friends").build();
    public static final Application ELLE = new ApplicationBuilder().withCompany("Tencent").withRole("UX Designer")
            .withApplicationDate("2026-01-05").withUrl("http://elle.example.com").build();
    public static final Application FIONA = new ApplicationBuilder().withCompany("Foodpanda")
            .withRole("Frontend Developer")
            .withApplicationDate("2026-01-06").withUrl("http://fiona.example.com").build();
    public static final Application GEORGE = new ApplicationBuilder().withCompany("Grab").withRole("DevOps Engineer")
            .withApplicationDate("2026-01-07").withUrl("http://george.example.com").build();

    // Manually added
    public static final Application HOON = new ApplicationBuilder().withCompany("HP").withRole("ML Engineer")
            .withApplicationDate("2026-01-08").withUrl("http://hoon.example.com").build();
    public static final Application IDA = new ApplicationBuilder().withCompany("IBM").withRole("Systems Analyst")
            .withApplicationDate("2026-01-09").withUrl("http://ida.example.com").build();

    // Manually added - Application's details found in {@code CommandTestUtil}
    public static final Application AMY = new ApplicationBuilder().withCompany(VALID_COMPANY_AMY)
            .withRole(VALID_ROLE_AMY)
            .withApplicationDate(VALID_APPLICATION_DATE_AMY).withUrl(VALID_URL_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Application BOB = new ApplicationBuilder().withCompany(VALID_COMPANY_BOB)
            .withRole(VALID_ROLE_BOB)
            .withApplicationDate(VALID_APPLICATION_DATE_BOB).withUrl(VALID_URL_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalApplications() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical applications.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Application application : getTypicalApplications()) {
            ab.addApplication(application);
        }
        return ab;
    }

    public static List<Application> getTypicalApplications() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
