package funkyflamingos.bisonfit.logic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.dso.TimeUntilOpenOrClose;
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
    public void testGetGymSchedule() {
        try {
            List<GymHours> output = gymHoursHandler.getGymSchedule();
            assertNotNull("getGymSchedule should return a non-null object.", output);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGymTimeNotNull() {
        try {
            assertNotNull("Should not return null.", gymHoursHandler.getTimeUntilOpenOrClose());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBeforeOpening() {
        try {
            TimeUntilOpenOrClose output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockBeforeOpening());
            assertFalse(output.isOpen());
            assertEquals(Duration.ofMinutes(30), output.getTimeUntilOpenOrClose().truncatedTo(ChronoUnit.MINUTES));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAfterOpening() {
        try {
            TimeUntilOpenOrClose output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockAfterOpening());
            assertTrue(output.isOpen());
            assertEquals(Duration.ofHours(8).plusMinutes(30), output.getTimeUntilOpenOrClose().truncatedTo(ChronoUnit.MINUTES));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAfterClosing() {
        try {
            TimeUntilOpenOrClose output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockAfterClosing());
            assertFalse(output.isOpen());
            assertEquals(Duration.ofHours(6).plusMinutes(30), output.getTimeUntilOpenOrClose().truncatedTo(ChronoUnit.MINUTES));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


    private Clock getClockBeforeOpening() {
        return Clock.fixed(Instant.parse("2023-06-25T07:29:30Z"), ZoneOffset.UTC);
    }

    private Clock getClockAfterOpening() {
        return Clock.fixed(Instant.parse("2023-06-25T11:29:30Z"), ZoneOffset.UTC);

    }

    private Clock getClockAfterClosing() {
        return Clock.fixed(Instant.parse("2023-06-25T23:29:30Z"), ZoneOffset.UTC);
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }

}
