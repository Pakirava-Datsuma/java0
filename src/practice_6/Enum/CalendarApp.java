package practice_6.Enum;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by swanta on 06.07.16.
 */
public class CalendarApp {
    public static void main(String[] args) {
        System.out.println(Month.FEBRUARY.getDaysCountNextYear());
        System.out.println(Month.detectSeason(Month.FEBRUARY));
        System.out.println(Month.getDaysInPeriod(Month.JULY,Month.JUNE));
    }
}
