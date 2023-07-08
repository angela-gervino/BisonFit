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
    }

    public RoutineHandler(IRoutinesPersistence p) {
        persistence = p;
    }

    public List<RoutineHeader> getAllRoutineHeaders() {
        return persistence.getAllRoutineHeaders();
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
}
