package practice_6.Enum;

/**
 * Created by swanta on 06.07.16.
 */
public enum MonthLong {
    ShortShortMonth(28),
    LongShortMonth(29),
    ShortMonth(30),
    LongMonth(31)
    ;
    int days;
    MonthLong(int days){
        this.days = days;
    }
    public int days() {
        return days;
    }
}
