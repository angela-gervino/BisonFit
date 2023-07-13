package funkyflamingos.bisonfit.logic;

import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;

public interface IWorkoutHandler {
    List<WorkoutHeader> getAllWorkoutHeaders();

    ArrayList<ExerciseHeader> getAllExerciseHeaders();

    ArrayList<ExerciseHeader> getAllSelectedExercises();

    Workout getWorkoutByID(int workoutID);

    void unselectAllExercises();

    void addNewWorkout(String workoutName);

    void addSelectedExercisesToWorkout(int workoutID);

    ArrayList<ExerciseHeader> getExerciseHeaders(WorkoutHeader workoutHeader);

    void deleteWorkout(int workoutID);

    void deleteExercise(ExerciseHeader exerciseHeader, WorkoutHeader workoutHeader);
}
