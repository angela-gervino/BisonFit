package funkyflamingos.bisonfit.logic;

import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.persistence.IExerciseLookupPersistence;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;
import funkyflamingos.bisonfit.persistence.ISavedRoutineExercises;

public class RoutineHandler implements IRoutineHandler {
    private IRoutinesPersistence routinesPersistence;
    private ISavedRoutineExercises savedRoutineExercisesPersistence;
    private IExerciseLookupPersistence exerciseLookupPersistence;
    ArrayList<ExerciseHeader> exerciseList;

    // Constructor for the database
    public RoutineHandler() {
        routinesPersistence = Services.getRoutinesPersistence();
        savedRoutineExercisesPersistence = Services.getSavedRoutineExercises();
        exerciseLookupPersistence = Services.getExerciseLookupPersistence();

        exerciseList = exerciseLookupPersistence.getAllExerciseHeaders();
    }

    public RoutineHandler(IRoutinesPersistence r, ISavedRoutineExercises s, IExerciseLookupPersistence e) {
        routinesPersistence = r;
        savedRoutineExercisesPersistence = s;
        exerciseLookupPersistence = e;

        exerciseList = exerciseLookupPersistence.getAllExerciseHeaders();
    }

    @Override
    public List<RoutineHeader> getAllRoutineHeaders() {
        return routinesPersistence.getAllRoutineHeaders();
    }

    @Override
    public ArrayList<ExerciseHeader> getAllExerciseHeaders() {
        return exerciseList;
    }

    @Override
    public ArrayList<ExerciseHeader> getAllSelectedExercises() {
        ArrayList<ExerciseHeader> selectedExerciseHeaders = new ArrayList<>();
        exerciseList.forEach(exerciseHeader -> {
            if (exerciseHeader.isSelected()) {
                selectedExerciseHeaders.add(exerciseHeader);
            }
        });

        return selectedExerciseHeaders;
    }

    @Override
    public Routine getRoutineByID(int routineID) {
        return routinesPersistence.getRoutineByID(routineID);
    }

    @Override
    public void addNewRoutine(String routineName) {
        routinesPersistence.addRoutine(routineName);
    }

    @Override
    public void deleteRoutine(int routineID) {
        if (getRoutineByID(routineID) != null) {
            savedRoutineExercisesPersistence.deleteRoutine(getRoutineByID(routineID).getHeader());
            routinesPersistence.deleteRoutineById(routineID);
        }
    }

    @Override
    public void addSelectedExercisesToRoutine(RoutineHeader routineHeader)
    {
        savedRoutineExercisesPersistence.addExercises(getAllSelectedExercises(), routineHeader);

        unselectAllExercises();
    }

    @Override
    public void unselectAllExercises()
    {
        exerciseList.forEach(exerciseHeader -> {
            if (exerciseHeader.isSelected()) {
                exerciseHeader.toggleSelected();
            }
        });
    }

    @Override
    public ArrayList<ExerciseHeader> getExerciseHeaders(RoutineHeader routineHeader)
    {
        return savedRoutineExercisesPersistence.getExercisesByRoutine(routineHeader);
    }

    @Override
    public void deleteExercise(ExerciseHeader exerciseHeader, RoutineHeader routineHeader)
    {
        savedRoutineExercisesPersistence.deleteExercise(exerciseHeader, routineHeader);
    }
}
