package home_3_expressions;

import java.util.Scanner;

/**
 * Created by swanta on 11.06.16.
 */
public class NumberParser {
    public static void main(String[] args) {
        int number = 0;
        while ((number < 1000) || (number > 9999)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter 4-digit number:");
            number = scanner.nextInt();
        }
        StringBuilder a = new StringBuilder();
        StringBuilder b = new StringBuilder();
        a.append(number);
        b.append(number);
        a.delete(0,2);
        b.delete(2,4);
        System.out.println(number+"="+a +","+ b);
        int a2 = new Integer(a.toString());
        int b2 = new Integer(b.toString());
        System.out.println(a2 +"+"+ b2+"="+(a2+b2));

    }
}
