package practice_8;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by swanta on 17.07.16.
 */
public class Book{
    public final static SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd");
    public final ChangeListener<Number> todayReadPagesListener = (observable, oldValue, newValue) -> {
        addTodayReadPagesQuantity(newValue.intValue());
        updatePlanSeries();
    };
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final SimpleStringProperty author = new SimpleStringProperty();
    private final SimpleStringProperty genre = new SimpleStringProperty();
    private final IntegerProperty pages = new SimpleIntegerProperty();
    private final IntegerProperty todayReadPages = new SimpleIntegerProperty();

    private ObservableList<XYChart.Data<String, Number>> realSeriesData = FXCollections.observableList(
            new ArrayList<XYChart.Data<String, Number>>()) ;
    private ObservableList<XYChart.Data<String, Number>> planSeriesData = FXCollections.observableList(
            new ArrayList<XYChart.Data<String, Number>>()) ;

    private Book() {
        realSeriesData.addListener(new ListChangeListener<XYChart.Data<String, Number>>() {
            @Override
            public void onChanged(Change<? extends XYChart.Data<String, Number>> c) {
                updatePlanSeries();
            }
        });
    }

    public Book(String title, String author, String genre, int pagesCount) {
        this();
        this.title.setValue(title);
        this.author.setValue(author);
        this.genre.setValue(genre);
        this.pages.setValue(pagesCount);
//        addTodayReadPagesQuantity(0);
    }
    public Book(String title, String author, String genre, int pagesCount, Collection<XYChart.Data<String, Number>> statistics) {
        this(title, author, genre, pagesCount);
        addStatistics(statistics);
    }
    public void addTodayReadPagesQuantity(int pages){
        addReadPagesQuantity(new Date(), pages);
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
        XYChart.Data<String, Number> previousData = null;
        for (XYChart.Data data : realSeriesData) {
            if (data.getXValue().equals(dateFormat.format(date))) {
                wasFound = true;
                int previousNumber = ((XYChart.Data<String, Number>) data).getYValue().intValue();
                ((XYChart.Data<String, Number>) data).setYValue((int) previousNumber + pages);
                break;
            }
            previousData = data;
        }

        if (!wasFound) {
            int previousNumber = (previousData != null)
                    ? previousData.getYValue().intValue()
                    : 0;
            realSeriesData.add(new XYChart.Data(dateFormat.format(date), previousNumber + pages));
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


    public ObservableList<XYChart.Data<String, Number>> getRealSeriesData() {
        return realSeriesData;
    }

    public ObservableList<XYChart.Data<String,Number>> getPlanSeriesData() {
        return planSeriesData;
    }
    private void updatePlanSeries() {
        ObservableList<XYChart.Data<String,Number>> planSeries = FXCollections.observableArrayList();
//        Date date = new Date();
        int pages = this.pages.getValue().intValue();
        int totalReadPages = realSeriesData.get(realSeriesData.size()-1).getYValue().intValue();
        float readTime = 0;
        int readTimes = realSeriesData.size()-1;
        for (XYChart.Data data : realSeriesData) {
            String oldDateString = data.getXValue().toString();
//            try {
//                Date oldDate = Book.dateFormat.parse(oldDateString);
//            } catch (ParseException e) {
//                e.printStackTrace();
//                break;
//            }
            float planReadPages = totalReadPages * ( readTime / readTimes);
            planSeries.add(new XYChart.Data<String, Number>(oldDateString, planReadPages));
            readTime++;

        }
        float planReadPages = totalReadPages * ( readTime / readTimes);
        planSeries.add(new XYChart.Data<String, Number>("future",
                (pages > planReadPages ? planReadPages : pages)));

        planSeriesData.setAll(planSeries);
    }

    public void addStatistics (Collection<XYChart.Data<String, Number>> statistics) {
        try {
            for (XYChart.Data data : statistics) {
                addReadPagesQuantity(data);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void addReadPagesQuantity(XYChart.Data<String, Number> data) throws ParseException{
        Date date = dateFormat.parse(data.getXValue().toString());
        int pages = data.getYValue().intValue();
        addReadPagesQuantity(date, pages);
    }
}
