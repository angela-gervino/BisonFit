package funkyflamingos.bisonfit.persistence;

import java.util.List;

import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;

public interface IWorkoutPersistence {

    List<WorkoutHeader> getAllRoutineHeaders();

    Workout getRoutineByID(int routineID);

    void addRoutine(String routineName);

    void deleteRoutineById(int routineId);
}
