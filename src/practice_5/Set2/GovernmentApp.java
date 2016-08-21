package practice_5.Set2;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by swanta on 21.08.16.
 */
public class GovernmentApp {
    public static void main(String[] args) {
        //let's create Set1 with random people
        //Set1 is required by task
        Set<Human> allHumans = Human.getSomeHumans(new String[]{
                "Василий", "Ирина", "Максим", "Радион", "Изабелла", "Владимир", "Наталья", "Михаил",
                "Дмитрий", "Надежда", "Станислав", "Галина", "Алла", "Виктор", "Марина", "Николай"
        });
        System.out.println("\nсозданы личности:");
        Human.print(allHumans);

        //filter normal people from Set1 to Set2
        //Set2 is required by task
        Set<Human> normalHumans = new HashSet<>();
        for (Human human : allHumans) {
            if (!human.isDepute()){
                normalHumans.add(human);
            }
        }

        //evaluate Set3 with deputes via substraction of Set2 from Set1
        //intersection is required by task
        Set<Human> deputesOnly = new HashSet<>();
        deputesOnly.addAll(allHumans);
        deputesOnly.removeAll(normalHumans);
        System.out.println("\nдепутаты:");
        Human.print(deputesOnly);
    }
}
