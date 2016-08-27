package practice_3;

import java.util.Date;
import java.util.Random;

/**
 * Created by swanta on 05.06.16.
 */
public class StartApplication {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        Random random = new Random();

//        while (!hospital.hasDoctors()) {
//            hospital.come(new Doctor());
//        }
//        hospital.come(new Doctor());
//        hospital.come(new Doctor());
//        hospital.come(new Doctor());
//        hospital.soutStatus();
//        hospital.come(new Human("Lara", "Croft", 'f', new Date(1234567), 53));
//
        for (int i = 0; i < 100 || !hospital.isFullOfPatients(); i++){
            final int CHANCE_FOR_DOCTOR_TO_COME = 17; // aprox. every (int) human is doctor
            if (random.nextInt(CHANCE_FOR_DOCTOR_TO_COME) == 0) {
                hospital.come(new Doctor());
            }
            else {
                hospital.come(Patient.getPatient());
            }
//            System.out.print(i + ": ------------");
        }
        hospital.soutStatus();
        hospital.healAll();
        hospital.soutStatus();

    }
}
