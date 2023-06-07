package funkyflamingos.bisonfit.logic;

import java.util.List;

import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;
import funkyflamingos.bisonfit.persistence.stubs.RoutinesPersistenceStub;

public class WorkoutManager {

    private IRoutinesPersistence persistence;
    public WorkoutManager() {
        persistence = new RoutinesPersistenceStub();
    }
    public WorkoutManager(IRoutinesPersistence p) {
        persistence = p;
    }
    public List<RoutineHeader> getAllRoutineHeaders() {
        return  persistence.getAllRoutineHeaders();
    }
}
