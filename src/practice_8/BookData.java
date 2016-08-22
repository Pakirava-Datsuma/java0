package practice_8;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by swanta on 19.08.16.
 */
public class BookData implements Serializable {
    private String title;
    private String author;
    private String genre;
    private int pages;

    private ArrayList<Data> readStatistics = new ArrayList<>();
//    private ArrayList<Data> planSeries = new ArrayList<>();
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd");


    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public BookData() {
    }

    public BookData(String title, String author, String genre, int pages) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pages = pages;
    }

    public Number getPages(Date date) {
        for (Data data : readStatistics)
            if (data.date.equals(dateFormat.format(date)))
                return data.pages;
        return 0;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getPages() {
        return pages;
    }

    public Collection<XYChart.Data<String, Number>> getReadStatistics() {
        List<XYChart.Data<String, Number>> result = new LinkedList<>();
        for (Data data : readStatistics) {
            result.add(new XYChart.Data<>(data.date, data.pages));
        }
        return result;
    }

    public void setReadStatistics(ObservableList<XYChart.Data<String, Number>> chartSeries) {
        for (XYChart.Data<String, Number> chartData : chartSeries) {
            this.readStatistics.add(new Data(chartData.getXValue(), chartData.getYValue()));
        }
    }
/*

    public ArrayList<Data> getPlanSeries() {
        return planSeries;
    }

    public void setPlanSeries(ObservableList<XYChart.Data<String, Number>> planSeries) {
        this.planSeries = planSeries;
    }

*/
    protected class Data implements Serializable{
        public String date;
        public Number pages;

        public Data(String date, Number pages) {
            this.date = date;
            this.pages = pages;
        }
    }
}
