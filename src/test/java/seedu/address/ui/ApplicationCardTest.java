package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.application.Application;
import seedu.address.testutil.ApplicationBuilder;

public class ApplicationCardTest {

    @Test
    public void getStatusStyleClass_applied_returnsAppliedClass() {
        Application application = new ApplicationBuilder().withStatus("Applied").build();
        assertEquals("status-applied", ApplicationCard.getStatusStyleClass(application));
    }

    @Test
    public void getStatusStyleClass_oa_returnsOaClass() {
        Application application = new ApplicationBuilder().withStatus("OA").build();
        assertEquals("status-oa", ApplicationCard.getStatusStyleClass(application));
    }

    @Test
    public void getStatusStyleClass_interview_returnsInterviewClass() {
        Application application = new ApplicationBuilder().withStatus("Interview").build();
        assertEquals("status-interview", ApplicationCard.getStatusStyleClass(application));
    }

    @Test
    public void getStatusStyleClass_offered_returnsOfferedClass() {
        Application application = new ApplicationBuilder().withStatus("Offered").build();
        assertEquals("status-offered", ApplicationCard.getStatusStyleClass(application));
    }

    @Test
    public void getStatusStyleClass_rejected_returnsRejectedClass() {
        Application application = new ApplicationBuilder().withStatus("Rejected").build();
        assertEquals("status-rejected", ApplicationCard.getStatusStyleClass(application));
    }

    @Test
    public void getStatusStyleClass_withdrawn_returnsWithdrawnClass() {
        Application application = new ApplicationBuilder().withStatus("Withdrawn").build();
        assertEquals("status-withdrawn", ApplicationCard.getStatusStyleClass(application));
    }

}
