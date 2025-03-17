package seedu.address.ui;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label tag;
    @FXML
    private ImageView tagType;
    @FXML
    private Label modules;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        Set<String> strModules = new HashSet<>();
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);

        // there will be only one tag.
        //TODO: change this when Tag is stored as a string in future iterations.
        tag.setText(person.getTags().stream().toList().get(0).tagName);

        // sets tag colour based on the role. TA = yellow. Prof = orange
        Image taTag = new Image(getClass().getResourceAsStream("/images/tag_TA.png"));
        Image profTag = new Image(getClass().getResourceAsStream("/images/tag_Prof.png"));
        tagType.setImage(Objects.equals(tag.getText(), "TA") ? taTag : profTag);
        person.getModules().stream()
                .sorted(Comparator.comparing(module -> module.toString()))
                .forEach(module -> strModules.add(module.getModuleCode()));
        modules.setText(String.join(", ", strModules));
    }
}
