package practice_6.EnumWithExceptions;

/**
 * Created by swanta on 03.07.16.
 */
public class AngryBirdsApp {
    public static void main(String[] args) {
        for (Bird bird :
                Bird.values()) {
            try {
                System.out.println(bird.name()+" launch...");
                bird.fly();
            } catch (BirdsFlyException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("next birdy...");
            }
        }
    }
}
