package practice_3;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by swanta on 05.06.16.
 */
public class Human {
    public String firstName;
    public String lastName;
    public int age;
    public char gender;
    protected int health;
    private static final String[] MALE_FIRST_NAMES = {"Bobby", "Alan", "Criss", "John", "Mike"};
    private static final String[] FEMALE_FIRST_NAMES = {"Lara", "Sarah", "Susane", "Nelly", "Martha"};
    private static final String[] LAST_NAMES = {"Wake", "Wood", "Depp", "Knob", "Smith", "Adams", "Spears"};
    private static final int ADULT_AGE_MIN = 18;
    public static final int HEALTH_MAX = 100;
    public static final int HEALTH_NEEDS_HEAL = 75;

    public Human() {
        Random random = new Random();
        boolean isFemale = random.nextBoolean();
        gender = ( isFemale ? 'f' : 'm');
        firstName = ( isFemale ? FEMALE_FIRST_NAMES : MALE_FIRST_NAMES)
                [random.nextInt(( isFemale ? FEMALE_FIRST_NAMES : MALE_FIRST_NAMES).length)];
        lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        age = random.nextInt(50) + ADULT_AGE_MIN;
        health = random.nextInt(HEALTH_MAX);

    }

    public Human(String firstName, String lastName, char gender, Date dateOfBirth, int health) {
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = dateOfBirth.compareTo(Calendar.getInstance().getTime());
        this.health = health;
    }

    public String getName() {
        return (firstName + " " + lastName + "("+health+")");
    }

    public void changeHealthBy(int i) {
        health = Math.min(HEALTH_MAX, Math.max(0, health + i));
    }

    public int getHealth() {
        return health;
    }

    public void soutStatus(String status) {
        System.out.println(getName() + " "+ status);
    }

    @Override
    public String toString() {
        return String.format(
                "mr.%s %s, %d years old, %dhp, _%c_.",
                firstName, lastName, age,
                health, gender);
    }

    public void goHome() {
        soutStatus("had gone home");
    }

    public void goHome(String why) {
        soutStatus("had gone home because " + why);
    }
}
