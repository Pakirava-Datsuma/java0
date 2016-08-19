package practice_8;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.chart.XYChart;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by swanta on 17.07.16.
 */
public class Book {
    private final SimpleStringProperty title  = new SimpleStringProperty();
    private final SimpleStringProperty author = new SimpleStringProperty();
    private final SimpleStringProperty genre = new SimpleStringProperty();
    private final IntegerProperty pages = new SimpleIntegerProperty();
    private XYChart.Series<String, Number> realSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> planSeries = new XYChart.Series<>();
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd");

    public Book(String title, String author, String genre, int pagesCount) {
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
        setReadPagesQuantity(new Date(), i);
    }

    private void setReadPagesQuantity(Date date, int pages) {
        String dateString = dateFormat.format(date);
        realSeries.getData().add(new XYChart.Data(dateString, pages));
    }
    public int getTodayReadPagesQuantity() {
//        Date date = new Date();
        return getReadPagesQuantity(new Date());
    }

    private int getReadPagesQuantity(Date date) {
        for (Object item : realSeries.getData())
            if (((XYChart.Data) item).getXValue().equals(dateFormat.format(date)))
                return ((XYChart.Data<String, Number>) item).getYValue().intValue();
        return 0;
    }

    public void addReadPagesQuantity(Date date, int pages) {
        String dateString = dateFormat.format(date);
        boolean wasFound = false;
        for (Object item : realSeries.getData()) {
            if (((XYChart.Data) item).getXValue().equals(dateFormat.format(date))) {
                wasFound = true;
                Number previousNumber = ((XYChart.Data<String, Number>) item).getYValue();
                ((XYChart.Data<String, Number>) item).setYValue((int) previousNumber + pages);
                break;
            }
        }

        if (!wasFound) {
            realSeries.getData().add(new XYChart.Data(dateFormat.format(date), pages));
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


    public XYChart.Series<String, Number> getSeries() {
        return realSeries;
    }

}
