package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.application.Application;

/**
 * Panel containing the list of applications.
 */
public class ApplicationListPanel extends UiPart<Region> {
    private static final String FXML = "ApplicationListPanel.fxml";
    private static final double WIDTH_THRESHOLD = 1000;
    private final Logger logger = LogsCenter.getLogger(ApplicationListPanel.class);

    @FXML
    private VBox containerVBox;

    @FXML
    private GridPane headerGridPane;

    @FXML
    private ListView<Application> applicationListView;

    /**
     * Creates a {@code ApplicationListPanel} with the given {@code ObservableList}.
     */
    public ApplicationListPanel(ObservableList<Application> applicationList) {
        super(FXML);
        applicationListView.setItems(applicationList);
        applicationListView.setCellFactory(listView -> new ApplicationListViewCell());
        Platform.runLater(this::setupResponsiveLayout);
    }

    /**
     * Sets up responsive layout behavior by listening to container width changes.
     * Triggers column constraint updates whenever the container width changes.
     */
    private void setupResponsiveLayout() {
        containerVBox.widthProperty().addListener((observable, oldValue, newValue) -> {
            updateColumnConstraints(newValue.doubleValue());
        });
        updateColumnConstraints(containerVBox.getWidth());
    }

    /**
     * Updates header column constraints based on the current width.
     * Switches between fixed-width layout (for small windows &lt; 1000px)
     * and responsive percentage-based layout (for larger windows &gt;= 1000px).
     *
     * @param width the current width of the container in pixels
     */
    private void updateColumnConstraints(double width) {
        headerGridPane.getColumnConstraints().clear();

        if (width < WIDTH_THRESHOLD) {
            headerGridPane.getColumnConstraints().addAll(
                createFixedColumn(40),
                createFixedColumn(100),
                createFixedColumn(175),
                createFixedColumn(100),
                createFixedColumn(100),
                createFixedColumn(200)
            );
        } else {
            headerGridPane.getColumnConstraints().addAll(
                createPercentColumn(5.4),
                createPercentColumn(13.4),
                createPercentColumn(23.5),
                createPercentColumn(17.5),
                createPercentColumn(13.4),
                createPercentColumn(26.8)
            );
        }
    }

    /**
     * Creates a column constraint with a responsive percentage-based width.
     *
     * @param percent the percentage width for this column relative to the container width
     * @return a ColumnConstraints object configured with the specified percentage width
     */
    private ColumnConstraints createFixedColumn(double width) {
        ColumnConstraints constraint = new ColumnConstraints();
        constraint.setPrefWidth(width);
        return constraint;
    }

    private ColumnConstraints createPercentColumn(double percent) {
        ColumnConstraints constraint = new ColumnConstraints();
        constraint.setPercentWidth(percent);
        return constraint;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Application} using a {@code ApplicationCard}.
     */
    class ApplicationListViewCell extends ListCell<Application> {
        @Override
        protected void updateItem(Application application, boolean empty) {
            super.updateItem(application, empty);

            if (empty || application == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ApplicationCard(application, getIndex() + 1).getRoot());
            }
        }
    }

}
