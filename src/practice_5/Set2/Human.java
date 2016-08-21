package practice_5.Set2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by swanta on 21.08.16.
 */
public class Human {
    final String name;
    final HumanType type;
    final CarModel car;
    final static Random random = new Random();

    private Human(String name){
        this.name = name;
        this.type = HumanType.values()[random.nextInt(HumanType.values().length)];
        this.car = CarModel.values()[random.nextInt(CarModel.values().length)];
    }

    public Human(String name, CarModel car) {
        this.name = name;
        this.type = HumanType.NORMAL;
        this.car = car;
    }

    public Human(String name, HumanType type, CarModel car) {
        this.name = name;
        this.type = type;
        this.car = car;
    }

    public static void print(Collection<Human> humans) {
        for (Human human : humans) {
            System.out.println(String.format
                    ("%s (%s) катается на %s"
                    , human.name, human.type.toString(), human.car.toString()));
        }
    }

    enum HumanType {
        NORMAL, DEPUTE;
    }

    enum CarModel {
        AUDI, MERCEDES, BMW, CHRYSLER, LOTUS, FERRARI;
    }

    public static Set<Human> getSomeHumans(String[] names) {
        Set<Human> humans = new HashSet<>();
        for (String name : names) {
            humans.add(new Human(name));
        }
        return humans;
    }

    public boolean isDepute () {
        return (type == HumanType.DEPUTE);
    }

}
