package practice_3;

/**
 * Created by swanta on 05.06.16.
 */
public class Hospital {
    private Reception reception = new Reception();
    private Morgue morgue = new Morgue();

    public void come(Human human) {
        human.soutStatus("come to hospital.");
        Doctor doctor = reception.getSomeDoctor(human);
        if (doctor != null) {
            boolean isReady = false;
            while (!isReady) {
                if (doctor.isDead(human)) {
                    morgue.writeIn(human);
                    isReady = true;
                } else if (doctor.isNeedHospitalization(human)) {
                    reception.writeIn(human);
                    isReady = true;
                } else if (doctor.isNeedHeal(human)) {
                    doctor.heal(human);
                } else {
                    human.goHome(" this human is not ill");
                    isReady = true;
                }
            }
        } else {
            human.goHome(" no doctors available");
        }
    }

    public void come(Doctor doctor) {
        boolean isReady = false;
        while (!isReady) {
            if (doctor.isDead(doctor)) {
                morgue.writeIn(doctor);
                isReady = true;
            } else if (doctor.isNeedHospitalization(doctor)) {
                doctor.soutStatus("* will not work today.");
                reception.writeIn(doctor);
                isReady = true;
            } else if (doctor.isNeedHeal(doctor)) {
                doctor.heal(doctor);
            } else {
                reception.addDoctor(doctor);
                isReady = true;
            }
        }
    }

    public boolean isFullOfPatients() {
        return reception.isFull();
    }

    public void healAll() {
        while (!isEmptyOfPatients()) {
            reception.writeOut(reception.healAtLeastOne());
        }
    }

    private boolean isEmptyOfPatients() {
        return reception.getPatientsCount() == 0;
    }

    public boolean hasDoctors() {
        return reception.getDoctorsCount() > 0;
    }

    public void soutStatus() {
        System.out.println("\nsummary : " + reception.workLists.size()+ " doctors:");
        for (DoctorWorkList workList :
                reception.workLists) {
            System.out.println(workList.doctor.getNameAndHealth() + " patients:");
            for (Human patient :
                    workList.getPatients()) {
                System.out.println("   "+patient.getNameAndHealth());
            }
        }
    }
}
