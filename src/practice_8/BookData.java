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

    @Override
    public String toString() {
        return "BookData{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", pages=" + pages +
                ", readStatistics=" + readStatistics +
                '}';
    }

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

    public Collection<XYChart.Data<String, Number>> getRandomStatistics(float readPercentage) {
        return BookData.getRandomStatistics(this.pages, readPercentage);
    }


    public static Collection<XYChart.Data<String, Number>> getRandomStatistics(int pages, float readPercentage) {
//        if (readPercentage > 100) throw new ArithmeticException("can't generate pages larger than 100%");
        Random random = new Random();
        final int MIN_PAGES_READING = 1;
        int pagesLeft = (int) (readPercentage / 100 * pages) - MIN_PAGES_READING;
        long time = new Date().getTime();
        final long ONE_DAY_TIME = 24*3600*1000;
        final long MIN_DATE_INTERVAL = ONE_DAY_TIME;
        final long RANDOM_DATE_INTERVAL = 3 * ONE_DAY_TIME; //interval = rnd(RANDOM_DATE_INTERVAL) + MIN_DATE_INTERVAL
        Collection<XYChart.Data<String, Number>> randomDatas = new HashSet<>();
        while (pagesLeft > 0) {
            String newDate = dateFormat.format(new Date(time));
            randomDatas.add(new XYChart.Data(newDate, pagesLeft));
            pagesLeft -= random.nextInt(pagesLeft) + MIN_PAGES_READING;
            time -= random.nextFloat() * RANDOM_DATE_INTERVAL + MIN_DATE_INTERVAL;
        }
        return randomDatas;
    }
}
