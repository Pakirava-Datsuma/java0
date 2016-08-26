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
            //this method shouldn't been called from here.
            human.goHome("this hospital haven't enough doctors! Hire someone! Man is crying :(");
        } else {
            workList.add(human);
        }
    }

    private DoctorWorkList getFreeWorkList() {
        if (isFull()) hireNewDoctor();
//        for (DoctorWorkList workList : workLists) {
//            if (!workList.isFull()) {
//                return workList;
//            }
//        }
        try {
            return workLists.stream()
                    .min((l1, l2) -> l1.getFreeSpace() - l2.getFreeSpace())
                    .get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public void writeOut (Human human) {
        for (DoctorWorkList workList:
                workLists)
           workList.remove(human);
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        workLists.add(new DoctorWorkList(doctor));
//        countDoctor++;
        doctor.soutStatus(" added");// as doctor#");//+countDoctor);
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


    public void removeDoctor(Doctor doctor) {
        DoctorWorkList workList = getWorkList(doctor);
        if (doctors.remove(doctor)) {
            doctor.soutStatus("* dressed out his bage.");
        }
        if (workList != null) {
            workLists.remove(workList);
            soutStatus("moving patients...");
            workList.getPatients().forEach(this::writeIn);
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

    public DoctorWorkList getWorkListWithPatients() {
        try {
            return workLists.stream()
                    .filter(workList -> workList.getPatients().size() > 0)
                    .findFirst()
                    .get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public DoctorWorkList getWorkList(Doctor doctor) {
        try {
            return workLists.stream()
                    .filter(workList ->
                            workList.doctor == doctor)
                    .findFirst()
                    .get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
