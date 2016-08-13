package practice_3;

import java.util.HashSet;

/**
 * Created by swanta on 23.06.16.
 */
public class HumanLimitedSet extends HashSet<Human> {
    protected int patientsMaxCount = 0;

    @Override
    public boolean add(Human human) {
        boolean result = false;
        if (!(size() > patientsMaxCount)
                && super.add(human)) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean remove(Object human) {
        boolean result = false;
        if (super.remove(human)) {
            result = true;
        }
        return result;
    }

    public boolean isFull() {
        boolean result = !(patientsMaxCount < size());
        return result;
    }

}
