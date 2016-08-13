package practice_3;

import java.util.Date;

/**
 * Created by swanta on 05.06.16.
 */
public class StartApplication {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();

        while (!hospital.hasDoctors()) {
            hospital.come(new Doctor());
        }
        hospital.come(new Doctor());
        hospital.come(new Doctor());
        hospital.come(new Doctor());
        hospital.soutStatus();
        hospital.come(new Human("Lara", "Croft", 'f', new Date(), 53));

        while (hospital.isFullOfPatients()) {
            hospital.come(new Human());
        }
        hospital.soutStatus();
        hospital.healAll();
        hospital.soutStatus();

    }
}
