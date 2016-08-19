package practice_8;

import com.sun.istack.internal.NotNull;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by swanta on 17.07.16.
 */
public class LibraryGUI extends Application {
    private Library library;
    private SimpleObjectProperty<Book> selectedBook = new SimpleObjectProperty<>();
    @NotNull
    private SimpleObjectProperty<Book> currentBook = new SimpleObjectProperty<Book>(){{
        selectedBook.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setValue(newValue);
            }
        });
    }};

    @Override
    public void stop() throws Exception {
        library.saveBooksToFile();
        super.stop();
    }

    @Override
    public void init() throws Exception {
        library = new Library(new File("library.lib")) {
            {
                loadBooksFromFile();
            }};
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
                setItems(library.getBooks());
                getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    selectedBook.setValue((Book)newValue);
                });
            }};

        VBox tableViewBox = new VBox(10) {{
                getChildren().addAll(tableTitle, tableView);
//              setPadding(new Insets(10));
            }};

//        ----------- start TextFields
        TextField addAuthorField = new TextField() {{
            setPromptText("print author here");}};

        TextField addSubjectField = new TextField() {{
            setPromptText("print subject here");}};

        TextField addTitleField = new TextField() {{
            setPromptText("print title here");}};

        TextField addPagesField = new TextField() {{
            setPromptText("print pages count here");}};

//        ------------end TextFields

        Button tableAddButton = new Button("+") {{
                setOnAction((ActionEvent e) -> {
                    if ( !(
                            addAuthorField.getText().isEmpty() ||
                            addPagesField.getText().isEmpty() ||
                            addTitleField.getText().isEmpty() ||
                            addSubjectField.getText().isEmpty())) {
                        library.addBook(addTitleField.getText(),
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
                setOnAction((ActionEvent e) -> {
                    library.getBooks().remove(tableView.getSelectionModel().getSelectedItem());
                });
            }};
        HBox tableButtonsBox = new HBox(10, tableAddButton, tableRemoveButton);
        VBox tableControlsBox = new VBox(10, addTitleField, addAuthorField, addSubjectField, addPagesField, tableButtonsBox);
        HBox libraryTabBox = new HBox(10, tableViewBox, tableControlsBox);
        Tab libraryTab = new Tab("MyLibrary") {{
                setContent(libraryTabBox);
            }};
        Axis xAxis = new CategoryAxis();
                xAxis.setLabel("Period");
        Axis yAxis = new NumberAxis();
                yAxis.setLabel("Quantity");
        XYChart.Series<String, Number> actualSeries = new LineChart.Series<String, Number>();
            actualSeries.setName("in fact reading");
        XYChart.Series<String, Number> planSeries = new LineChart.Series<String, Number>();
            planSeries.setName("have to be read");
        LineChart<String, Number> quantityChart = new LineChart<String, Number>(xAxis, yAxis){{
                setMinSize(200, 200);
                getData().addAll(actualSeries, planSeries);
                currentBook.addListener((observable, oldValue, newValue) -> {
                    actualSeries.setData(newValue.getSeries().getData());
                });
            }};
        TextField addTodayPagesQuantity = new TextField(){{setPromptText("add new quantity");}};
        Button addTodayPagesQuantityButton = new Button("Add") {{
                setOnAction(e -> {
                    currentBook.getValue().addTodayReadPagesQuantity(Integer.parseInt(addTodayPagesQuantity.getText()));
//            addTodayPagesQuantity.clear();
                });
            }};
        HBox addTodayPagesQuantityGroup = new HBox(addTodayPagesQuantity, addTodayPagesQuantityButton);
        Pane progressBox = new VBox (quantityChart, addTodayPagesQuantityGroup);

        Tab progressTab = new Tab("Progress", progressBox) {{
                selectedBook.addListener((observable, oldValue, newValue) -> {
                    disableProperty().setValue(newValue == null);
                });

            }};
        TabPane root = new TabPane() {{
                setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
                getTabs().addAll(libraryTab, progressTab);
//        root.minHeightProperty().set(300);
                setMinSize(300, 500);
            }};

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}

