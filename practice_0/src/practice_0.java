package pra

/**
 * Created by swanta on 18.05.16.
 */
public class practice_0 {
    public static void main(String[] args) {
        int value1, value2, value3;

        value1 = Integer.parseInt(args[0]);
        value2 = Integer.parseInt(args[1]);
        value3 = Integer.parseInt(args[2]);
        int max = getMax(value1, value2, value3);
        int min = getMin(value1, value2, value3);
    }

    public static int getMax(int a, int b, int c) {
        int result = 0;
        if ( a >= b && a >= c ){
            result = a;
        } else if ( b >= a && b >= c ){
            result = b;
        } else {
            result = c;
        }
        return result;
    }

    public static int getMin(int a, int b, int c) {
        int result = 0;
        if ( a <= b && a <= c ){
            result = a;
        } else if ( b <= a && b <= c ){
            result = b;
        } else {
            result = c;
        }
        return result;
    }

}
