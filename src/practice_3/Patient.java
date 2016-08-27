package practice_3;

import java.util.Random;

/**
 * Created by swanta on 27.08.16.
 */
public abstract class Patient extends Human implements Healable {
    static double CHANCE_TO_BE_HEALED;
    @Override
    public double getChanceToBeHealed() {
        return CHANCE_TO_BE_HEALED;
    }
    public static Patient getPatient() {
        return new Random().nextBoolean()
                ? new Optimist()
                : new Pessimist();
    }

}
