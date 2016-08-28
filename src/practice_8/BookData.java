package practice_8;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by swanta on 19.08.16.
 */
public class BookData implements Serializable {
    public static final Comparator<XYChart.Data<String, Number>>  dataComparatorOnlyDate =
            (d1, d2) -> d1.getXValue().compareTo(d2.getXValue());
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
                ", readStatistics=" + readStatistics.stream().map(Statistic::toString).collect(Collectors.joining(" | ")) +
                '}';
    }

    private int pages;

    private ArrayList<Statistic> readStatistics = new ArrayList<>();
//    private ArrayList<Statistic> planSeries = new ArrayList<>();
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");


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
        return readStatistics.stream()
                .filter(statistic -> statistic.date.equals(dateFormat.format(date)))
                .mapToInt(statistic -> statistic.pages.intValue())
                .sum();
//        for (Statistic statistic : readStatistics)
//            if (statistic.date.equals(dateFormat.format(date)))
//                return statistic.pages;
//        return 0;
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

    public List<XYChart.Data<String, Number>> getReadStatistics() {
        return readStatistics.stream()
                .map(statistic ->
                        new XYChart.Data<String, Number>(statistic.date, statistic.pages))
                .collect(Collectors.toList());
//        List<XYChart.Data<String, Number>> result = new LinkedList<>();
//        for (Statistic statistic : readStatistics) {
//            result.add(new XYChart.Data<>(statistic.date, statistic.pages));
//        }
//        return result;
    }

    public void setReadStatistics(ObservableList<XYChart.Data<String, Number>> chartSeries) {
        this.readStatistics = chartSeries.stream()
                .map(bookData ->
                        new Statistic(bookData.getXValue(), bookData.getYValue()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
/*

    public ArrayList<Statistic> getPlanSeries() {
        return planSeries;
    }

    public void setPlanSeries(ObservableList<XYChart.Statistic<String, Number>> planSeries) {
        this.planSeries = planSeries;
    }

*/
    protected class Statistic implements Serializable{
        public String date;
        public Number pages;

        public Statistic(String date, Number pages) {
            this.date = date;
            this.pages = pages;
        }

    @Override
    public String toString() {
        return String.format("(%s=%s)", date, pages.toString());
    }
}

    public List<XYChart.Data<String, Number>> getRandomStatistics(float readPercentage) {
        return BookData.getRandomStatistics(this.pages, readPercentage);
    }


    public static List<XYChart.Data<String, Number>> getRandomStatistics(int pages, float readPercentage) {
//        if (readPercentage > 100) throw new ArithmeticException("can't generate pages larger than 100%");
        Random random = new Random();
//        final int MIN_PAGES_READING = 1;
        final int MAX_PAGES_READING = 160;
        int pagesToRead = (int) (readPercentage / 100.0 * pages);// - MIN_PAGES_READING;
        long time = new Date().getTime();
        final long ONE_DAY_TIME = 24*3600*1000;
        final long MIN_DATE_INTERVAL = 1*ONE_DAY_TIME;
        final long RANDOM_DATE_INTERVAL = 3 * ONE_DAY_TIME; //interval = rnd(RANDOM_DATE_INTERVAL) + MIN_DATE_INTERVAL
        List<XYChart.Data<String, Number>> randomDatas = new ArrayList<>();
        int pagesLeft = pagesToRead;
        while (pagesLeft >= 0) {
            String newDate = dateFormat.format(new Date(time));
            randomDatas.add(new XYChart.Data<>(newDate, pagesLeft));
            pagesLeft -= random.nextInt(MAX_PAGES_READING);// + MIN_PAGES_READING;
            time -= random.nextFloat() * RANDOM_DATE_INTERVAL + MIN_DATE_INTERVAL;
        }
        randomDatas.sort(dataComparatorOnlyDate);
        return randomDatas;
    }
}
