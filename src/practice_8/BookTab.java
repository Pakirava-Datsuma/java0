package practice_8;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ListChangeListener;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swanta on 27.08.16.
 */
public class BookTab extends Tab {
    private SimpleObjectProperty<Book> currentBook = new SimpleObjectProperty<Book>() {{
        addListener((observable, oldValue, newValue) -> setText(((Book)newValue).getTitleAndAuthor()));
    }};


    private Axis xAxis = new CategoryAxis();
    private Axis yAxis = new NumberAxis();
    private XYChart.Series<String, Number> actualSeries = new LineChart.Series<String, Number>();
    private XYChart.Series<String, Number> planSeries = new LineChart.Series<String, Number>();
    private LineChart<String, Number> quantityChart = new LineChart<String, Number>(xAxis, yAxis){{
        setMinSize(200, 200);
        getData().addAll(actualSeries, planSeries);
        currentBook.addListener((observable, oldValue, newValue) -> {
            actualSeries.setData(newValue.getSeriesData());
            planSeries.setData(newValue.getPlanData());
        });
        xAxis.setLabel("Period");
        yAxis.setLabel("Quantity");
        actualSeries.setName("in fact reading");
        planSeries.setName("have to be read");

    }};
    private TextField addTodayPagesQuantity = new TextField(){{setPromptText("add new quantity");}};
    private Button addTodayPagesQuantityButton = new Button("Add") {{
        setOnAction(e -> {
            currentBook.getValue().addTodayReadPagesQuantity(Integer.parseInt(addTodayPagesQuantity.getText()));
//            addTodayPagesQuantity.clear();
        });
    }};
    private HBox addTodayPagesQuantityGroup = new HBox(addTodayPagesQuantity, addTodayPagesQuantityButton);
    private Pane progressBox = new VBox(quantityChart, addTodayPagesQuantityGroup);

    {
        setContent(progressBox);
    }

    public BookTab(Book Book) {
        this.currentBook.setValue(Book);
    }

    public Book getBook() {
        return currentBook.getValue();
    }

    public boolean has(Book book) {
        return getBook() == book;
    }
}
