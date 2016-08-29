package practice_8;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import java.io.File;

/**
 * Created by swanta on 27.08.16.
 */
public class UserBox extends VBox {
private static final String EMPTY_NAME_PROMPT = "Press LMB to set name. RMB to set photo.";
    private static final String EMPTY_PHOTO_PATH = "user-alt-128.png";

    SimpleObjectProperty<User> user = new SimpleObjectProperty<User>() {{
        addListener((observable, oldValue, newValue) -> {
            userName.setValue(newValue.getName());
            userPhotoPath.setValue(newValue.getPathToPhoto());
        });
    }};
    SimpleStringProperty userName = new SimpleStringProperty() {{
        addListener((observable, oldValue, newValue) -> {
            user.getValue().setName(newValue);
            labelImg.setText( newValue.isEmpty()
                ? EMPTY_NAME_PROMPT
                : newValue);
        });
    }};
    SimpleStringProperty userPhotoPath = new SimpleStringProperty() {{
        addListener((observable, oldValue, newValue) -> {
            user.getValue().setPathToPhoto(newValue);
            if (newValue.isEmpty() || !(new File(newValue).exists())) setPhoto(EMPTY_PHOTO_PATH);
        });
    }};

    Label labelImg = new Label(EMPTY_NAME_PROMPT) {{
        setGraphicTextGap(10);

    }};
    Dialog inputNewName = new TextInputDialog() {
        {
            setHeaderText("Input your name");
            setOnHiding(event -> userName.setValue(this.getEditor().getText()));
        }
    };
    Dialog inputPhotoPath = new TextInputDialog() {
        {
            setHeaderText("Input path to your photo");
            setOnHiding(event -> System.out.println(this.getEditor().getText()));
        }
    };

    {
        getChildren().addAll(labelImg);
        setOnMouseClicked(event -> {
            switch (event.getButton()) {
                case PRIMARY:
                    inputNewName.show();
                    break;
                case SECONDARY:
                    inputPhotoPath.show();
                    break;
            }
        });
    }

    public void setUser (User user) {
        this.user.setValue(user);
    }
    
    public void setPhoto (String filePath) {
        Image image = new Image(getClass().getResourceAsStream(filePath));
        ImageView photo = new ImageView(image);
        System.out.println("photo: " + filePath);
        System.out.println(photo.getX());
        photo.setFitHeight(200);
        photo.setFitWidth(200);
        labelImg.setGraphic(photo);
    }
}
