package funkyflamingos.bisonfit.persistence;

import java.util.List;

import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.dso.RoutineHeader;

public interface IRoutinesPersistence {

    List<RoutineHeader> getAllRoutineHeaders();

    Routine getRoutineByID(int routineID);

    void addRoutine(String routineName);

    void deleteRoutineById(int routineId);
}
