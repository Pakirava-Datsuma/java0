package practice_3;

/**
 * Created by swanta on 27.08.16.
 */
public class Optimist extends Patient {
    public Optimist() {
        super();
        CHANCE_TO_BE_HEALED = 1.0;
    }

    @Override
    public String getName() {
        return "[+]" + super.getName();
    }
}
