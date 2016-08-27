package practice_3;

/**
 * Created by swanta on 27.08.16.
 */
public class Pessimist extends Patient {
    static double CHANCE_TO_BE_HEALED = 0.75;

    @Override
    public String getName() {
        return "[-]" + super.getName();
    }
}
