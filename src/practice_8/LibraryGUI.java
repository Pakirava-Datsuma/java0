package practice_8;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private Book selectedBook;

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
            }};
        TextField addTodayPagesQuantity = new TextField(){{setPromptText("add new quantity");}};
        Button addTodayPagesQuantityButton = new Button("Add") {{
                setOnAction(e -> {
                    selectedBook.addTodayReadPagesQuantity(Integer.parseInt(addTodayPagesQuantity.getText()));
//            addTodayPagesQuantity.clear();
                });
            }};
        HBox addTodayPagesQuantityGroup = new HBox(addTodayPagesQuantity, addTodayPagesQuantityButton);
        Pane progressBox = new VBox (quantityChart, addTodayPagesQuantityGroup);

        Tab progressTab = new Tab("Progress", progressBox) {{
                setOnSelectionChanged(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        if (isSelected()) {
                            //applying selection must be done when item has been selected
                            selectedBook = (Book)tableView.getSelectionModel().getSelectedItems().get(0);
                            actualSeries.getData().setAll(selectedBook.getSeries().getData());
                        }
                    }
                });
                disableProperty().setValue(true);
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

