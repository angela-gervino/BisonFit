package funkyflamingos.bisonfit.logic;

import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;

public interface IWorkoutHandler {
    List<WorkoutHeader> getAllRoutineHeaders();

    ArrayList<ExerciseHeader> getAllExerciseHeaders();

    ArrayList<ExerciseHeader> getAllSelectedExercises();

    Workout getRoutineByID(int routineID);

    void unselectAllExercises();

    void addNewRoutine(String routineName);

    void addSelectedExercisesToRoutine(WorkoutHeader routineHeader);

    ArrayList<ExerciseHeader> getExerciseHeaders(WorkoutHeader routineHeader);

    void deleteRoutine(int routineID);

    void deleteExercise(ExerciseHeader exerciseHeader, WorkoutHeader routineHeader);
}
