package home_2;

/**
 * Created by swanta on 08.06.16.

public class AnimalCreator {
    private static String[] NAMES = {
            "Carver", "Noah", "Mike", "Bitty", "Spooky", "Slowpok" };

    public static Animal[] Create(Class Tclass, int animalCount){
        Animal[] animals = new Tclass[animalCount];
        for (int i = 0; i < animalCount; i++) {
            animals[i] = new Tclass(NAMES[i]);
        }

    }
}
*/