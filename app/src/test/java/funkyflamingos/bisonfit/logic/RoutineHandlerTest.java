package funkyflamingos.bisonfit.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;

public class RoutineHandlerTest {
    RoutineHandler routineHandler;

    @Before
    public void setup() {
        IRoutinesPersistence persistence = Services.getRoutinesPersistence(false);
        routineHandler = new RoutineHandler(persistence);
    }

    @Test
    public void testGetByIDFound() {
        assertNotNull(routineHandler.getRoutineByID(1));
    }

    @Test
    public void testGetByIDNotFound() {
        assertNull(routineHandler.getRoutineByID(100));
    }

    @Test
    public void testExpectedName() {
        Routine foundRoutine = routineHandler.getRoutineByID(1);
        assertTrue(foundRoutine.getHeader().getName().equals("Upper Body"));
    }

    @Test
    public void testGetByIDHeaderNotNull() {
        assertNotNull(routineHandler.getRoutineByID(1).getHeader());
    }

    @Test
    public void testGetAllRoutineHeadersNotNull() {
        assertNotNull(routineHandler.getAllRoutineHeaders());
    }

    @Test
    public void testGetAllRoutineReturnsList() {
        assertTrue(routineHandler.getAllRoutineHeaders() instanceof List);
    }

}
