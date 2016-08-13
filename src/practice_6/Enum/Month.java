package practice_6.Enum;

import java.time.Year;

import static practice_6.Enum.MonthLong.*;

/**
 * Created by swanta on 03.07.16.
 */
public enum Month {
    JANUARY(LongMonth),
    FEBRUARY(ShortShortMonth){
        public int getDaysFebruary(Year year) {
            System.out.println(this.name());
            return (year.isLeap() ? LongShortMonth.days() : ShortShortMonth.days());
        }
        @Override
        public int getDaysCount() {
            return getDaysFebruary(year);
        }
        @Override
        public int getDaysCountNextYear() {
            return getDaysFebruary(year.plusYears(1));
        }
    },
    MARCH(LongMonth),
    APRIL(ShortMonth),
    MAY(LongMonth),
    JUNE(ShortMonth),
    JULY(LongMonth),
    AUGUST(LongMonth),
    SEPTEMBER(ShortMonth),
    OCTOBER(LongMonth),
    NOVEMBER(ShortMonth),
    DECEMBER(LongMonth);

    MonthLong days;
    final Year year = Year.now();


    Month(MonthLong days) {
        this.days = days;
    }

    public int getDaysCount(){
        System.out.println(this.name());
        return days.days();
    }

    public int getDaysCountNextYear(){
        return getDaysCount();
    }

    public static int getDaysInPeriod(Month monthStart, Month monthEnd){
        int monthFirst = monthStart.ordinal();
        int monthLast = monthEnd.ordinal();
        int days = monthStart.getDaysCount();
        int month = monthFirst;
        do {
            month = ((month == Month.values().length-1) ? 0: month+1);
            if (month < monthFirst) {
                days += Month.values()[month].getDaysCountNextYear();
            }
            else {
                days += Month.values()[month].getDaysCount();
            }

        } while (month != monthLast);
        return days;

    }

    public static String detectSeason(Month month) {
        String season;
        switch (month) {
            case DECEMBER:
            case JANUARY:
            case FEBRUARY:
                season = "WINTER";
                break;
            case MARCH:
            case APRIL:
            case MAY:
                season = "";
                break;
            case JUNE:
            case JULY:
            case AUGUST:
                season = "SUMMER";
                break;
            case SEPTEMBER:
            case OCTOBER:
            case NOVEMBER:
                season = "";
                break;
            default:
                season = "You have to upgrade this software after new calendar implementation";
                break;
        }
        return season;
    }
}
