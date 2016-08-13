/**
 * Created by swanta on 21.05.16.
 */
public class PowerOf2Checker {
    private static final byte POWER_BASE = 2;

    public static void main(long number) {
        while (number > 1) {
            number %= 2;
        }
        String failMessageModifier = new String("");
        if (number == 1) failMessageModifier = "НЕ ";
        String message = String.format("Введённое число %sявляется степенью %d", failMessageModifier, POWER_BASE);
        System.out.println( message );
    }
}
