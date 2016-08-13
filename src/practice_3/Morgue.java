package practice_3;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by swanta on 12.06.16.
 */
public class Morgue {
    private Set<Human> bodies = new HashSet<>();
    private int bodiesCount = 0;
    private final int BODIES_COUNT_MAX = 10;

    public void writeIn (Human human) {
        if (bodiesCount < BODIES_COUNT_MAX ) {
            if (bodies.add(human)) {
                bodiesCount++;
                human.soutStatus("lays in morgue cabin #" + bodiesCount);
            }
        } else {
            human.soutStatus("will be digged in under the ground " +
                    "because the morgue hasen't enough space. " +
                    "It's a very rare case for this program. " +
                    "May be the programmer is coursed.");
            human.soutStatus("R.I.P");
            human = null;
        }

    }
}
