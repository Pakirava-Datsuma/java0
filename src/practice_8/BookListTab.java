package practice_8;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Created by swanta on 28.08.16.
 */
public class BookListTab extends javafx.scene.control.Tab {

    Label tableTitle = new Label("MyBooks") {{
        setFont(new Font(18));
    }};

    TableColumn titleColumn = new TableColumn("Title") {{
        setMinWidth(200);
        setCellValueFactory(new PropertyValueFactory<>("Title"));
    }};

    TableColumn authorColumn = new TableColumn("Author") {{
        setMinWidth(100);
        setCellValueFactory(new PropertyValueFactory<>("Author"));
    }};

    TableColumn pagesColumn = new TableColumn("Pages") {{
        setMinWidth(25);
        setCellValueFactory(new PropertyValueFactory<>("Pages"));
    }};
    TableColumn readPagesColumn = new TableColumn("ReadPages") {{
        setMinWidth(25);
        setCellValueFactory(new PropertyValueFactory<>("ReadPages"));
    }};
    TableColumn subjectColumn = new TableColumn("Genre") {{
        setMinWidth(50);
        setCellValueFactory(new PropertyValueFactory<>("Genre"));
    }};
    TableView tableView = new TableView() {{
        setMinSize(400, 300);
        getColumns().addAll(titleColumn, authorColumn, subjectColumn, pagesColumn, readPagesColumn);
    }};

    VBox tableViewBox = new VBox(10) {{
        getChildren().addAll(tableTitle, tableView);
//              setPadding(new Insets(10));
    }};

    //        ----------- start TextFields
    TextField addAuthorField = new TextField() {{
        setPromptText("print author here");
    }};

    TextField addSubjectField = new TextField() {{
        setPromptText("print subject here");
    }};

    TextField addTitleField = new TextField() {{
        setPromptText("print title here");
    }};

    TextField addPagesField = new TextField() {{
        setPromptText("print pages count here");
    }};

//        ------------end TextFields

    Button tableAddButton = new Button("+");
    Button tableRemoveButton = new Button("-") {{
        setDisable(true);
    }};
    HBox tableButtonsBox = new HBox(10, tableAddButton, tableRemoveButton);
    VBox tableControlsBox = new VBox(10, addTitleField, addAuthorField, addSubjectField, addPagesField, tableButtonsBox);
    HBox libraryTabBox = new HBox(10, tableViewBox, tableControlsBox);

    {
        setContent(libraryTabBox);
        setClosable(false);
    }

}
