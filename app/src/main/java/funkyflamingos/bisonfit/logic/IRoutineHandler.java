package funkyflamingos.bisonfit.logic;

import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.dso.RoutineHeader;

public interface IRoutineHandler {
    List<RoutineHeader> getAllRoutineHeaders();

    ArrayList<ExerciseHeader> getAllExerciseHeaders();

    ArrayList<ExerciseHeader> getAllSelectedExercises();

    Routine getRoutineByID(int routineID);

    void unselectAllExercises();

    void addNewRoutine(String routineName);

    void addSelectedExercisesToRoutine(RoutineHeader routineHeader);

    ArrayList<ExerciseHeader> getExerciseHeaders(RoutineHeader routineHeader);

    void deleteRoutine(int routineID);

    void deleteExercise(ExerciseHeader exerciseHeader, RoutineHeader routineHeader);
}
