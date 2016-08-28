package practice_8;

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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by swanta on 17.07.16.
 */
public class Book implements Serializable {
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
    private final static String NEXT_PLANNING_DATE_STRING = "future";

    transient
    private final SimpleStringProperty title = new SimpleStringProperty() {{ addListener((observable, oldValue, newValue) -> {
        data.setTitle(newValue);});}};;

    transient
    private final SimpleStringProperty author = new SimpleStringProperty() {{addListener((observable, oldValue, newValue) -> {
        data.setAuthor(newValue);});}};;

    transient
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

    transient
    private final SimpleStringProperty genre = new SimpleStringProperty() {{addListener((observable, oldValue, newValue) -> {
        data.setGenre(newValue);});}};

    transient
    private final IntegerProperty pages = new SimpleIntegerProperty() {{addListener((observable, oldValue, newValue) -> {
        data.setPages(newValue.intValue());});}};;

    transient
    public final IntegerProperty todayPagesToAdd = new SimpleIntegerProperty() {{addListener((observable, oldValue, newValue) -> {
        data.setPages(newValue.intValue());});}};
    transient
    private ObservableList<XYChart.Data<String, Number>> realSeriesData = FXCollections.observableList(
            new ArrayList<XYChart.Data<String, Number>>());

    transient
    private ObservableList<XYChart.Data<String, Number>> planSeriesData = FXCollections.observableList(
            new ArrayList<XYChart.Data<String, Number>>());
    transient
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
    private int readPages;

    {
        realSeriesData.addListener(
                new ListChangeListener<XYChart.Data<String, Number>>() {
                    @Override
                    public void onChanged(Change<? extends XYChart.Data<String, Number>> c) {
                        if (realSeriesData.size() > 0)
                            setReadPages(
                                    realSeriesData.stream()
                                            .mapToInt(data -> data.getYValue().intValue())
                                            .max()
                                            .getAsInt());
                                    else
                                        setReadPages(0);
                    }
                });
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public Book(BookData data) {
//        this(data.getTitle(), data.getAuthor(), data.getGenre(), data.getPages());
        this.data = data;
        setPropertiesFromData();
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
        List<BookData> result = new ArrayList<BookData>();
        for (Book book : books) {
            result.add(book.getData());
        }
        return result;
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

    // this method shouldn't use any BookData as input
    private void setPropertiesFromData() {
        setTitle(data.getTitle());
        setAuthor(data.getAuthor());
        setGenre(data.getGenre());
        setPages(data.getPages());
    }
    public final void writeObject(ObjectOutputStream oos) throws IOException{
        oos.writeObject(this.data);
    }

    public final void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        data = (BookData)ois.readObject();
        setPropertiesFromData();
    }

    public int getReadPages() {
        return readPages;
    }

    public void setReadPages(int readPages) {
        this.readPages = readPages;
    }

    public String getTitleAndAuthor() {
        return titleAndAuthor.getValue();
    }

    public ObservableList<XYChart.Data<String, Number>> getPlanData() {
        return planSeriesData;
    }
}
