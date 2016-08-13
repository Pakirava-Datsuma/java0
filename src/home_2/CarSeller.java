package home_2;

import java.util.Scanner;

/**
 * Created by swanta on 08.06.16.
 */
public class CarSeller {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How much money do you have?");
        int sum = scanner.nextInt();
        String item = "";
        boolean isItDeal = true;

        switch (sum) {
            case 1000:
                item = "the wheel";
                break;
            case 10000:
                item = "the engine";
                break;
            case 33000:
                item = "blue ZAZ";
                break;
            case 33003:
                item = "white ZAZ";
                break;
            case 100000:
                item = "golden ZAZ";
                break;
            default:
                int tavriaCost = 65000;
                isItDeal = sum >= tavriaCost;
                if (!isItDeal) break;
                item = "lucky Tavria";
                sum = 65000;
                break;
        }
        if (isItDeal)
            System.out.println("You have bought " + item + " for " + sum + " UAH. Enjoy it!");
        else
            System.out.println("Need more gold!!!");
    }
}
