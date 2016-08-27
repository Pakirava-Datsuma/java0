package practice_3;

import java.util.Random;

import static java.lang.Math.pow;

/**
 * Created by swanta on 05.06.16.
 */
public class Doctor extends Human implements Healable{
    public static final int LEVEL_MAX = 5;
    public static final int LEVEL_MIN = 1; // must be greater than zero as it used for HumanLimitedSet.limit
    public static final int OPERATING_SELF_DAMAGE = 20;
    private static final int HEALING_LEVEL_MIN = -10;
    private static final int HEALING_LEVEL_MAX = 25;
    public final int level;
//    public Doctor(String firstName, String lastName, char gender, Date dateOfBirth, int health, int level) {
    static double CHANCE_TO_BE_HEALED = 0.75;

    public Doctor () {
        super();
        Random random = new Random();
        health = random.nextInt(40) + 60; // usually doctor is more healthy than human
        this.level = random.nextInt(LEVEL_MAX - LEVEL_MIN) + LEVEL_MIN;
        System.out.println("new doctor: " + this.toString());
    }

    public void heal (Healable patient) {
        Random random = new Random();
        double patientFactor = patient.getChanceToBeHealed();
        double randomFactor = random.nextFloat();
        // the lower level the lower coefficient
        double levelFactor = (LEVEL_MAX - level)/10.0 + 1.0; // +1 to avoid zero
        int hpChange = (int) (
                (HEALING_LEVEL_MAX - HEALING_LEVEL_MIN)
                        * pow(randomFactor, levelFactor)
                        * patientFactor
                        + HEALING_LEVEL_MIN);
        ((Human)patient).changeHealthBy(hpChange);
        soutStatus(String.format("* has added %d points to %s.", hpChange, ((Human)patient).getNameAndHealth()));
//        human.soutStatus("have heal " + human.getNameAndHealth() +".");
        trySelfDamage();
//        if (!isFilingGood()) soutStatus("feels BAD and CAN'T work more");
    }

    public void heal () {
        Random random = new Random();
        double randomFactor = random.nextFloat();
        // the lower level the lower coefficient
        double levelFactor = (LEVEL_MAX - level)/10.0 + 1.0; // +1 to avoid zero
        int hpChange = (int) (
                (HEALING_LEVEL_MAX - HEALING_LEVEL_MIN)
                        * pow(randomFactor, levelFactor)
                        + HEALING_LEVEL_MIN);
        this.changeHealthBy(hpChange);
        soutStatus(String.format("* added %d points to self.", hpChange));
    }
    private boolean trySelfDamage() {
        Random random = new Random();
        boolean damaged = (random.nextInt(level+1) == 0); // doctors 0 level should damage himself only 1/2 times
        if (damaged) {
            changeHealthBy(-OPERATING_SELF_DAMAGE);
            soutStatus(String.format(" damaged on %d points.", OPERATING_SELF_DAMAGE));
        }
//        else soutStatus("have done operation successful.");
        return damaged;
    }

    public boolean isNeedHospitalization (Human human) {
        //        if (result) soutDiagnose(human, "needs hospitalization");
//        else soutDiagnose(human, "doesn't need hospitalization");
        return (human.getHealth() < HEALTH_NEEDS_HEAL);
    }

    private void soutDiagnose(Healable human, String diagnose) {
//        System.out.print(getNameAndHealth()+" diagnose: ");
//        human.soutStatus(diagnose);
    }

    public boolean isNeedHeal (Healable human) {
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

    public boolean isDead(Healable human) {
        boolean result = human.getHealth() < 1;
        return result;
    }

    public boolean isFilingGood() {
        while (isNeedHeal(this) && !isNeedHospitalization(this)) {
                this.heal();
        }
        return !isNeedHospitalization(this);
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

    @Override
    public double getChanceToBeHealed() {
        return CHANCE_TO_BE_HEALED;
    }
}
