package funkyflamingos.bisonfit.persistence;

import java.util.ArrayList;

import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.WorkoutHeader;

public interface ISavedWorkoutExercises {
    public ArrayList<ExerciseHeader> getExercisesByRoutine(WorkoutHeader routineHeader);

    public void addExercises(ArrayList<ExerciseHeader> exerciseHeaders, WorkoutHeader routineHeader);

    public void deleteExercise(ExerciseHeader exerciseHeader, WorkoutHeader routineHeader);

    public void deleteRoutine(WorkoutHeader routineHeader);
}
