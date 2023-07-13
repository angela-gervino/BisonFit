package funkyflamingos.bisonfit.persistence;

import java.util.ArrayList;

import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.WorkoutHeader;

public interface ISavedWorkoutExercises {
    ArrayList<ExerciseHeader> getExercisesByWorkout(WorkoutHeader workoutHeader);

    void addExercises(ArrayList<ExerciseHeader> exerciseHeaders, WorkoutHeader workoutHeader);

    void deleteExercise(ExerciseHeader exerciseHeader, WorkoutHeader workoutHeader);

    void deleteWorkout(WorkoutHeader workoutHeader);
}
