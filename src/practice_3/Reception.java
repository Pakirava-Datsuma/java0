package practice_3;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by swanta on 05.06.16.
 */
public class Reception {
    public Set <DoctorWorkList> workLists = new HashSet<>();
    private Set <Doctor> doctors = new HashSet<>();
    private int countDoctor = 0;

    public void writeIn (Human human) {
        if (countDoctor > 0) {
            search:
            for (DoctorWorkList workList :
                    workLists) {

                if (workList.add(human)) break search;
                human.soutStatus("is crying!");
                human.goHome("this hospital haven't enough doctors! Hire someone!");
            }
        } else {
            human.goHome("this hospital have not ANY doctors left!");
        }
    }

    public void writeOut (Human human) {
        search: for (DoctorWorkList workList:
                workLists)
            if (workList.remove(human)) {
                human.goHome();
                break search;
            }else {
                human.soutStatus("can't be found in this hospital! " +
                        "You are bad programmer!");
                human.goHome(); //anyway:)
            }
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        workLists.add(new DoctorWorkList(doctor));
        countDoctor++;
        doctor.soutStatus("* is ready for job as doctor#"+countDoctor);
    }

    public boolean isFull() {
        if (countDoctor == 0) return true;
        for (DoctorWorkList list:
             workLists) {
            if (!list.isFull()) return false;
        }
        return true;
    }

    public Doctor getSomeDoctor(Human human) {
        Doctor doctor;
        if (doctors.isEmpty()) {
            soutStatus("doctor not found.");
            return null;
        }
        doctor = doctors.iterator().next();
        doctor.soutStatus("checking " + human.getName());
        return doctor;
    }

    public Doctor getSomeDoctor() {
        Doctor doctor;
        if (doctors.isEmpty()) {
            soutStatus("doctor not found.");
            return null;
        }
        doctor = doctors.iterator().next();
        soutStatus("doctor found.");
        doctor.soutStatus("is ready.");
        return doctor;
    }

    private void soutStatus(String status) {
        System.out.println("RECEPTION: "+status);
    }

    public Human healAtLeastOne() {
        while (getPatientsCount() > 0) {
            lists:
            for (DoctorWorkList list:
                    workLists) {
                Doctor doctor = list.doctor;
                if (list.size() > 0) {
                    patients:
                    for (Human patient :
                            list.getPatients()) {
                        if (!doctor.heal(patient))
                        if (!doctor.isFilingGood()) {
                            hospitalizeDoctor(list);
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

    private void hospitalizeDoctor(DoctorWorkList list) {
        Doctor doctor = list.doctor;
        doctor.soutStatus("hospitalizing...");
        removeDoctor(list);
        writeIn(doctor);
        doctor.soutStatus("hospitalized.");
    }

    private void removeDoctor(DoctorWorkList list) {
        countDoctor--;
        if (doctors.remove(list.doctor) && workLists.remove(list)) {
            list.doctor.soutStatus("gone from reception.");
        } else {
            list.doctor.soutStatus("STILL ON RECEPTION");
        }
        patients:
        for (Human patient:
             list.getPatients()) {
            patient.soutStatus("moving to another doctor...");
            writeOut(patient);
            patient.soutStatus("writed out from reception");
            writeIn(patient);
            patient.soutStatus("writed in on reception");
        }
    }

    public int getPatientsCount() {
        int count = 0;
        for (DoctorWorkList list:
             workLists) {
            count += list.size();
        }
        return count;
    }
}
