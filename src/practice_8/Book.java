package practice_8;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by swanta on 17.07.16.
 */
public class Book{
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
    private final static String NEXT_PLANNING_DATE_STRING = "future";

    private final SimpleStringProperty title = new SimpleStringProperty() {{ addListener((observable, oldValue, newValue) -> {
        data.setTitle(newValue);
    });}};;

    private final SimpleStringProperty author = new SimpleStringProperty() {{addListener((observable, oldValue, newValue) -> {
        data.setAuthor(newValue);
    });}};;
    private final SimpleStringProperty genre = new SimpleStringProperty() {{addListener((observable, oldValue, newValue) -> {
        data.setGenre(newValue);
    });}};;
    private final IntegerProperty pages = new SimpleIntegerProperty() {{addListener((observable, oldValue, newValue) -> {
        data.setPages(newValue.intValue());
    });}};;
    public final IntegerProperty todayPagesToAdd = new SimpleIntegerProperty() {{addListener((observable, oldValue, newValue) -> {
        data.setPages(newValue.intValue());
    });}};

    private ObservableList<XYChart.Data<String, Number>> realSeriesData = FXCollections.observableList(
            new ArrayList<XYChart.Data<String, Number>>());
    private ObservableList<XYChart.Data<String, Number>> planSeriesData = FXCollections.observableList(
            new ArrayList<XYChart.Data<String, Number>>());

    private BookData data = new BookData() {{
        //link realSeriesData to planSeriesData & data
        realSeriesData.addListener(new ListChangeListener<XYChart.Data<String, Number>>() {
            @Override
            public void onChanged(Change<? extends XYChart.Data<String, Number>> c) {
                //it's easier to reset all series data of book then iterate each change
                data.setReadStatistics(realSeriesData);
            }
        });
        realSeriesData.addListener(new ListChangeListener<XYChart.Data<String, Number>>() {
            @Override
            public void onChanged(Change<? extends XYChart.Data<String, Number>> c) {
                planSeriesData.setAll(realSeriesData.sorted(dataComparatorOnlyDate ));
                //  calculate 2..N-1 points
                int numberOfPointsToCalculate = planSeriesData.size()-1;
                //  1    is first base point
                int startPagesNumber = planSeriesData.get(0).getYValue().intValue();
                //  N    is last base point
                int lastPagesNumber = planSeriesData.get(numberOfPointsToCalculate).getYValue().intValue();
                int pagesPerDay = getPages() / numberOfPointsToCalculate;
                XYChart.Data<String, Number> planData;
                for (int i = 1; i < numberOfPointsToCalculate; i++) {
                    planData = planSeriesData.get(i);
                    planData.setYValue(pagesPerDay * i);
                }
                //  calculate N+1 point
                planData = new XYChart.Data<String, Number>(
                        NEXT_PLANNING_DATE_STRING,
                        pagesPerDay * (numberOfPointsToCalculate + 1));
                planSeriesData.add(planData);

            }
        });
    }};

    @Override
    public String toString() {
        return data.toString();
    }

    public Book(BookData data) {
        this(data.getTitle(), data.getAuthor(), data.getGenre(), data.getPages());
        this.data = data;
//        linkSeriesData();
    }
    public Book(String title, String author, String genre, int pagesCount) {
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
        XYChart.Data<String, Number> modifyingData;
        try {
            modifyingData = realSeriesData.stream()
                .filter(data ->
                        data.getXValue().equals(dateFormat.format(date)))
                .findFirst()
                .get();
            pages += modifyingData.getYValue().intValue();
            modifyingData.setYValue(pages);
        }catch (NoSuchElementException e){
            modifyingData = new XYChart.Data<>(dateFormat.format(date), pages);
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

    public BookData getData() {
        return data;
    }

    public static List<Book> getBooks(List<BookData> bookData) {
        return bookData.stream()
                .map(Book::new)
                .collect(Collectors.toList());
    }
    public static List<BookData> getBooksData(List<Book> books) {
        return books.stream().map(Book::getData).collect(Collectors.toList());
    }

//    public void setData(BookData data) {
//        this.author = data.author;
//        this.
//        this.realSeriesData.setAll(data.getReadStatistics());
//        this.data = data;
//    }

    public static void setRandomStatistics(Book book) {
        final float READ_PERCENTAGE = 50;
        book.realSeriesData.setAll(
                book.getData().getRandomStatistics(READ_PERCENTAGE));
    }
}
