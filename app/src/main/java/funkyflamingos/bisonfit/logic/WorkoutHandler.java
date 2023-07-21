package funkyflamingos.bisonfit.logic;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.dso.Exercise;
import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.ExerciseSet;
import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;
import funkyflamingos.bisonfit.persistence.IExerciseLookupPersistence;
import funkyflamingos.bisonfit.persistence.IWorkoutPersistence;
import funkyflamingos.bisonfit.persistence.ISavedWorkoutExercises;

public class WorkoutHandler implements IWorkoutHandler {
    private IWorkoutPersistence workoutsPersistence;
    private ISavedWorkoutExercises savedWorkoutExercisesPersistence;
    private IExerciseLookupPersistence exerciseLookupPersistence;
    private ArrayList<ExerciseHeader> exerciseList;

    // Constructor for the database
    public WorkoutHandler() {
        workoutsPersistence = Services.getWorkoutsPersistence();
        savedWorkoutExercisesPersistence = Services.getSavedWorkoutExercises();
        exerciseLookupPersistence = Services.getExerciseLookupPersistence();

        exerciseList = exerciseLookupPersistence.getAllExerciseHeaders();
    }

    public WorkoutHandler(IWorkoutPersistence r, ISavedWorkoutExercises s, IExerciseLookupPersistence e) {
        workoutsPersistence = r;
        savedWorkoutExercisesPersistence = s;
        exerciseLookupPersistence = e;

        exerciseList = exerciseLookupPersistence.getAllExerciseHeaders();
    }

    @Override
    public List<WorkoutHeader> getAllWorkoutHeaders() {
        return workoutsPersistence.getAllWorkoutHeaders();
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
    public WorkoutHeader getWorkoutByID(int workoutID) {
        return workoutsPersistence.getWorkoutHeaderByID(workoutID);
    }

    @Override
    public void addNewWorkout(String workoutName) {
        workoutsPersistence.addWorkout(workoutName);
    }

    @Override
    public void deleteWorkout(int workoutID) {
        if (getWorkoutByID(workoutID) != null) {
            savedWorkoutExercisesPersistence.deleteWorkout(getWorkoutByID(workoutID));
            workoutsPersistence.deleteWorkoutById(workoutID);
        }
    }

    @Override
    public void addSelectedExercisesToWorkout(int workoutID)
    {
        savedWorkoutExercisesPersistence.addExercises(getAllSelectedExercises(), workoutID);

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
    public ArrayList<ExerciseHeader> getExerciseHeaders(WorkoutHeader workoutHeader)
    {
        return savedWorkoutExercisesPersistence.getExercisesByWorkout(workoutHeader);
    }

    @Override
    public void deleteExercise(ExerciseHeader exerciseHeader, WorkoutHeader workoutHeader)
    {
        savedWorkoutExercisesPersistence.deleteExercise(exerciseHeader, workoutHeader);
    }

    @Override
    public boolean savePerformedWorkout(Workout workout) {
        Workout workoutProcessed = new Workout(workout.getHeader());
        for(Exercise curExercise : workout.getAllExercises()) {
            Exercise newExercise = new Exercise(curExercise.getHeader());
            for(ExerciseSet currSet : curExercise.getAllSets()) {
                ExerciseSet newSet;

                // validate values: replace invalid (<0) values with 0s
                if(currSet.getReps() < 0)
                    currSet.setReps(0);
                if(currSet.getWeight() < 0)
                    currSet.setWeight(0);

                // add this set to the exercise only if it has data
                if(currSet.getWeight() > 0 && currSet.getReps() > 0) {
                    newSet = new ExerciseSet(currSet.getWeight(), currSet.getReps());
                    newExercise.addSet(newSet);
                }
            }

            // add this exercise to the workout only if it has sets
            if(newExercise.getAllSets().size() > 0)
                workoutProcessed.addExercise(newExercise);
        }

        // only save this if the processed workout has exercises
        if(workoutProcessed.getAllExercises().size() > 0) {
            //TODO: Save to workoutProcessed to DB
            return true;
        }
        return  false;
    }
}
