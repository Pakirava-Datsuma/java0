package practice_8;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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

    @Override
    public void stop() throws Exception {
        library.saveBooksToFile();
        super.stop();
    }

    @Override
    public void init() throws Exception {
        library = new Library(new File("library.lib"));
        library.loadBooksFromFile();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TabPane root = new TabPane();
        root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab libraryTab = new Tab("MyLibrary");
//        libraryTab.setText("MyLibrary");

        Label tableTitle = new Label("MyBooks");
        tableTitle.setFont(new Font(18));
        TableView tableView = new TableView();
        tableView.setMinSize(400, 300);
        TableColumn titleColumn = new TableColumn("Title");
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));

        TableColumn authorColumn = new TableColumn("Author");
        authorColumn.setMinWidth(100);
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("Author"));

        TableColumn subjectColumn = new TableColumn("Genre");
        subjectColumn.setMinWidth(50);
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("Genre"));

        TableColumn pagesColumn = new TableColumn("Pages");
        pagesColumn.setMinWidth(50);
        pagesColumn.setCellValueFactory(new PropertyValueFactory<>("Pages"));

        tableView.getColumns().addAll(titleColumn, authorColumn,subjectColumn, pagesColumn);
        tableView.setItems(library.getBooks());

        VBox tableViewBox = new VBox(10);
        tableViewBox.getChildren().addAll(tableTitle, tableView);
//        tableViewBox.setPadding(new Insets(10));

//        ----------- start TextFields
        TextField addAuthorField = new TextField();
        addAuthorField.setPromptText("print author here");
        TextField addSubjectField = new TextField();
        addSubjectField.setPromptText("print subject here");
        TextField addTitleField = new TextField();
        addTitleField.setPromptText("print title here");
        TextField addPagesField = new TextField();
        addPagesField.setPromptText("print pages count here");

//        ------------end TextFields

        Button tableAddButton = new Button("+");
        tableAddButton.setOnAction( (ActionEvent e) -> {
            library.addBook(addTitleField.getText(),
                    addAuthorField.getText(),
                    addSubjectField.getText(),
                    Integer.parseInt(addPagesField.getText()));
            addAuthorField.clear();
            addPagesField.clear();
            addTitleField.clear();
            addSubjectField.clear();

        });
//        tableControls[i].setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//            }
//        });
        Button tableRemoveButton = new Button("-");
        tableRemoveButton.setOnAction( (ActionEvent e) -> {
            library.getBooks().remove(tableView.getSelectionModel().getSelectedItem());
        });

        HBox tableButtonsBox = new HBox(10, tableAddButton, tableRemoveButton);

        VBox tableControlsBox = new VBox(10, addTitleField, addAuthorField, addSubjectField, addPagesField, tableButtonsBox);


        HBox libraryTabBox = new HBox(10, tableViewBox, tableControlsBox);

        libraryTab.setContent(libraryTabBox);

        Axis xAxis = new CategoryAxis();
        xAxis.setLabel("Period");
        Axis yAxis = new NumberAxis();
        yAxis.setLabel("Quantity");
        LineChart<String, Number> quantityChart = new LineChart<String, Number>(xAxis, yAxis);
        quantityChart.setMinSize(200, 200);
        XYChart.Series<String, Number> actualSeries = new LineChart.Series<String, Number>();
        XYChart.Series<String, Number> planSeries = new LineChart.Series<String, Number>();
        actualSeries.setName("in fact reading");
        planSeries.setName("have to be read");
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd");
        for (int i = 5; i > 0; i--) {
            String dateString = dateFormat.format(new Date(date.getTime() - i*(1000*3600*24)));
            actualSeries.getData().add(new XYChart.Data(dateString,new Random().nextInt(25)));
            planSeries.getData().add(new XYChart.Data(dateString, 20));
        }

        TextField addPagesQuantity = new TextField();
        Button addPagesQButton = new Button("+");
        addPagesQButton.setOnAction( e -> {
            boolean wasFound = false;
            for (Object item :
                    actualSeries.getData()) {
                if (((XYChart.Data) item).getXValue().equals(dateFormat.format(date))) {
                    Number previousNumber = ((XYChart.Data<String, Number>) item).getYValue();
                    ((XYChart.Data<String, Number>) item).setYValue((int)previousNumber + Integer.parseInt(addPagesQuantity.getText()));
                    break;
                }
//                if point not found then new point
                actualSeries.getData().add(new XYChart.Data(dateFormat.format(date), 13));

            }
            addPagesQuantity.clear();
        });

        quantityChart.getData().addAll(actualSeries, planSeries);

        TextField addTodayPagesQuantity = new TextField();
        addTodayPagesQuantity.setPromptText("add new quantity");
        Button addTodayPagesQuantityButton = new Button("Add");
        addTodayPagesQuantityButton.setOnAction(e -> {
            boolean wasFound = false;
            for (Object item : actualSeries.getData()) {
                if (((XYChart.Data)item).getXValue().equals(dateFormat.format(date))) {
                    wasFound = true;
                    Number previousNumber =((XYChart.Data<String, Number>) item).getYValue();
                    ((XYChart.Data<String, Number>) item).setYValue((int)previousNumber + Integer.parseInt(addTodayPagesQuantity.getText()));
                    break;
                }
            }

            if (!wasFound) {
                actualSeries.getData().add(new XYChart.Data(dateFormat.format(date), Integer.parseInt(addTodayPagesQuantity.getText())));
                planSeries.getData().add(new XYChart.Data(dateFormat.format(date), 20));
            }
//            addTodayPagesQuantity.clear();
        });

        HBox addTodayPagesQuantityGroup = new HBox(addTodayPagesQuantity, addTodayPagesQuantityButton);
        Pane progressBox = new VBox (quantityChart, addTodayPagesQuantityGroup);

        Tab progressTab = new Tab("Progress", progressBox);
        progressTab.setOnSelectionChanged(e -> {
            if (progressTab.isSelected()) {
                Book activeBook = (Book)library.getBooks().get(tableView.getSelectionModel().getFocusedIndex());
                actualSeries = activeBook.getSeries();
            }
        });
        progressTab.disableProperty().setValue(true);
        root.getTabs().addAll(libraryTab, progressTab);
//        root.minHeightProperty().set(300);
        root.setMinSize(300, 500);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
