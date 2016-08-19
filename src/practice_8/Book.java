package practice_8;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by swanta on 17.07.16.
 */
public class Book{
    private BookData data;
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd");

    private final SimpleStringProperty title;
    private final SimpleStringProperty author;
    private final SimpleStringProperty genre;
    private final IntegerProperty pages;

    private ObservableList<XYChart.Data<String, Number>> realSeriesData = FXCollections.observableList(
            new ArrayList<XYChart.Data<String, Number>>()) ;
    private ObservableList<XYChart.Data<String, Number>> planSeriesData = FXCollections.observableList(
            new ArrayList<XYChart.Data<String, Number>>());
    private ListChangeListener<XYChart.Data<String, Number>> readPagesDataUpdater =
            new ListChangeListener<XYChart.Data<String, Number>>() {
                @Override
                public void onChanged(Change<? extends XYChart.Data<String, Number>> c) {
                    //it's easier to reset all series data of book then iterate each change
                    data.setReadStatistics(realSeriesData);
                }
            };
    private ListChangeListener<XYChart.Data<String, Number>> planSeriesUpdater =
            new ListChangeListener<XYChart.Data<String, Number>>() {
                @Override
                public void onChanged(Change<? extends XYChart.Data<String, Number>> c) {
                    //it's easier to reset all series data of book then iterate each change
                    data.setReadStatistics(realSeriesData);
                }
            };

    private Book () {
        data = new BookData();
        this.realSeriesData.addListener(planSeriesUpdater);
        this.realSeriesData.addListener(readPagesDataUpdater);
        this.title = new SimpleStringProperty() {{ addListener((observable, oldValue, newValue) -> {
            data.setTitle(newValue);
        });}};
        this.author = new SimpleStringProperty() {{addListener((observable, oldValue, newValue) -> {
            data.setAuthor(newValue);
        });}};
        this.genre = new SimpleStringProperty() {{addListener((observable, oldValue, newValue) -> {
            data.setGenre(newValue);
        });}};
        this.pages = new SimpleIntegerProperty() {{addListener((observable, oldValue, newValue) -> {
            data.setPages(newValue.intValue());
        });}};

    }
    public Book (BookData data) {
        this();
        this.data = data;
        this.realSeriesData.setAll(data.getReadStatistics());
    }
    public Book(String title, String author, String genre, int pagesCount) {
        this();
        this.title.setValue(title);
        this.author.setValue(author);
        this.genre.setValue(genre);
        this.pages.setValue(pagesCount);
        setTodayReadPagesQuantity(0);
    }
    public void addTodayReadPagesQuantity(int pages){
        addReadPagesQuantity(new Date(), pages);
    }

    public void setTodayReadPagesQuantity(int i) {
//        Date date = new Date();
        addReadPagesQuantity(new Date(), i);
    }

//    private void setReadPagesQuantity(Date date, int pages) {
//        realSeriesData.add(date, pages);
//    }
//    public Number getTodayReadPagesQuantity() {
////        Date date = new Date();
//        return getReadPagesQuantity(new Date());
//    }
//
//    private Number getReadPagesQuantity(Date date) {
//        return realSeriesData.getPages(date);
//    }

    public void addReadPagesQuantity(Date date, int pages) {
        String dateString = dateFormat.format(date);
        boolean wasFound = false;
        for (Object item : realSeriesData) {
            if (((XYChart.Data) item).getXValue().equals(dateFormat.format(date))) {
                wasFound = true;
                Number previousNumber = ((XYChart.Data<String, Number>) item).getYValue();
                ((XYChart.Data<String, Number>) item).setYValue((int) previousNumber + pages);
                break;
            }
        }

        if (!wasFound) {
            realSeriesData.add(new XYChart.Data(dateFormat.format(date), pages));
        }
    }
    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getGenre() {
        return genre.get();
    }

    public SimpleStringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getPages() {
        return pages.get();
    }

    public IntegerProperty pagesProperty() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages.set(pages);
    }


    public ObservableList<XYChart.Data<String, Number>> getSeriesData() {
        return realSeriesData;
    }

    public BookData getData() {
        return data;
    }
}
