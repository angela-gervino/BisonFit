package funkyflamingos.bisonfit.logic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.dso.Exercise;
import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.ExerciseSet;
import funkyflamingos.bisonfit.dso.PerformedWorkoutHeader;
import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;
import funkyflamingos.bisonfit.persistence.IExerciseLookupPersistence;
import funkyflamingos.bisonfit.persistence.IPerformedWorkoutRecordPersistence;
import funkyflamingos.bisonfit.persistence.IWorkoutPersistence;
import funkyflamingos.bisonfit.persistence.ISavedWorkoutExercises;

public class WorkoutHandler implements IWorkoutHandler {
    private IWorkoutPersistence workoutsPersistence;
    private ISavedWorkoutExercises savedWorkoutExercisesPersistence;
    private IExerciseLookupPersistence exerciseLookupPersistence;
    private IPerformedWorkoutRecordPersistence performedWorkoutRecordPersistence;
    private ArrayList<ExerciseHeader> exerciseList;

    // Constructor for the database
    public WorkoutHandler() {
        workoutsPersistence = Services.getWorkoutsPersistence();
        savedWorkoutExercisesPersistence = Services.getSavedWorkoutExercises();
        exerciseLookupPersistence = Services.getExerciseLookupPersistence();
        performedWorkoutRecordPersistence = Services.getPerformedWorkoutRecordPersistence();

        exerciseList = exerciseLookupPersistence.getAllExerciseHeaders();
    }

    public WorkoutHandler(IWorkoutPersistence r, ISavedWorkoutExercises s, IExerciseLookupPersistence e, IPerformedWorkoutRecordPersistence p) {
        workoutsPersistence = r;
        savedWorkoutExercisesPersistence = s;
        exerciseLookupPersistence = e;
        performedWorkoutRecordPersistence = p;

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
    public WorkoutHeader getWorkoutHeaderByID(int workoutID) {
        return workoutsPersistence.getWorkoutHeaderByID(workoutID);
    }

    @Override
    public void addNewWorkout(String workoutName) {
        workoutsPersistence.addWorkout(workoutName);
    }

    @Override
    public void deleteWorkout(int workoutID) {
        if (this.getWorkoutHeaderByID(workoutID) != null) {
            savedWorkoutExercisesPersistence.deleteWorkout(this.getWorkoutHeaderByID(workoutID));
            workoutsPersistence.deleteWorkoutById(workoutID);
        }
    }

    @Override
    public void addSelectedExercisesToWorkout(int workoutID) {
        savedWorkoutExercisesPersistence.addExercises(getAllSelectedExercises(), workoutID);

        unselectAllExercises();
    }

    @Override
    public void unselectAllExercises() {
        exerciseList.forEach(exerciseHeader -> {
            if (exerciseHeader.isSelected()) {
                exerciseHeader.toggleSelected();
            }
        });
    }

    @Override
    public ArrayList<ExerciseHeader> getExerciseHeaders(WorkoutHeader workoutHeader) {
        return savedWorkoutExercisesPersistence.getExercisesByWorkout(workoutHeader);
    }

    @Override
    public void deleteExercise(ExerciseHeader exerciseHeader, WorkoutHeader workoutHeader) {
        savedWorkoutExercisesPersistence.deleteExercise(exerciseHeader, workoutHeader);
    }

    @Override
    public ArrayList<PerformedWorkoutHeader> getPerformedWorkoutHeaders() {
        ArrayList<PerformedWorkoutHeader> list = performedWorkoutRecordPersistence.getPerformedWorkoutHeaders();
        return list;
    }

    @Override
    public boolean savePerformedWorkout(Workout workout) {
        Workout workoutProcessed = new Workout(workout.getHeader());
        for (Exercise curExercise : workout.getAllExercises()) {
            Exercise newExercise = new Exercise(curExercise.getHeader());
            newExercise.getHeader().resetSetCount();
            for (ExerciseSet currSet : curExercise.getAllSets()) {
                ExerciseSet newSet;

                // validate values: replace invalid (<0) values with 0s
                if (currSet.getReps() < 0)
                    currSet.setReps(0);
                if (currSet.getWeight() < 0)
                    currSet.setWeight(0);

                // add this set to the exercise only if it has data
                if (currSet.getWeight() > 0 && currSet.getReps() > 0) {
                    newSet = new ExerciseSet(currSet.getWeight(), currSet.getReps());
                    newExercise.addSet(newSet);
                }
            }

            // add this exercise to the workout only if it has sets
            if (newExercise.getAllSets().size() > 0)
                workoutProcessed.addExercise(newExercise);
        }

        // only save this if the processed workout has exercises
        if (workoutProcessed.getAllExercises().size() > 0) {
            PerformedWorkoutHeader performedHeader = (PerformedWorkoutHeader) workoutProcessed.getHeader();
            performedHeader.setDateEnded(LocalDateTime.now());
            performedWorkoutRecordPersistence.addPerformedWorkout(workoutProcessed);
            return true;
        }
        return false;
    }

    @Override
    public Workout getWorkoutToPerform(int workoutID) {
        Workout workoutToPerform = workoutsPersistence.getWorkoutByID(workoutID);
        workoutToPerform.setHeader(new PerformedWorkoutHeader(workoutToPerform.getHeader()));
        return workoutToPerform;
    }

    @Override
    public Workout getPerformedWorkout(int workoutID) {
        return performedWorkoutRecordPersistence.getPerformedWorkoutById(workoutID);
    }
}
