package funkyflamingos.bisonfit.logic;

import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.persistence.IExerciseLookupPersistence;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;

public class RoutineHandler implements IRoutineHandler {

    private IRoutinesPersistence persistence;
    private IExerciseLookupPersistence exerciseLookupPersistence;
    List<RoutineHeader> addedRoutines; //Delete this when "addNewRoutine()" has been implemented
    int addedRoutinesCounter; //Delete this when "addNewRoutine()" has been implemented
    ArrayList<ExerciseHeader> exerciseList; //This is just for testing purposes (delete this later)

    // Constructor for the database
    public RoutineHandler() {
        persistence = Services.getRoutinesPersistence();
        exerciseLookupPersistence = Services.getExerciseLookupPersistence();
        exerciseList = exerciseLookupPersistence.getAllExerciseHeaders();

        addedRoutines = new ArrayList<>(); //Delete this when "addNewRoutine()" has been implemented
        addedRoutinesCounter = 3; //Delete this when "addNewRoutine()" has been implemented
    }

    public RoutineHandler(IRoutinesPersistence p) {
        persistence = p;
    }

    @Override
    public List<RoutineHeader> getAllRoutineHeaders() {
        //return persistence.getAllRoutineHeaders(); //Uncomment this when "addNewRoutine()" has been implemented
        List<RoutineHeader> allRoutineHeaders = new ArrayList<>(); //Delete this when "addNewRoutine()" has been implemented
        allRoutineHeaders.addAll(persistence.getAllRoutineHeaders()); //Delete this when "addNewRoutine()" has been implemented
        allRoutineHeaders.addAll(addedRoutines); //Delete this when "addNewRoutine()" has been implemented
        return allRoutineHeaders; //Delete this when "addNewRoutine()" has been implemented
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
        // This function needs to add a new routine to the persistence
        addedRoutines.add(new RoutineHeader(routineName, addedRoutinesCounter++)); //Delete this when "addNewRoutine()" has been implemented
    }
}
