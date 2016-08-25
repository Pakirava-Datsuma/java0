package practice_3;

import java.util.*;

/**
 * Created by swanta on 05.06.16.
 */
public class Human {
    public static final char MALE = 'm';
    public static final char FEMALE = 'f';
    public String firstName;
    public String lastName;
    public int age;
    public char gender;
    protected int health;
    private static final String[] MALE_FIRST_NAMES = {"Bobby", "Alan", "Criss", "John", "Mike"};
    private static final String[] FEMALE_FIRST_NAMES = {"Lara", "Sarah", "Susane", "Nelly", "Martha"};
    private static final String[] LAST_NAMES = {"Wake", "Wood", "Depp", "Knob", "Smith", "Adams", "Spears"};
    private static Set<String> usedNames = new HashSet<>();
    private static final int ADULT_AGE_MIN = 18;
    public static final int HEALTH_MAX = 100;
    public static final int HEALTH_NEEDS_HEAL = 75;

    public Human() {
        Random random = new Random();
        boolean isFemale = random.nextBoolean();
        boolean nameGenerated;
        String nameSuffix = "";
        do { // avoid generating duplicate names
                gender = ( isFemale ? FEMALE : MALE);

            firstName = isFemale
                    ? FEMALE_FIRST_NAMES[random.nextInt( FEMALE_FIRST_NAMES.length)]
                    : MALE_FIRST_NAMES[random.nextInt( MALE_FIRST_NAMES.length)];
            lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)].concat(nameSuffix);
            nameGenerated = usedNames.add(getName());
                char[] suffixChar = {(char)random.nextInt(Character.MAX_VALUE)};
                nameSuffix = new String(suffixChar);
        } while (!nameGenerated);
        age = random.nextInt(50) + ADULT_AGE_MIN;
        health = random.nextInt(HEALTH_MAX);
    }

    public Human(String firstName, String lastName, char gender, Date dateOfBirth, int health) {
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = dateOfBirth.compareTo(Calendar.getInstance().getTime());
        this.health = health;
        usedNames.add(getName());
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getNameAndHealth() {
        return (getName() + " ("+health+")");
    }

    public void changeHealthBy(int i) {
        health = Math.min(HEALTH_MAX, Math.max(0, health + i));
        soutStatus("* health changed to " + getHealth());
    }

    public int getHealth() {
        return health;
    }

    public void soutStatus(String status) {
        String newstatus = status.replace("*", getName());
        if (newstatus.equals(status)) {
            newstatus = getNameAndHealth().concat(status);
        }
        System.out.println(newstatus);
    }

    @Override
    public String toString() {
        return String.format(
                "%s.%s %s, %d years old, %dhp, _%c_.",
                gender == MALE ? "mr." : "mrs",
                firstName, lastName, age,
                health, gender);
    }

    public void goHome() {
        goHome("");
    }

    public void goHome(String why) {
        why = "had gone home" + (why.isEmpty() ? "." : (" because " + why));
        soutStatus(why);
        usedNames.remove(getName());
    }
}
