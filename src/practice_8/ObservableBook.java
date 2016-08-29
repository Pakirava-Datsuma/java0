package practice_8;

import com.sun.javafx.collections.ArrayListenerHelper;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by swanta on 17.07.16.
 */
public final class ObservableBook implements Serializable {
    private final static String NEXT_PLANNING_DATE_STRING = "future";

    private final SimpleStringProperty title = new SimpleStringProperty() {{ addListener((observable, oldValue, newValue) -> {
        book.setTitle(newValue);});}};;

    private final SimpleStringProperty author = new SimpleStringProperty() {{addListener((observable, oldValue, newValue) -> {
        book.setAuthor(newValue);});}};;

    private SimpleStringProperty titleAndAuthor = new SimpleStringProperty() {
        ChangeListener<? super String> titleAndAutorListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                titleAndAuthor.setValue(
                        String.format("\"%s\" by %s"
                                ,title.getValue()
                                ,author.getValue()));
            }
        };
        {
            title.addListener(titleAndAutorListener);
            author.addListener(titleAndAutorListener);
        }
    };

    private final SimpleStringProperty genre = new SimpleStringProperty() {{addListener((observable, oldValue, newValue) -> {
        book.setGenre(newValue);});}};

    private final IntegerProperty pages = new SimpleIntegerProperty() {{addListener((observable, oldValue, newValue) -> {
        book.setPages(newValue.intValue());});}};;

//    public final IntegerProperty todayPagesToAdd = new SimpleIntegerProperty() {{addListener((observable, oldValue, newValue) -> {
//        book.setPages(newValue.intValue());});}};
    
    private ObservableList<XYChart.Data<String, Number>> realSeriesData = FXCollections.observableList(
            new ArrayList<XYChart.Data<String, Number>>());

    private ObservableList<XYChart.Data<String, Number>> planSeriesData = FXCollections.observableList(
            new ArrayList<XYChart.Data<String, Number>>());
    
    private Book book = new Book();
    private SimpleIntegerProperty readPages = new SimpleIntegerProperty();

    private final ListChangeListener<? super XYChart.Data<String, Number>> bookReadStatisticsUpdater
            = (ListChangeListener<XYChart.Data<String, Number>>) c -> {
                //it's easier to reset all series book of book then iterate each change
                book.setReadStatistics(realSeriesData);
            };

    private final ListChangeListener<? super XYChart.Data<String, Number>> planSeriesDataUpdater
            = (ListChangeListener<XYChart.Data<String, Number>>) c -> {
        //  calculate 2..N-1 points
        int numberOfPointsToCalculate = realSeriesData.size() + 1;
        //  1    is first base point
        int startPagesNumber = realSeriesData.get(0).getYValue().intValue();
        //  N    is last base point
        int lastPagesNumber = realSeriesData.get(numberOfPointsToCalculate - 1).getYValue().intValue();
        int pagesPerDay = getPages() / (numberOfPointsToCalculate - 2);
        XYChart.Data<String, Number> realData, planData;
        List<XYChart.Data<String, Number>> planSeries = new ArrayList<>();
        for (int i = 0; i < numberOfPointsToCalculate; i++) {
            realData = realSeriesData.get(i);
            planData = new XYChart.Data<>();
            planData.setYValue(pagesPerDay * i);
            planData.setXValue( i == numberOfPointsToCalculate
                    ? NEXT_PLANNING_DATE_STRING
                    : realData.getXValue());
            planSeries.add(planData);
        }
        planSeriesData.setAll(planSeries);

    };

    private final ListChangeListener<? super XYChart.Data<String, Number>> readPagesUpdater
            = (ListChangeListener<XYChart.Data<String, Number>>) c -> {
        if (realSeriesData.size() > 0)
            setReadPages(
                    realSeriesData.stream()
                            .mapToInt(data -> data.getYValue().intValue())
                            .max()
                            .getAsInt());
        else
            setReadPages(0);

    };

    {
        //link realSeriesData to planSeriesData & book
        realSeriesData.addListener(bookReadStatisticsUpdater);
        realSeriesData.addListener(planSeriesDataUpdater);
        realSeriesData.addListener(readPagesUpdater);
    }

    @Override
    public String toString() {
        return book.toString();
    }

    public ObservableBook(Book book) {
        this.book = book;
        setPropertiesFromData();
    }
    public ObservableBook(String title, String author, String genre, int pagesCount) {
//        linkSeriesData();
        this.title.setValue(title);
        this.author.setValue(author);
        this.genre.setValue(genre);
        this.pages.setValue(pagesCount);
//        setTodayReadPagesQuantity(0);
    }
    public void addTodayReadPagesQuantity(int pages){
        addReadPagesQuantity(new Date(), pages);
    }

    public void setTodayReadPagesQuantity(int i) {
//        Date date = new Date();
        addReadPagesQuantity(new Date(), i);
    }

    public void addReadPagesQuantity(Date date, int pages) {
        String dateString = Book.dateFormat.format(date);
        boolean wasFound = false;
        XYChart.Data<String, Number> modifyingData;
        try {
            modifyingData = realSeriesData.stream()
                .filter(data ->
                        data.getXValue().equals(Book.dateFormat.format(date)))
                .findFirst()
                .get();
            pages += modifyingData.getYValue().intValue();
            modifyingData.setYValue(pages);
        }catch (NoSuchElementException e){
            modifyingData = new XYChart.Data<>(Book.dateFormat.format(date), pages);
            realSeriesData.add(modifyingData);
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

    public Book getBook() {
        return book;
    }

    public static List<ObservableBook> getObservableBooks(List<Book> book) {
        return book.stream()
                .map(ObservableBook::new)
                .collect(Collectors.toList());
    }
    public static List<Book> getBooks(List<ObservableBook> observableBooks) {
        List<Book> result = new ArrayList<>();
        for (ObservableBook observableBook : observableBooks) {
            result.add(observableBook.getBook());
        }
        return result;
    }

//    public void setData(Book book) {
//        this.author = book.author;
//        this.
//        this.realSeriesData.setAll(book.getReadStatistics());
//        this.book = book;
//    }

    public static void setRandomStatistics(ObservableBook observableBook) {
        final float READ_PERCENTAGE = 50;
        observableBook.realSeriesData.setAll(
                observableBook.getBook().getRandomStatistics(READ_PERCENTAGE));
    }

    // this method shouldn't use any Book as input
    private void setPropertiesFromData() {
        setTitle(book.getTitle());
        setAuthor(book.getAuthor());
        setGenre(book.getGenre());
        setPages(book.getPages());
//        realSeriesData.removeListener(bookReadStatisticsUpdater);
        realSeriesData.setAll(book.getReadStatistics());
//        realSeriesData.addListener(bookReadStatisticsUpdater);
    }

    public int getReadPages() {
        return readPages.getValue();
    }

    public SimpleIntegerProperty getReadPagesProperty() {
        return readPages;
    }

    public void setReadPages(int readPages) {
        this.readPages.setValue(readPages);
    }

    public String getTitleAndAuthor() {
        return titleAndAuthor.getValue();
    }

    public ObservableList<XYChart.Data<String, Number>> getPlanData() {
        return planSeriesData;
    }

    public SimpleStringProperty getGenreProperty() {
        return genre;
    }
}
