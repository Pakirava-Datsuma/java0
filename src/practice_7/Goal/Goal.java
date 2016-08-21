package practice_7.Goal;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by swanta on 10.07.16.
 */
public class Goal implements Serializable{
    String description;
    Date dueDate;
    transient long daysLeft;
    public static final String DATE_FORMAT = "DD.MM.YYYY";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public void countDaysLeft(){
        long time = (new Date()).getTime();
        long dueTime = dueDate.getTime();
        daysLeft = ((dueTime - time)
                    /(24*1000*60*60));
    }

    public Goal(String description, String dueDate) throws ParseException{
        this.description = description;
        this.dueDate = dateFormat.parse(dueDate);
        countDaysLeft();
    }

    @Override
    public String toString() {
        return String.format(
                        "цель > %s\n" +
                        "осталось дней > %d"
                , description
                , daysLeft
        );
    }
}
