package seedu.address.ui.personlist;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        personListView.setPlaceholder(createEmptyPlaceholder()); // Displays when personList is empty

        /* Disable until cell flickering bug is fixed. */
        personListView.setSelectionModel(null);

        /* The bottom code is the temporary solution to the above problem.
        *  Only use it when there is genuinely no solution to the bug */
        EventHandler<MouseEvent> handler = MouseEvent::consume;

        // block mouse click events
        personListView.addEventFilter(MouseEvent.ANY, handler);
    }

    // TODO: Add javadoc
    private VBox createEmptyPlaceholder() {
        // Set Label
        Label emptyLabel = new Label("It's rather empty here...");
        emptyLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #888;");

        // Set Image
        Image sadFaceImage = new Image(getClass().getResourceAsStream("/images/sad-face.png"));
        ImageView emptyListImage = new ImageView(sadFaceImage);
        emptyListImage.setOpacity(0.40);

        VBox placeholder = new VBox(emptyListImage, emptyLabel);
        placeholder.setAlignment(Pos.CENTER);
        placeholder.setPadding(new Insets(20));

        return placeholder;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
