package practice_8;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;

/**
 * Created by swanta on 27.08.16.
 */
public class UserBox extends VBox {
    Label labelImg = new Label("press to set name") {{
        setGraphicTextGap(10);
    }};
    
    int textFieldSize = 70;
    TextField nameEdit = new TextField() {{
        setWidth(textFieldSize);
    }};
    
    int buttonSize = 30;
    Button saveName = new Button("OK") {{
        setWidth(buttonSize);
//        setTranslateY(buttonSize);
    }};
    Button discardName = new Button("X") {{
        setWidth(buttonSize);
//        setTranslateY(buttonSize);
    }};

    int nameEditBoxSpace = 1;
    HBox nameEditBox = new HBox(1, nameEdit, saveName, discardName) {{
        setWidth(textFieldSize + 2*buttonSize + 2*nameEditBoxSpace);
    }};

    
    public UserBox() {
        getChildren().addAll(labelImg, nameEditBox);
    }
    public void setUser (LibraryUser user) {
        setPhoto(user.getPathToPhoto());
        String userName = user.getName();
        labelImg.setText(userName);
        nameEdit.setText(userName);
    }
    
    public void setPhoto (String filePath) {
        Image image = new Image(getClass().getResourceAsStream(filePath));
        ImageView photo = new ImageView(image);
        labelImg.setGraphic(photo);
    }
}
