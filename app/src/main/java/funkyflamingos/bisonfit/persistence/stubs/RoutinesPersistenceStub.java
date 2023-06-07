package funkyflamingos.bisonfit.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;

public class RoutinesPersistenceStub implements IRoutinesPersistence {

    List<Routine> allRoutines;

    public RoutinesPersistenceStub() {
        allRoutines = new ArrayList<Routine>();
        allRoutines.add(new Routine(new RoutineHeader("Upper Body", 1)));
        allRoutines.add(new Routine(new RoutineHeader("Always skip leg day", 2)));
        allRoutines.add(new Routine(new RoutineHeader("Core", 3)));
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
        return allRoutines.stream().filter(r -> r.getHeader().getId() == routineID)
                .findFirst().orElse(null);
    }

}
