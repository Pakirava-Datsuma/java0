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
            DoctorWorkList workList = reception.getWorkListWithPatients();
            Doctor doctor = workList.getDoctor();
            Human patient = workList.getFirstPatient();
            if (healPatient(patient, doctor)) { // curing ended
                reception.writeOut(patient);
                if (doctor.isDead(patient)) { // patient is dead
                    morgue.writeIn(patient);
                }
                else { //patient is healthy
                    try { // he is a doctor
                        come((Doctor) patient);
                    }
                    catch (ClassCastException e) { // he isn't a doctor
                        patient.goHome("he(she) feel good");
                    }
                }
            }
            else { // doctor feels bad
//                doctor.soutStatus("feels BAD and CAN'T work more");
                reception.removeDoctor(doctor);
                if (doctor.isDead(doctor)) {
                    morgue.writeIn(doctor);
                }
                else {
                    reception.writeIn(doctor);
                }
            }
        }
    }


    // true if patient is health (or dead)
    // false if doctor is filing bad
    // if both (1) and (2)
    //    it's important to hospitalize doctor
    //    and let another doctor check the patient
    public boolean healPatient(Human patient, Doctor doctor) {
        while (doctor.isFilingGood() && doctor.isNeedHeal(patient) && !doctor.isDead(patient)) {
            doctor.heal(patient);
        };
        return doctor.isFilingGood();
    }

    private boolean isEmptyOfPatients() {
        return reception.getPatientsCount() == 0;
    }

    public boolean hasDoctors() {
        return reception.getDoctorsCount() > 0;
    }

    public void soutStatus() {
        System.out.println("\n---------------------\nsummary : ");
        System.out.println(reception.workLists.size()+ " doctors:");
        for (DoctorWorkList workList :
                reception.workLists) {
            workList.doctor.soutStatus(" patients:");
            for (Human patient :
                    workList.getPatients()) {
                patient.soutStatus("");
            }
        }
        System.out.println(morgue.getBodiesCount() + " bodies in MORGUE:");
        for (Human body :
                morgue.getBodies()) {
            body.soutStatus("");
        }
    }
}
