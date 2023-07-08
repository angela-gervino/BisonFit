package funkyflamingos.bisonfit.logic;

import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;
import funkyflamingos.bisonfit.persistence.stubs.RoutinesPersistenceStub;

public class RoutineHandler {


    private IRoutinesPersistence persistence;

    List<RoutineHeader> addedRoutines; //Delete this when "addNewRoutine()" has been implemented
    int addedRoutinesCounter; //Delete this when "addNewRoutine()" has been implemented
    ArrayList<ExerciseHeader> exerciseList; //This is just for testing purposes (delete this later)

    // Constructor for the stub

  //  public RoutineHandler() {
  //      persistence = new RoutinesPersistenceStub();
  //  }


    // Constructor for the database
    public RoutineHandler (){
        persistence = Services.getRoutinesPersistence();

        // The following is for testing purposes
        exerciseList = new ArrayList<ExerciseHeader>();
        exerciseList.add(new ExerciseHeader("Yoga", 0));
        exerciseList.add(new ExerciseHeader("Bicep Curls", 1));
        exerciseList.add(new ExerciseHeader("Super Long Exercise Name", 2));
        exerciseList.add(new ExerciseHeader("Jumping Jacks", 3));
        exerciseList.add(new ExerciseHeader("Dancing", 4));
        exerciseList.add(new ExerciseHeader("Jogging", 5));
        exerciseList.add(new ExerciseHeader("Push ups", 6));

        addedRoutines = new ArrayList<>(); //Delete this when "addNewRoutine()" has been implemented
        addedRoutinesCounter = 3; //Delete this when "addNewRoutine()" has been implemented
    }

    public RoutineHandler(IRoutinesPersistence p) {
        persistence = p;
    }

    public List<RoutineHeader> getAllRoutineHeaders() {
        //return persistence.getAllRoutineHeaders(); //Uncomment this when "addNewRoutine()" has been implemented
        List<RoutineHeader> allRoutineHeaders = new ArrayList<>(); //Delete this when "addNewRoutine()" has been implemented
        allRoutineHeaders.addAll(persistence.getAllRoutineHeaders()); //Delete this when "addNewRoutine()" has been implemented
        allRoutineHeaders.addAll(addedRoutines); //Delete this when "addNewRoutine()" has been implemented
        return allRoutineHeaders; //Delete this when "addNewRoutine()" has been implemented
    }

    public ArrayList<ExerciseHeader> getAllExerciseHeaders()
    {
        return exerciseList;
    }

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

    public Routine getRoutineByID(int routineID) {
        return persistence.getRoutineByID(routineID);
    }

    public void addNewRoutine(String routineName)
    {
        // This function needs to add a new routine to the persistence
        addedRoutines.add(new RoutineHeader(routineName, addedRoutinesCounter++)); //Delete this when "addNewRoutine()" has been implemented
    }
}
