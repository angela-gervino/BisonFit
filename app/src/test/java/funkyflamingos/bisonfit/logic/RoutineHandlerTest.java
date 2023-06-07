package funkyflamingos.bisonfit.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.persistence.stubs.RoutinesPersistenceStub;

public class RoutineHandlerTest {
    RoutineHandler routineHandler;

    @Before
    public void setup() {
        RoutinesPersistenceStub persistence = new RoutinesPersistenceStub();
        routineHandler = new RoutineHandler(persistence);
    }

    @Test
    public void getByID() {
        Routine foundRoutine = routineHandler.getRoutineByID(1);
        assertTrue(foundRoutine.getHeader().getName().equals("Upper Body"));
    }

    @Test
    public void testNoMatchinID() {
        assertNull(routineHandler.getRoutineByID(100));
    }

    @Test
    public void testGetAllRoutineHeadersNotNull() {
        assertNotNull(routineHandler.getAllRoutineHeaders());
    }
}
