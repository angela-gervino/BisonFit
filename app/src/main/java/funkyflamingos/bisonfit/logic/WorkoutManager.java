package funkyflamingos.bisonfit.logic;

import java.util.List;

import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;

public class WorkoutManager {

    private IRoutinesPersistence persistence;
    public WorkoutManager(IRoutinesPersistence p) {
        persistence = p;
    }
    public List<RoutineHeader> getAllRoutineHeaders() {
        return  persistence.getAllRoutineHeaders();
    }
}
