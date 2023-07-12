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

    public void unselectAllExercises();

    void addNewRoutine(String routineName);

    public void addSelectedExercisesToRoutine(RoutineHeader routineHeader);

    public ArrayList<ExerciseHeader> getExerciseHeaders(RoutineHeader routineHeader);
}
