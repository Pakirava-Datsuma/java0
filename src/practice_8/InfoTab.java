package practice_8;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * Created by swanta on 28.08.16.
 */
public class InfoTab extends Tab {

    GridPane rootPane;
    UserBox userInfoPane;
    LibraryBox libraryInfoPane;
    ProgramInfoBox programInfoPane;

    {
        // self settings

        userInfoPane = new UserBox();
        libraryInfoPane = new LibraryBox();
        programInfoPane = new ProgramInfoBox();

        rootPane = new GridPane() {{
            setConstraints(libraryInfoPane, 0, 0, 1, 2);
            setConstraints(userInfoPane, 1, 0);
            setConstraints(programInfoPane, 1, 1);
        }};

        setText("Statistics");
        setContent(rootPane);
        setClosable(false);
    }

}