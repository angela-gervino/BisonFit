package funkyflamingos.bisonfit.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;

public class RoutinesPersistenceStub implements IRoutinesPersistence {

    List<Routine> allRoutines;
    int nextId = 0;

    public RoutinesPersistenceStub() {
        allRoutines = new ArrayList<Routine>();
    }

    @Override
    public List<RoutineHeader> getAllRoutineHeaders() {
        List<RoutineHeader> allHeaders = new ArrayList<RoutineHeader>();
        for (Routine routine : allRoutines)
            allHeaders.add(routine.getHeader());
        return allHeaders;
    }

    @Override
    public Routine getRoutineByID(int routineID) {
        return allRoutines.stream().filter(routine -> routine.getHeader().getId() == routineID)
                .findFirst().orElse(null);
    }

    @Override
    public void addRoutine(String routineName) {
        allRoutines.add(new Routine(new RoutineHeader(routineName, nextId)));
        nextId++;
    }

    @Override
    public void deleteRoutineById(int routineId) {
        allRoutines.remove(getIndexById(routineId));
    }

    private int getIndexById(int routineId) {
        int index = -1;
        for (int i = 0; i < allRoutines.size() && index == -1; i++) {
            if (allRoutines.get(i).getHeader().getId() == routineId)
                index = i;
        }
        return index;
    }
}
