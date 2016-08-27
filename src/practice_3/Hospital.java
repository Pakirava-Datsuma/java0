package practice_3;

/**
 * Created by swanta on 05.06.16.
 */
public class Hospital {
    private Reception reception = new Reception();
    private Morgue morgue = new Morgue();

    public void come(Patient patient) {
        patient.soutStatus("come to hospital.");
        Doctor doctor = reception.getFreeDoctor();
        boolean patientIsReady = false;
            do {
                if (doctor.isDead(patient)) {
                    morgue.writeIn(patient);
                    patientIsReady = true;
                } else if (doctor.isNeedHospitalization(patient)) {
                    reception.writeIn(patient);
                    patientIsReady = true;
                } else if (doctor.isNeedHeal(patient)) {
                    doctor.heal(patient);
                    if (!doctor.isFilingGood()) {
                        helpDoctor(doctor);
                        doctor = reception.getFreeDoctor();
                    }
                } else {
                    patient.goHome(" this patient is not ill");
                    patientIsReady = true;
                }
            } while (!patientIsReady);
    }

    public void come(Doctor doctor) {
        if (doctor.isFilingGood()) {
            reception.addDoctor(doctor);
        } else {
            helpDoctor(doctor);
        }
    }

    public boolean isFullOfPatients() {
        return reception.isFull();
    }

    public void healAll() {
        while (!isEmptyOfPatients()) {
            DoctorWorkList workList = reception.getWorkListWithPatients();
            Doctor doctor = workList.getDoctor();
            Healable patient = workList.getFirstPatient();
            /*
            Reception shall not check if patient needs healing.
            Reception only managing contents of worklists
                and collection of doctors.
            So technical specification of this task has a lot of mistakes and misunderstands.
            And this code reflects missing of effective TS in quality of code.
            So I'm sorry for all this spaghetti :)
             */
            if (healPatient(patient, doctor)) { // curing ended
                reception.writeOut(patient);
                if (doctor.isDead(patient)) { // patient is dead
                    morgue.writeIn((Human)patient);
                }
                else { //patient is healthy
                    try { // he is a doctor
                        come((Doctor) patient);
                    }
                    catch (ClassCastException e) { // he isn't a doctor
                        ((Human)patient).goHome("he(she) feel good");
                    }
                }
            }
            else {
                helpDoctor(doctor);
            }
        }
    }

    private void helpDoctor(Doctor doctor) {
        // doctor feels bad
                doctor.soutStatus("* feels BAD and CAN'T work right now");
        reception.removeDoctor(doctor);
        if (doctor.isDead(doctor)) {
            morgue.writeIn(doctor);
        }
        else {
            reception.writeIn(doctor);
        }
    }


    // true if patient is health (or dead)
    // false if doctor is filing bad
    // if both (1) and (2)
    //    it's important to hospitalize doctor
    //    and let another doctor check the patient
    public boolean healPatient(Healable patient, Doctor doctor) {
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
            for (Healable patient :
                    workList.getPatients()) {
                ((Human)patient).soutStatus("");
            }
        }
        System.out.println(morgue.getBodiesCount() + " bodies in MORGUE:");
        for (Human body :
                morgue.getBodies()) {
            body.soutStatus("");
        }
    }
}
