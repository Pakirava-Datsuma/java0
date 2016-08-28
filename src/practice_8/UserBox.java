package practice_8;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Created by swanta on 27.08.16.
 */
public class UserBox extends VBox {

    private ChoiceBox<Library> libraryChoice;

    private SimpleObjectProperty<User> user = new SimpleObjectProperty<User>() {{
        addListener((observable, oldValue, newValue) -> {
            userName.setValue(user.getValue().getName());
            userPhotoPath.setValue(user.getValue().getPathToPhoto());
        });
    }};
    private SimpleStringProperty userName = new SimpleStringProperty() {{
        addListener((observable, oldValue, newValue) -> {
            user.getValue().setName(newValue);
            labelImg.setText(newValue);
        });
    }};
    private SimpleStringProperty userPhotoPath = new SimpleStringProperty() {{
        addListener((observable, oldValue, newValue) -> {
            user.getValue().setPathToPhoto(newValue);
            setPhoto(userPhotoPath.getValue());
        });
    }};

    private final Node programInfoBox = new ProgramInfoBox();

    private Label labelImg = new Label("press to set name") {{
        setGraphicTextGap(10);
    }};
    private Dialog inputNewName = new TextInputDialog() {
        {
            setHeaderText("Input your name");
            setOnHiding(event -> this.getEditor().getText());
        }
    };
    private Dialog inputPhotoPath = new TextInputDialog() {
        {
            setHeaderText("Input path to your photo");
        }
    };

    {
        getChildren().addAll(labelImg, programInfoBox);
        libraryChoice = new ChoiceBox<Library>();

    }

    public UserBox() {
    }
    public void setUser (User user) {
        setPhoto(user.getPathToPhoto());
        String userName = user.getName();
        labelImg.setText(userName);
    }
    
    public void setPhoto (String filePath) {
        Image image = new Image(getClass().getResourceAsStream(filePath));
        ImageView photo = new ImageView(image);
        labelImg.setGraphic(photo);
    }
}
