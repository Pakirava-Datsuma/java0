package practice_3;

import java.util.Random;

/**
 * Created by swanta on 05.06.16.
 */
public class Doctor extends Human {
    public static final int LEVEL_MAX = 5;
    public static final int LEVEL_MIN = 1;
    public static final int OPERATING_SELF_DAMAGE = 10;
    public final int level;

//    public Doctor(String firstName, String lastName, char gender, Date dateOfBirth, int health, int level) {
//        super(firstName, lastName, gender, dateOfBirth, health);
//        this.level = level;
//        soutStatus("created: " + this.toString());
//     }

    public Doctor () {
        super();
        Random random = new Random();
        health = random.nextInt(40) + 60; // usually doctor is more healthy than human
        this.level = random.nextInt(LEVEL_MAX - LEVEL_MIN) + LEVEL_MIN;
        soutStatus("created: " + this.toString());
    }

    public void heal (Human human) {
        Random random = new Random();
        soutStatus("* are going to heal " + human.getName() + ".");
        human.changeHealthBy(random.nextInt(35) - 10);
//        human.soutStatus("have heal " + human.getNameAndHealth() +".");
        if (human != this) trySelfDamage();
        if (!isFilingGood()) soutStatus("feels BAD and CAN'T work more");
    }

    private boolean trySelfDamage() {
        Random random = new Random();
        boolean damaged = (random.nextInt(level+1) == 0);
        if (damaged) {
            changeHealthBy(-OPERATING_SELF_DAMAGE);
//            soutStatus("has been damaged.");
        }
//        else soutStatus("have done operation successful.");
        return damaged;
    }

    public boolean isNeedHospitalization (Human human) {
        boolean result = (human.getHealth() < HEALTH_NEEDS_HEAL);
        if (result) soutDiagnose(human, "needs hospitalization");
//        else soutDiagnose(human, "doesn't need hospitalization");
        return result;
    }

    private void soutDiagnose(Human human, String diagnose) {
//        System.out.print(getNameAndHealth()+" diagnose: ");
//        human.soutStatus(diagnose);
    }

    public boolean isNeedHeal (Human human) {
        boolean result = (human.getHealth() < HEALTH_MAX);
        if (result)
            soutDiagnose(human, "needs some heal");
//        else
//            soutDiagnose(human, "is NOT ILL");
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + " DOCTOR level " + level +".";
    }

    public boolean isDead(Human human) {
        boolean result = human.getHealth() < 1;
        if (result) {
            if (this == human) {
                soutStatus("is chocked because of finding himself dead.");
            } else {
                soutDiagnose(human, " have die in age of " + human.age
                        + ". Doctor is very sorry.");
            }
        } //else soutDiagnose(human, "is alive.");
        return result;
    }

    public boolean isFilingGood() {
        if (!(isDead(this) || isNeedHospitalization(this))) return true;
        else return false;
    }

    @Override
    public String getNameAndHealth() {
        return "dr.["+level+"] " + super.getNameAndHealth();
    }

    public static Doctor getHealthyDoctor() {
        Doctor doctor = new Doctor();
        doctor.health = HEALTH_MAX;
        return doctor;
    }
}
