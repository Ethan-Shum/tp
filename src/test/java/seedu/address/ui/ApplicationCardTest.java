package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.Label;
import seedu.address.model.application.Application;
import seedu.address.testutil.ApplicationBuilder;

public class ApplicationCardTest {

    @BeforeAll
    public static void setUpJavaFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException e) {
            // JavaFX runtime is already initialized by another test.
            latch.countDown();
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @Test
    public void constructor_appliedStatus_addsAppliedStyleClass() throws Exception {
        Application application = new ApplicationBuilder().withStatus("Applied").build();

        ApplicationCard card = createCardOnFxThread(application);
        Label statusLabel = getStatusLabel(card);

        assertEquals("Applied", statusLabel.getText());
        assertTrue(statusLabel.getStyleClass().contains("status-applied"));
    }

    @Test
    public void constructor_oaStatus_addsOaStyleClass() throws Exception {
        Application application = new ApplicationBuilder().withStatus("OA").build();

        ApplicationCard card = createCardOnFxThread(application);
        Label statusLabel = getStatusLabel(card);

        assertEquals("OA", statusLabel.getText());
        assertTrue(statusLabel.getStyleClass().contains("status-oa"));
    }

    private static ApplicationCard createCardOnFxThread(Application application) throws Exception {
        final ApplicationCard[] holder = new ApplicationCard[1];
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            holder[0] = new ApplicationCard(application, 1);
            latch.countDown();
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        return holder[0];
    }

    private static Label getStatusLabel(ApplicationCard card) throws Exception {
        Field statusField = ApplicationCard.class.getDeclaredField("status");
        statusField.setAccessible(true);
        return (Label) statusField.get(card);
    }
}

