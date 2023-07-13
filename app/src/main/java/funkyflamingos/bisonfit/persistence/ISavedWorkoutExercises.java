package funkyflamingos.bisonfit.persistence;

import java.util.ArrayList;

import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.WorkoutHeader;

public interface ISavedWorkoutExercises {
    public ArrayList<ExerciseHeader> getExercisesByWorkout(WorkoutHeader workoutHeader);

    public void addExercises(ArrayList<ExerciseHeader> exerciseHeaders, WorkoutHeader workoutHeader);

    public void deleteExercise(ExerciseHeader exerciseHeader, WorkoutHeader workoutHeader);

    public void deleteWorkout(WorkoutHeader workoutHeader);
}
