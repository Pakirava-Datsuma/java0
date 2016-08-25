package practice_3;

import java.util.Date;

/**
 * Created by swanta on 05.06.16.
 */
public class StartApplication {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();

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
            hospital.come(new Human());
//            System.out.print(i + ": ------------");
        }
        hospital.soutStatus();
        hospital.healAll();
        hospital.soutStatus();

    }
}
