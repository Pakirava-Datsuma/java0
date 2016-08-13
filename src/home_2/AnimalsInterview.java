package home_2;

import java.util.Scanner;

/**
 * Created by swanta on 04.06.16.
 */

public class AnimalsInterview {
    private static final int ANIMAL_COUNT_MIN = 3;
    private static String[] NAMES = {
            "Carver", "Noah", "Mike", "Bitty", "Spooky", "Slowpok" };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int animalCount = 0;

        System.out.println("How many OCELOTs would you like to create?");
        animalCount = Math.max(ANIMAL_COUNT_MIN, scanner.nextInt());
        Ocelot[] ocelots = new Ocelot[animalCount];
        for (int i = 0; i < animalCount; i++)
            ocelots[i] = new Ocelot("Ocelotty" + i);

        System.out.println("How many PANGOLINs would you like to create?");
        animalCount = Math.max(ANIMAL_COUNT_MIN, scanner.nextInt());
        Pangolin[] pangolins = new Pangolin[animalCount];
        for (int i = 0; i < animalCount; i++)
            pangolins[i] = new Pangolin("Pangollini" + i);

        System.out.println("How many TAHRs would you like to create?");
        animalCount = Math.max(ANIMAL_COUNT_MIN,scanner.nextInt());
        Tahr[] tahrs = new Tahr[animalCount];
        for (int i = 0; i < animalCount; i++)
            tahrs[i] = new Tahr("Tahrious"+i);

        ocelots[0].Introduce();
        pangolins[0].Introduce();
        pangolins[1].Introduce();
        for (int i = 0; i < tahrs.length; i++)
            tahrs[i].Introduce();
        for (int i = 2; i < pangolins.length; i++)
            pangolins[i].Introduce();
        for (int i = 1; i < tahrs.length; i++)
            tahrs[i].Introduce();
    }
}
