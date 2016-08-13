package home_2;

import java.time.Month;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Created by swanta on 04.06.16.
 */
public class MonthTillBirthday {
    static String[] MONTHS = {"January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int birthdayMonth = scanner.nextInt();

        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(calendar.MONTH) + 1;

        while (currentMonth  != birthdayMonth) {
            System.out.println(MONTHS[currentMonth - 1]);
            if (currentMonth >= 12) {
                currentMonth = 1;
            } else {
                currentMonth++;
            }
        }
    }
}
