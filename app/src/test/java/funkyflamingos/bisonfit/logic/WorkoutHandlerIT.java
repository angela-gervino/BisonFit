package funkyflamingos.bisonfit.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import funkyflamingos.bisonfit.persistence.hsqldb.ExerciseLookupPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.PerformedWorkoutRecordPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.WorkoutPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.SavedWorkoutExercisesPersistenceHSQLDB;
import funkyflamingos.bisonfit.utils.TestUtils;

public class WorkoutHandlerIT {
    private WorkoutHandler workoutHandler;

    private File tempDB;

    @Before
    public void setup() throws Exception {
        tempDB = TestUtils.copyDB();

        String dbPathName = this.tempDB.getAbsolutePath().replace(".script", "");

        final IWorkoutPersistence workoutsPersistenceStub = new WorkoutPersistenceHSQLDB(dbPathName);
        final ISavedWorkoutExercises savedWorkoutExercisesPersistenceStub = new SavedWorkoutExercisesPersistenceHSQLDB(dbPathName);
        final IExerciseLookupPersistence exerciseLookupPersistenceStub = new ExerciseLookupPersistenceHSQLDB(dbPathName);
        final IPerformedWorkoutRecordPersistence performedWorkoutRecordPersistence = new PerformedWorkoutRecordPersistenceHSQLDB(dbPathName);
        workoutHandler = new WorkoutHandler(workoutsPersistenceStub, savedWorkoutExercisesPersistenceStub,
                exerciseLookupPersistenceStub, performedWorkoutRecordPersistence);
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }

    @Test
    public void testGetByIDFound() {
        workoutHandler.addNewWorkout("My New Workout Workout");
        assertNotNull(workoutHandler.getWorkoutHeaderByID(0));
    }

    @Test
    public void testGetByIDNotFound() {
        assertNull(workoutHandler.getWorkoutHeaderByID(0));
    }

    @Test
    public void testExpectedName() {
        workoutHandler.addNewWorkout("Upper Body");
        WorkoutHeader foundWorkout = workoutHandler.getWorkoutHeaderByID(0);
        assertTrue(foundWorkout.getName().equals("Upper Body"));
    }

    @Test
    public void testGetByIDHeaderNotNull() {
        workoutHandler.addNewWorkout("Upper Body");
        assertNotNull(workoutHandler.getWorkoutHeaderByID(0));
    }

    @Test
    public void testGetAllWorkoutHeadersNotNull() {
        assertNotNull(workoutHandler.getAllWorkoutHeaders());
    }

    @Test
    public void testGetAllWorkoutReturnsList() {
        assertTrue(workoutHandler.getAllWorkoutHeaders() instanceof List);
    }

    @Test
    public void testGetAllExerciseHeadersNotNull() {
        assertNotNull(workoutHandler.getAllExerciseHeaders());
    }

    @Test
    public void testGetAllExerciseReturnsArrayList() {
        assertTrue(workoutHandler.getAllExerciseHeaders() instanceof ArrayList);
    }

    @Test
    public void testGetAllSelectedExercisesEmpty() {
        assertTrue(workoutHandler.getAllSelectedExercises().isEmpty());
    }

    @Test
    public void testGetAllSelectedExercisesNonEmpty() {
        ArrayList<ExerciseHeader> exercises = workoutHandler.getAllExerciseHeaders();
        exercises.forEach(exercise -> {
            if (!exercise.isSelected())
                exercise.toggleSelected();
        });

        assertFalse(workoutHandler.getAllSelectedExercises().isEmpty());
    }

    @Test
    public void testUnselectAllExercisesWhenNoneSelected() {
        workoutHandler.unselectAllExercises();
        assertTrue(workoutHandler.getAllSelectedExercises().isEmpty());
    }

    @Test
    public void testUnselectAllExercisesAllSelected() {
        ArrayList<ExerciseHeader> exercises = workoutHandler.getAllExerciseHeaders();
        exercises.forEach(exercise -> {
            if (!exercise.isSelected())
                exercise.toggleSelected();
        });

        workoutHandler.unselectAllExercises();
        assertTrue(workoutHandler.getAllSelectedExercises().isEmpty());
    }

    @Test
    public void testAddNewWorkout() {
        workoutHandler.addNewWorkout("First Workout Workout!");
        workoutHandler.addNewWorkout("Second Workout Workout!");
        workoutHandler.addNewWorkout("Third Workout Workout!");

        assert (workoutHandler.getAllWorkoutHeaders().size() == 3);
    }

