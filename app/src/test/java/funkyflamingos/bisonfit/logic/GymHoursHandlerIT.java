package funkyflamingos.bisonfit.logic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;
import funkyflamingos.bisonfit.persistence.hsqldb.GymHoursPersistenceHSQLDB;
import funkyflamingos.bisonfit.utils.TestUtils;

public class GymHoursHandlerIT {

    private GymHoursHandler gymHoursHandler;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        final IGymHoursPersistence persistence = new GymHoursPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        gymHoursHandler = new GymHoursHandler(persistence);
    }

    @Test
    public void testNotNull() throws Exception {
        String result = gymHoursHandler.getTimeUntilOpenOrClose();
        assertEquals("hi", result);
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }

}
