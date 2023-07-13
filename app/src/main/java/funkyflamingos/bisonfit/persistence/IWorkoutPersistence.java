package funkyflamingos.bisonfit.persistence;

import java.util.List;

import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;

public interface IWorkoutPersistence {

    List<WorkoutHeader> getAllWorkoutHeaders();

    Workout getWorkoutByID(int workoutID);

    void addWorkout(String workoutName);

    void deleteWorkoutById(int workoutId);
}