    @Test
    public void testAddSelectedExercisesToWorkoutWhenAllSelected() {
        workoutHandler.addNewWorkout("First Workout Workout!");
        WorkoutHeader firstWorkout = workoutHandler.getAllWorkoutHeaders().get(0);

        ArrayList<ExerciseHeader> exercises = workoutHandler.getAllExerciseHeaders();
        exercises.forEach(exercise -> {
            if (!exercise.isSelected())
                exercise.toggleSelected();
        });

        workoutHandler.addSelectedExercisesToWorkout(firstWorkout.getId());

        assertEquals(exercises.size(), workoutHandler.getExerciseHeaders(firstWorkout).size());
    }

    @Test
    public void testAddSelectedExercisesToWorkoutWhenNoneSelected() {
        workoutHandler.addNewWorkout("First Workout Workout!");
        WorkoutHeader firstWorkout = workoutHandler.getAllWorkoutHeaders().get(0);

        workoutHandler.addSelectedExercisesToWorkout(firstWorkout.getId());

        assertEquals(0, workoutHandler.getExerciseHeaders(firstWorkout).size());
    }

    @Test
    public void testGetExerciseHeadersWhenEmpty() {
        workoutHandler.addNewWorkout("First Workout Workout!");
        WorkoutHeader firstWorkout = workoutHandler.getAllWorkoutHeaders().get(0);

        assertTrue(workoutHandler.getExerciseHeaders(firstWorkout).isEmpty());
    }

    @Test
    public void testGetExerciseHeadersWhenNotEmpty() {
        workoutHandler.addNewWorkout("First Workout Workout!");
        WorkoutHeader firstWorkout = workoutHandler.getAllWorkoutHeaders().get(0);

        ArrayList<ExerciseHeader> exercises = workoutHandler.getAllExerciseHeaders();
        exercises.get(0).toggleSelected();

        workoutHandler.addSelectedExercisesToWorkout(firstWorkout.getId());

        assertEquals(1, workoutHandler.getExerciseHeaders(firstWorkout).size());
    }

    @Test
    public void testDeleteWorkoutWhenNonexistent() {
        workoutHandler.deleteWorkout(0);

        assertTrue(workoutHandler.getAllWorkoutHeaders().isEmpty());
    }

    @Test
    public void testDeleteWorkoutWhenExists() {
        workoutHandler.addNewWorkout("Upper Body Workout");
        workoutHandler.deleteWorkout(0);

        assertTrue(workoutHandler.getAllWorkoutHeaders().isEmpty());
    }

    @Test
    public void testDeleteExerciseWhenNonexistent() {
        workoutHandler.addNewWorkout("Fun Workout");
        WorkoutHeader funWorkout = workoutHandler.getWorkoutHeaderByID(0);

        ArrayList<ExerciseHeader> exercises = workoutHandler.getAllExerciseHeaders();
        workoutHandler.deleteExercise(exercises.get(0), funWorkout);

        assertTrue(workoutHandler.getExerciseHeaders(funWorkout).isEmpty());
    }

    @Test
    public void testDeleteExerciseWhenExistent() {
        workoutHandler.addNewWorkout("Fun Workout");
        WorkoutHeader funWorkout = workoutHandler.getWorkoutHeaderByID(0);

        ArrayList<ExerciseHeader> exercises = workoutHandler.getAllExerciseHeaders();
        exercises.get(0).toggleSelected();

        workoutHandler.addSelectedExercisesToWorkout(funWorkout.getId());
        workoutHandler.deleteExercise(workoutHandler.getExerciseHeaders(funWorkout).get(0), funWorkout);

        assertTrue(workoutHandler.getExerciseHeaders(funWorkout).isEmpty());
    }

    @Test
    public void testSavePerformedWorkoutNoSets() {
        Workout workout = new Workout(new WorkoutHeader("fakeworkout", 0));
        workout.addExercise(new Exercise("E1", 1));
        workout.addExercise(new Exercise("E2", 2));
        workout.addExercise(new Exercise("E3", 3));
        workout.addExercise(new Exercise("E4", 4));

        boolean result = workoutHandler.savePerformedWorkout(workout);
        assertFalse(result);
    }

    @Test
    public void testSavePerformedWorkoutNoExercises() {
        Workout workout = new Workout(new WorkoutHeader("fakeworkout", 0));

        boolean result = workoutHandler.savePerformedWorkout(workout);
        assertFalse(result);
    }

