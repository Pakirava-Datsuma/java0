package practice_8;

import com.sun.istack.internal.NotNull;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by swanta on 17.07.16.
 */
public class LibraryGUI extends Application {
    private SimpleObjectProperty<Library> currentLibrary = new SimpleObjectProperty<Library>(){{
        addListener( (observable, oldLibrary, newLibrary) -> {
            if (oldLibrary != null) {
                Library.saveLibrary(oldLibrary);
            }
        });
    }};
    private ObservableList<Library> libraries;
    private boolean firstRun; //TODO: hello message at first run
    private TabPane root = new TabPane();

    private BookTabs bookTab = new BookTabs(currentLibrary.getBooks(), root.getTabs());

    private SimpleObjectProperty<Book> selectedBook =
            new SimpleObjectProperty<>();// link to tableView in tableView constructor section

    @NotNull
    private SimpleObjectProperty<Book> currentBook = new SimpleObjectProperty<Book>(){{
        selectedBook.addListener((observable, oldValue, newValue) -> {if (newValue != null) setValue(newValue);});}};
    private SimpleBooleanProperty unselectedBook = new SimpleBooleanProperty(){{
        selectedBook.addListener((observable, oldValue, newValue) -> setValue(newValue == null));}};

    @Override
    public void stop() throws Exception {
        currentLibrary.saveBooksToFile();
        super.stop();
    }

    @Override
    public void init() throws Exception {
        List<Library> libraries = Library.getSerializedLibraries();
        this.libraries = FXCollections.observableList(libraries);
        firstRun = libraries.isEmpty();
        if (firstRun) {
            currentLibrary = Library.createNonSerializableLibrary();
        }
        else {
            libraryChoice.setItems(this.libraries);
            currentLibrary = libraries.get(0);
            libraryChoice.setValue(currentLibrary);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

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
            setMinWidth(50);
            setCellValueFactory(new PropertyValueFactory<>("Pages"));
        }};
        TableColumn subjectColumn = new TableColumn("Genre") {{
            setMinWidth(50);
            setCellValueFactory(new PropertyValueFactory<>("Genre"));
        }};
        TableView tableView = new TableView() {{
            setMinSize(400, 300);
            getColumns().addAll(titleColumn, authorColumn, subjectColumn, pagesColumn);
// link List<Book> to tablView
            setItems(currentLibrary.getBooks());
// link tableView to SelectedBook Property
            getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                selectedBook.setValue((Book) newValue);
                setOnMouseClicked(event -> {
                    System.out.println("mouse.doubleclick");
                    if (event.getClickCount() == 2 && !unselectedBook.getValue()) {
                        root.getSelectionModel().select(
                                bookTab.add(currentBook.getValue()));
                    }
                });
            });
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

        Button tableAddButton = new Button("+") {{
            setOnAction((ActionEvent e) -> {
                if (!(
                        addAuthorField.getText().isEmpty() ||
                                addPagesField.getText().isEmpty() ||
                                addTitleField.getText().isEmpty() ||
                                addSubjectField.getText().isEmpty())) {
                    currentLibrary.addBook(addTitleField.getText(),
                            addAuthorField.getText(),
                            addSubjectField.getText(),
                            Integer.parseInt(addPagesField.getText()));
                    addAuthorField.clear();
                    addPagesField.clear();
                    addTitleField.clear();
                    addSubjectField.clear();
                }
            });
        }};
        Button tableRemoveButton = new Button("-") {{
            setDisable(true);
            unselectedBook.addListener((observable, oldValue, newValue) ->
                    setDisable(newValue));
            setOnAction((ActionEvent e) ->
                    currentLibrary.getBooks().remove(selectedBook));
        }};
        HBox tableButtonsBox = new HBox(10, tableAddButton, tableRemoveButton);
        VBox tableControlsBox = new VBox(10, addTitleField, addAuthorField, addSubjectField, addPagesField, tableButtonsBox);
        HBox libraryTabBox = new HBox(10, tableViewBox, tableControlsBox);
        Tab libraryTab = new Tab("MyLibrary") {{
            setContent(libraryTabBox);
            setClosable(false);
        }};

        Tab SPECIALtab = new UserInfoTab(currentLibrary.getBooks()) // like S.P.E.C.I.A.L in Fallout
//            selectedBook.addListener((observable, oldValue, newValue) -> {
//                disableProperty().setValue(newValue == null);
//            });
        ;
//        root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        root.getTabs().addAll(libraryTab, SPECIALtab);
        root.setMinSize(300, 500);

        currentLibrary.addBook("H.Potter", "J.Roulling", "fantasy", 321);
        currentLibrary.addBook("Diving into C++", "H.Deiteil, R.Deiteil", "study", 810);
        currentLibrary.addBook("Gun", "Aghata Christi", "detective", 524);
        currentLibrary.getBooks().forEach(Book::setRandomStatistics);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}

