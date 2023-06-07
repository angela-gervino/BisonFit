package funkyflamingos.bisonfit.logic;

import java.util.List;

import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;
import funkyflamingos.bisonfit.persistence.stubs.RoutinesPersistenceStub;

public class RoutineHandler {

    private IRoutinesPersistence persistence;

    public RoutineHandler() {
        persistence = new RoutinesPersistenceStub();
    }

    public RoutineHandler(IRoutinesPersistence p) {
        persistence = p;
    }

    public List<RoutineHeader> getAllRoutineHeaders() {
        return persistence.getAllRoutineHeaders();
    }
}
