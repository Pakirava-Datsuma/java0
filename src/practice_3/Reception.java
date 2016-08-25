package practice_3;

import javax.print.Doc;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Created by swanta on 05.06.16.
 */
public class Reception {
    public Set <DoctorWorkList> workLists = new HashSet<>();
    private Set <Doctor> doctors = new HashSet<>();
//    private int countDoctor = 0;

    public void writeIn (Human human) {
        DoctorWorkList workList = getFreeWorkList();
        if (workList == null) {
            human.goHome("this hospital haven't enough doctors! Hire someone! Man is crying :(");
        } else {
            workList.add(human);
        }
    }

    private DoctorWorkList getFreeWorkList() {
        if (isFull()) hireNewDoctor();
        for (DoctorWorkList workList : workLists) {
            if (!workList.isFull()) {
                return workList;
            }
        }
        return null;
    }

    public void writeOut (Human human) {
        search: for (DoctorWorkList workList:
                workLists)
            if (workList.remove(human)) {
                human.goHome();
                break search;
            }else {
                human.goHome(" he(she) can't be found in this hospital! " +
                        "You are bad programmer!"); // anyway :)
            }
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        workLists.add(new DoctorWorkList(doctor));
//        countDoctor++;
        doctor.soutStatus("* added");// as doctor#");//+countDoctor);
    }

    public boolean isFull() {
        for (DoctorWorkList workList:
             workLists) {
            if (!workList.isFull()) return false;
        }
        return true;
    }

    public Doctor getSomeDoctor(Human human) {
        //public wrapper for getFreeDoctor()
        //TODO: replace with getFreeDoctor()
        return getFreeDoctor();
    }

    private Doctor getFreeDoctor() {
        return getFreeWorkList().doctor;
    }


    public Doctor getSomeDoctor() {
        Doctor doctor;
        try {
            doctor = doctors.iterator().next();
        }
        catch (NoSuchElementException e) {
            soutStatus("\n...not enough doctors! need to hire one.");
            doctor = hireNewDoctor();
        }
//        soutStatus("doctor found.");
//        doctor.soutStatus("is ready.");
        return doctor;
    }

    private Doctor hireNewDoctor() {
        Doctor doctor = Doctor.getHealthyDoctor();
        doctor.soutStatus("* hired");
        addDoctor(doctor);
        return doctor;
    }

    private void soutStatus(String status) {
        System.out.println("RECEPTION: "+status);
    }

    public Human healAtLeastOne() {
        while (getPatientsCount() > 0) {
            lists:
            for (DoctorWorkList workList:
                    workLists) {
                Doctor doctor = workList.doctor;
                if (workList.getPatients().size() > 0) {
                    patients:
                    for (Human patient :
                            workList.getPatients()) {
                        if (!doctor.heal(patient))
                        if (!doctor.isFilingGood()) {
                            hospitalizeDoctor(workList);
                            continue lists;
                        }
                        if (!doctor.isNeedHeal(patient)) {
                            return patient;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void hospitalizeDoctor(DoctorWorkList workList) {
        Doctor doctor = workList.doctor;
        doctor.soutStatus("* is hospitalizing...");
        removeDoctor(workList);
        writeIn(doctor);
//        doctor.soutStatus("hospitalized.");
    }

    private void removeDoctor(DoctorWorkList workList) {
//        countDoctor--;
        Doctor doctor = workList.doctor;
        doctors.remove(doctor);
        doctor.soutStatus("* dressed out his bage.");
        workLists.remove(workList);
        soutStatus("moving patients...");
        patients:
        for (Human patient:
             workList.getPatients()) {
//            patient.soutStatus("* moving to another doctor...");
//            writeOut(patient);
//            patient.soutStatus("* writed out from reception");
            writeIn(patient);
//            patient.soutStatus("* writed in on reception");
        }
    }

    public int getPatientsCount() {
        int count = 0;
        for (DoctorWorkList workList:
             workLists) {
            count += workList.getPatients().size();
        }
        return count;
    }

    public int getDoctorsCount() {
        return doctors.size();
    }
}