    @Test
    public void testSavePerformedWorkoutNoWeight() {
        Workout workout = new Workout(new WorkoutHeader("fakeworkout", 0));
        workout.addExercise(new Exercise("E1", 1));
        workout.addExercise(new Exercise("E2", 2));
        Exercise E3 = new Exercise("E3", 3);
        E3.addSet(new ExerciseSet(0, 5));
        workout.addExercise(E3);
        workout.addExercise(new Exercise("E4", 4));

        boolean result = workoutHandler.savePerformedWorkout(workout);
        assertFalse(result);
    }

    @Test
    public void testSavePerformedWorkoutNoReps() {
        Workout workout = new Workout(new WorkoutHeader("fakeworkout", 0));
        workout.addExercise(new Exercise("E1", 1));
        workout.addExercise(new Exercise("E2", 2));
        Exercise E3 = new Exercise("E3", 3);
        E3.addSet(new ExerciseSet(15, 0));
        workout.addExercise(E3);
        workout.addExercise(new Exercise("E4", 4));

        boolean result = workoutHandler.savePerformedWorkout(workout);
        assertFalse(result);
    }

    @Test
    public void testSavePerformedWorkoutSuccess() {
        Workout workout = new Workout(new PerformedWorkoutHeader("fakeworkout", 0));
        workout.addExercise(new Exercise("E1", 1));
        workout.addExercise(new Exercise("E2", 2));
        Exercise E3 = new Exercise("E3", 3);
        E3.addSet(new ExerciseSet(15, 9));
        workout.addExercise(E3);
        workout.addExercise(new Exercise("E4", 4));

        boolean result = workoutHandler.savePerformedWorkout(workout);
        assertTrue(result);
    }

    @Test
    public void testWorkoutToPerformNotNull() {
        final int ID = 0;
        workoutHandler.addNewWorkout("Fun Workout");
        WorkoutHeader funWorkout = workoutHandler.getWorkoutHeaderByID(ID);

        ArrayList<ExerciseHeader> exercises = workoutHandler.getAllExerciseHeaders();
        exercises.get(0).toggleSelected();

        workoutHandler.addSelectedExercisesToWorkout(funWorkout.getId());

        Workout workout = workoutHandler.getWorkoutToPerform(ID);

        assertNotNull(workout);
    }

    @Test
    public void testWorkoutToPerformCorrectHeader() {
        final int ID = 0;
        workoutHandler.addNewWorkout("Fun Workout");
        WorkoutHeader funWorkout = workoutHandler.getWorkoutHeaderByID(ID);

        ArrayList<ExerciseHeader> exercises = workoutHandler.getAllExerciseHeaders();
        exercises.get(0).toggleSelected();

        workoutHandler.addSelectedExercisesToWorkout(funWorkout.getId());

        WorkoutHeader header = workoutHandler.getWorkoutToPerform(ID).getHeader();

        assertTrue(header instanceof PerformedWorkoutHeader);
    }

    @Test
    public void testGetPerformedWorkoutHeaders() {
        Workout workout = new Workout(new PerformedWorkoutHeader("fakeworkout", 0));
        workout.addExercise(new Exercise("E1", 1));
        workout.addExercise(new Exercise("E2", 2));
        Exercise E3 = new Exercise("E3", 3);
        E3.addSet(new ExerciseSet(15, 9));
        workout.addExercise(E3);
        workout.addExercise(new Exercise("E4", 4));

        workoutHandler.savePerformedWorkout(workout);
        ArrayList<PerformedWorkoutHeader> listOfHeaders = workoutHandler.getPerformedWorkoutHeaders();

        assertEquals(listOfHeaders.size(), 1);
    }

    @Test
    public void testGetPerformedWorkout() {
        Workout workout = new Workout(new PerformedWorkoutHeader("fakeworkout", 0));
        workout.addExercise(new Exercise("E1", 1));
        workout.addExercise(new Exercise("E2", 2));
        Exercise E3 = new Exercise("E3", 3);
        E3.addSet(new ExerciseSet(15, 9));
        workout.addExercise(E3);
        workout.addExercise(new Exercise("E4", 4));

        workoutHandler.savePerformedWorkout(workout);

        Workout result = workoutHandler.getPerformedWorkout(0);

        assertNotNull(result);
    }
}









