package practice_3;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by swanta on 21.06.16.
 */
public class WorkLists {
    private Set<DoctorWorkList> workList;

    public WorkLists(Collection<DoctorWorkList> workList) {
        this.workList.addAll(workList);
    }

    public boolean hasNoPatients () {
        for (DoctorWorkList list :
                workList) {
            if (list.getPatients().size() > 0) {
                return false;
            }
            }
        return true;
    }
}
