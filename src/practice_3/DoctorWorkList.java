package practice_3;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Created by swanta on 05.06.16.
 */
public class DoctorWorkList {
    public Doctor doctor;
    private HumanLimitedSet patients;

    public DoctorWorkList(Doctor doctor) {
        this.doctor = doctor;
        patients = new HumanLimitedSet(doctor.level);
    }

    public boolean add(Human human) {
        boolean result = (patients.add(human));
        if (result) {
            human.soutStatus("have been written in worklist of doctor "
                    + doctor.getNameAndHealth());
        }
        return result;
    }

    public boolean remove(Object human) {
        boolean result = (patients.remove(human));
        if (result) {
            ((Human)human).soutStatus("have been removed from worklist of doctor "
                    + doctor.getNameAndHealth());
        }
        return result;
    }

    public List<Human> getPatients() {
        return patients.stream().collect(Collectors.toList());
    }

    public boolean isFull() {
        return patients.isFull();
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Human getFirstPatient() {
        try {
            return patients.stream()
                    .findFirst()
                    .get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
