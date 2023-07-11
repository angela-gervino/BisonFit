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
import funkyflamingos.bisonfit.persistence.ISavedRoutineExercises;
import funkyflamingos.bisonfit.persistence.stubs.RoutinesPersistenceStub;

public class RoutineHandler {

public class RoutineHandler implements IRoutineHandler {

    private IRoutinesPersistence persistence;
    private IExerciseLookupPersistence exerciseLookupPersistence;

    private ISavedRoutineExercises savedRoutineExercisesPersistence;
    ArrayList<ExerciseHeader> exerciseList; //This is just for testing purposes (delete this later)

    // Constructor for the database
    public RoutineHandler() {
        persistence = Services.getRoutinesPersistence();
        exerciseLookupPersistence = Services.getExerciseLookupPersistence();
        exerciseList = exerciseLookupPersistence.getAllExerciseHeaders();

        savedRoutineExercisesPersistence = Services.getSavedRoutineExercises();
    }

    public RoutineHandler(IRoutinesPersistence p) {
        persistence = p;
    }

    @Override
    public List<RoutineHeader> getAllRoutineHeaders() {
        return persistence.getAllRoutineHeaders();
    }

    @Override
    public ArrayList<ExerciseHeader> getAllExerciseHeaders()
    {
        return exerciseList;
    }

    @Override
    public ArrayList<ExerciseHeader> getAllSelectedExercises()
    {
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
        return persistence.getRoutineByID(routineID);
    }

    @Override
    public void addNewRoutine(String routineName)
    {
        System.out.println("adding routine" + routineName + " in logic");
        persistence.addRoutine(routineName);
    }

    public void deleteRoutine(int routineID)
    {
        persistence.deleteRoutineById(routineID);
        savedRoutineExercisesPersistence.deleteRoutine(getRoutineByID(routineID).getHeader());
    }
}
