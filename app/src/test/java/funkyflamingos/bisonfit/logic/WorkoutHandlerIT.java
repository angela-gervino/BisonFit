package funkyflamingos.bisonfit.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;
import funkyflamingos.bisonfit.persistence.IExerciseLookupPersistence;
import funkyflamingos.bisonfit.persistence.IWorkoutPersistence;
import funkyflamingos.bisonfit.persistence.ISavedWorkoutExercises;
import funkyflamingos.bisonfit.persistence.hsqldb.ExerciseLookupPersistenceHSQLDB;
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

        workoutHandler = new WorkoutHandler(workoutsPersistenceStub, savedWorkoutExercisesPersistenceStub, exerciseLookupPersistenceStub);
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }

    @Test
    public void testGetByIDFound() {
        workoutHandler.addNewWorkout("My New Workout Workout");
        assertNotNull(workoutHandler.getWorkoutByID(0));
    }

    @Test
    public void testGetByIDNotFound() {
        assertNull(workoutHandler.getWorkoutByID(0));
    }

    @Test
    public void testExpectedName() {
        workoutHandler.addNewWorkout("Upper Body");
        Workout foundWorkout = workoutHandler.getWorkoutByID(0);
        assertTrue(foundWorkout.getHeader().getName().equals("Upper Body"));
    }

    @Test
    public void testGetByIDHeaderNotNull() {
        workoutHandler.addNewWorkout("Upper Body");
        assertNotNull(workoutHandler.getWorkoutByID(0).getHeader());
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
        WorkoutHeader funWorkout = workoutHandler.getWorkoutByID(0).getHeader();

        ArrayList<ExerciseHeader> exercises = workoutHandler.getAllExerciseHeaders();
        workoutHandler.deleteExercise(exercises.get(0), funWorkout);

        assertTrue(workoutHandler.getExerciseHeaders(funWorkout).isEmpty());
    }

    @Test
    public void testDeleteExerciseWhenExistent() {
        workoutHandler.addNewWorkout("Fun Workout");
        WorkoutHeader funWorkout = workoutHandler.getWorkoutByID(0).getHeader();

        ArrayList<ExerciseHeader> exercises = workoutHandler.getAllExerciseHeaders();
        exercises.get(0).toggleSelected();

        workoutHandler.addSelectedExercisesToWorkout(funWorkout.getId());
        workoutHandler.deleteExercise(workoutHandler.getExerciseHeaders(funWorkout).get(0), funWorkout);

        assertTrue(workoutHandler.getExerciseHeaders(funWorkout).isEmpty());
    }
}









