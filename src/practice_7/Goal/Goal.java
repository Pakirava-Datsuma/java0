package practice_7.Goal;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by swanta on 10.07.16.
 */
public class Goal {
    String description;
    Date dueDate;
    transient int daysLeft;

    private void countDaysLeft(){
//        Instant instant = Date.from();
//        daysLeft = Date.from(dueDate);
    }
}
