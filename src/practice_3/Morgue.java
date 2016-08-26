package practice_3;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by swanta on 12.06.16.
 */
public class Morgue {
    private Set<Human> bodies = new HashSet<>();
    private static final int BODIES_COUNT_MAX = 10; // Integer.MAX_VALUE

    public void writeIn (Human body) {

        if (bodies.size() < BODIES_COUNT_MAX ) {
            if (bodies.add(body)) {
                body.soutStatus("* lays in morgue cabin #" + bodies.size());
            }
        } else {
            body.soutStatus("* will be digged in under the ground " +
                    "because the morgue hasen't enough space. " +
                    "It's a very rare case for this program. " +
                    "May be the programmer is coursed.");
            body.soutStatus("R.I.P");
            body = null;
        }

    }

    public int getBodiesCount() {
        return bodies.size();
    }

    public Set<Human> getBodies() {
        return bodies;
    }
}
