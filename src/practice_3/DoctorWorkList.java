package practice_3;

import java.util.HashSet;

/**
 * Created by swanta on 05.06.16.
 */
public class DoctorWorkList extends HumanLimitedSet{
    public Doctor doctor;
    private Human[] patients;

    public DoctorWorkList(Doctor doctor) {
        this.doctor = doctor;
        patientsMaxCount = doctor.level;
    }

    @Override
    public boolean add(Human human) {
        boolean result = (super.add(human));
        if (result) {
            human.soutStatus("have been written in worklist of doctor "
                    + doctor.getName());
        }
        return result;
    }

    @Override
    public boolean remove(Object human) {
        boolean result = (super.remove(human));
        if (result) {
            ((Human)human).soutStatus("have been removed from worklist of doctor "
                    + doctor.getName());
        }
        return result;
    }

    public Human[] getPatients() {
        return patients;
    }
}
