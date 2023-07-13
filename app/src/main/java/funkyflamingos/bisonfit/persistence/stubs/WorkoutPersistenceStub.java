package funkyflamingos.bisonfit.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;
import funkyflamingos.bisonfit.persistence.IWorkoutPersistence;

public class WorkoutPersistenceStub implements IWorkoutPersistence {

    List<Workout> allRoutines;
    int nextId = 0;

    public WorkoutPersistenceStub() {
        allRoutines = new ArrayList<Workout>();
    }

    @Override
    public List<WorkoutHeader> getAllRoutineHeaders() {
        List<WorkoutHeader> allHeaders = new ArrayList<WorkoutHeader>();
        for (Workout routine : allRoutines)
            allHeaders.add(routine.getHeader());
        return allHeaders;
    }

    @Override
    public Workout getRoutineByID(int routineID) {
        return allRoutines.stream().filter(routine -> routine.getHeader().getId() == routineID)
                .findFirst().orElse(null);
    }

    @Override
    public void addRoutine(String routineName) {
        allRoutines.add(new Workout(new WorkoutHeader(routineName, nextId)));
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
