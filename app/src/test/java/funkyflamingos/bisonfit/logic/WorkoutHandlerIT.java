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
    private WorkoutHandler routineHandler;

    private File tempDB;

    @Before
    public void setup() throws Exception {
        tempDB = TestUtils.copyDB();

        String dbPathName = this.tempDB.getAbsolutePath().replace(".script", "");

        final IWorkoutPersistence routinesPersistenceStub = new WorkoutPersistenceHSQLDB(dbPathName);
        final ISavedWorkoutExercises savedRoutineExercisesPersistenceStub = new SavedWorkoutExercisesPersistenceHSQLDB(dbPathName);
        final IExerciseLookupPersistence exerciseLookupPersistenceStub = new ExerciseLookupPersistenceHSQLDB(dbPathName);

        routineHandler = new WorkoutHandler(routinesPersistenceStub, savedRoutineExercisesPersistenceStub, exerciseLookupPersistenceStub);
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }

    @Test
    public void testGetByIDFound() {
        routineHandler.addNewRoutine("My New Workout Routine");
        assertNotNull(routineHandler.getRoutineByID(0));
    }

    @Test
    public void testGetByIDNotFound() {
        assertNull(routineHandler.getRoutineByID(0));
    }

    @Test
    public void testExpectedName() {
        routineHandler.addNewRoutine("Upper Body");
        Workout foundRoutine = routineHandler.getRoutineByID(0);
        assertTrue(foundRoutine.getHeader().getName().equals("Upper Body"));
    }

    @Test
    public void testGetByIDHeaderNotNull() {
        routineHandler.addNewRoutine("Upper Body");
        assertNotNull(routineHandler.getRoutineByID(0).getHeader());
    }

    @Test
    public void testGetAllRoutineHeadersNotNull() {
        assertNotNull(routineHandler.getAllRoutineHeaders());
    }

    @Test
    public void testGetAllRoutineReturnsList() {
        assertTrue(routineHandler.getAllRoutineHeaders() instanceof List);
    }

    @Test
    public void testGetAllExerciseHeadersNotNull() {
        assertNotNull(routineHandler.getAllExerciseHeaders());
    }

    @Test
    public void testGetAllExerciseReturnsArrayList() {
        assertTrue(routineHandler.getAllExerciseHeaders() instanceof ArrayList);
    }

    @Test
    public void testGetAllSelectedExercisesEmpty() {
        assertTrue(routineHandler.getAllSelectedExercises().isEmpty());
    }

    @Test
    public void testGetAllSelectedExercisesNonEmpty() {
        ArrayList<ExerciseHeader> exercises = routineHandler.getAllExerciseHeaders();
        exercises.forEach(exercise -> {
            if (!exercise.isSelected())
                exercise.toggleSelected();
        });

        assertFalse(routineHandler.getAllSelectedExercises().isEmpty());
    }

    @Test
    public void testUnselectAllExercisesWhenNoneSelected() {
        routineHandler.unselectAllExercises();
        assertTrue(routineHandler.getAllSelectedExercises().isEmpty());
    }

    @Test
    public void testUnselectAllExercisesAllSelected() {
        ArrayList<ExerciseHeader> exercises = routineHandler.getAllExerciseHeaders();
        exercises.forEach(exercise -> {
            if (!exercise.isSelected())
                exercise.toggleSelected();
        });

        routineHandler.unselectAllExercises();
        assertTrue(routineHandler.getAllSelectedExercises().isEmpty());
    }

    @Test
    public void testAddNewRoutine() {
        routineHandler.addNewRoutine("First Workout Routine!");
        routineHandler.addNewRoutine("Second Workout Routine!");
        routineHandler.addNewRoutine("Third Workout Routine!");

        assert(routineHandler.getAllRoutineHeaders().size() == 3);
    }

    @Test
    public void testAddSelectedExercisesToRoutineWhenAllSelected() {
        routineHandler.addNewRoutine("First Workout Routine!");
        WorkoutHeader firstWorkout = routineHandler.getAllRoutineHeaders().get(0);

        ArrayList<ExerciseHeader> exercises = routineHandler.getAllExerciseHeaders();
        exercises.forEach(exercise -> {
            if (!exercise.isSelected())
                exercise.toggleSelected();
        });

        routineHandler.addSelectedExercisesToRoutine(firstWorkout);

        assertEquals(exercises.size(), routineHandler.getExerciseHeaders(firstWorkout).size());
    }

    @Test
    public void testAddSelectedExercisesToRoutineWhenNoneSelected() {
        routineHandler.addNewRoutine("First Workout Routine!");
        WorkoutHeader firstWorkout = routineHandler.getAllRoutineHeaders().get(0);

        routineHandler.addSelectedExercisesToRoutine(firstWorkout);

        assertEquals(0, routineHandler.getExerciseHeaders(firstWorkout).size());
    }

    @Test
    public void testGetExerciseHeadersWhenEmpty() {
        routineHandler.addNewRoutine("First Workout Routine!");
        WorkoutHeader firstWorkout = routineHandler.getAllRoutineHeaders().get(0);

        assertTrue(routineHandler.getExerciseHeaders(firstWorkout).isEmpty());
    }

    @Test
    public void testGetExerciseHeadersWhenNotEmpty() {
        routineHandler.addNewRoutine("First Workout Routine!");
        WorkoutHeader firstWorkout = routineHandler.getAllRoutineHeaders().get(0);

        ArrayList<ExerciseHeader> exercises = routineHandler.getAllExerciseHeaders();
        exercises.get(0).toggleSelected();

        routineHandler.addSelectedExercisesToRoutine(firstWorkout);

        assertEquals(1, routineHandler.getExerciseHeaders(firstWorkout).size());
    }

    @Test
    public void testDeleteRoutineWhenNonexistent() {
        routineHandler.deleteRoutine(0);

        assertTrue(routineHandler.getAllRoutineHeaders().isEmpty());
    }

    @Test
    public void testDeleteRoutineWhenExists() {
        routineHandler.addNewRoutine("Upper Body Workout");
        routineHandler.deleteRoutine(0);

        assertTrue(routineHandler.getAllRoutineHeaders().isEmpty());
    }

    @Test
    public void testDeleteExerciseWhenNonexistent() {
        routineHandler.addNewRoutine("Fun Workout");
        WorkoutHeader funWorkout = routineHandler.getRoutineByID(0).getHeader();

        ArrayList<ExerciseHeader> exercises = routineHandler.getAllExerciseHeaders();
        routineHandler.deleteExercise(exercises.get(0), funWorkout);

        assertTrue(routineHandler.getExerciseHeaders(funWorkout).isEmpty());
    }

    @Test
    public void testDeleteExerciseWhenExistent() {
        routineHandler.addNewRoutine("Fun Workout");
        WorkoutHeader funWorkout = routineHandler.getRoutineByID(0).getHeader();

        ArrayList<ExerciseHeader> exercises = routineHandler.getAllExerciseHeaders();
        exercises.get(0).toggleSelected();

        routineHandler.addSelectedExercisesToRoutine(funWorkout);
        routineHandler.deleteExercise(routineHandler.getExerciseHeaders(funWorkout).get(0), funWorkout);

        assertTrue(routineHandler.getExerciseHeaders(funWorkout).isEmpty());
    }
}









