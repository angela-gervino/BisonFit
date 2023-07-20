package funkyflamingos.bisonfit.logic;

import org.junit.Before;
import org.junit.Test;

import java.time.*;
import java.util.List;

import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;
import funkyflamingos.bisonfit.persistence.stubs.GymHoursPersistenceStub;

import static org.junit.Assert.*;

public class GymHoursHandlerTest {
    GymHoursHandler gymHoursHandler;

    @Before
    public void setup() {
        IGymHoursPersistence persistence = new GymHoursPersistenceStub();
        gymHoursHandler = new GymHoursHandler(persistence);
    }

    @Test
    public void testNotNull() {
        try {
            assertNotNull(gymHoursHandler.getTimeUntilOpenOrClose());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBeforeFirstHours() {
        try {
            String output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockBeforeFirstHours());
            assertEquals("30m Until Opening", output);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBeforeFirstHoursMidnight() {
        try {
            String output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockBeforeFirstHoursMidnight());
            assertEquals("6h 0m Until Opening", output);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDuringFirstHours() {
        try {
            String output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockDuringFirstHours());
            assertEquals("5h 30m Until Closing", output);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBetweenHours() {
        try {
            String output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockBetweenHours());
            assertEquals("30m Until Opening", output);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDuringLastHours() {
        try {
            String output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockDuringLastHours());
            assertEquals("30m Until Closing", output);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAfterAllHours() {
        try {
            String output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockAfterAllHours());
            assertEquals("Closed Till Wednesday", output);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testNoHoursToday() {
        try {
            String output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockNoHoursToday());
            assertEquals("17h 30m Until Opening", output);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testClosingTomorrow() {
        try {
            String output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockClosingTomorrow());
            assertEquals("27h 30m Until Closing", output);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testClosingMidnight() {
        try {
            String output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockClosingMidnight());
            assertEquals("3h 30m Until Closing", output);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testClosingLaterThisWeek() {
        try {
            String output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockClosingLaterThisWeek());
            assertEquals("Open Till Sunday", output);
        } catch (Exception e) {
            fail(e.getMessage());
        }
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

    private Clock getClockBeforeFirstHours() {
        return Clock.fixed(Instant.parse("2023-06-26T05:29:30Z"), ZoneOffset.UTC);
    }

    private Clock getClockBeforeFirstHoursMidnight() {
        return Clock.fixed(Instant.parse("2023-06-26T00:00:00Z"), ZoneOffset.UTC);
    }

    private Clock getClockDuringFirstHours() {
        return Clock.fixed(Instant.parse("2023-06-26T06:29:30Z"), ZoneOffset.UTC);
    }

    private Clock getClockBetweenHours() {
        return Clock.fixed(Instant.parse("2023-06-26T12:29:30Z"), ZoneOffset.UTC);
    }

    private Clock getClockDuringLastHours() {
        return Clock.fixed(Instant.parse("2023-06-26T19:29:30Z"), ZoneOffset.UTC);
    }

    private Clock getClockAfterAllHours() {
        return Clock.fixed(Instant.parse("2023-06-26T20:00:00Z"), ZoneOffset.UTC);
    }

    private Clock getClockNoHoursToday() {
        return Clock.fixed(Instant.parse("2023-06-27T12:29:30Z"), ZoneOffset.UTC);
    }

    private Clock getClockClosingTomorrow() {
        return Clock.fixed(Instant.parse("2023-06-28T06:29:30Z"), ZoneOffset.UTC);
    }

    private Clock getClockClosingMidnight() {
        return Clock.fixed(Instant.parse("2023-06-29T20:29:30Z"), ZoneOffset.UTC);
    }

    private Clock getClockClosingLaterThisWeek() {
        return Clock.fixed(Instant.parse("2023-06-30T06:29:30Z"), ZoneOffset.UTC);
    }
}
