package funkyflamingos.bisonfit.logic;

import org.junit.Before;
import org.junit.Test;

import java.time.*;

import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;
import funkyflamingos.bisonfit.persistence.stubs.GymHoursPersistenceStub;

import static org.junit.Assert.*;

public class GymStatusHandlerTest {
    GymHoursHandler gymStatusHandler;

    @Before
    public void setup() {
        IGymHoursPersistence persistence = new GymHoursPersistenceStub();
        gymStatusHandler = new GymHoursHandler(persistence);
    }

    @Test
    public void testNotNull() {
        try {
            assertNotNull(gymStatusHandler.getTimeUntilOpenOrClose());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBeforeFirstHours() {
        try {
            String output = gymStatusHandler.getTimeUntilOpenOrCloseHelper(getClockBeforeFirstHours());
            assertEquals("30m Until Opening", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBeforeFirstHoursMidnight() {
        try {
            String output = gymStatusHandler.getTimeUntilOpenOrCloseHelper(getClockBeforeFirstHoursMidnight());
            assertEquals("6h 0m Until Opening", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDuringFirstHours() {
        try {
            String output = gymStatusHandler.getTimeUntilOpenOrCloseHelper(getClockDuringFirstHours());
            assertEquals("5h 30m Until Closing", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBetweenHours() {
        try {
            String output = gymStatusHandler.getTimeUntilOpenOrCloseHelper(getClockBetweenHours());
            assertEquals("30m Until Opening", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDuringLastHours() {
        try {
            String output = gymStatusHandler.getTimeUntilOpenOrCloseHelper(getClockDuringLastHours());
            assertEquals("30m Until Closing", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAfterAllHours() {
        try {
            String output = gymStatusHandler.getTimeUntilOpenOrCloseHelper(getClockAfterAllHours());
            assertEquals("Closed Till Wednesday", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testNoHoursToday() {
        try {
            String output = gymStatusHandler.getTimeUntilOpenOrCloseHelper(getClockNoHoursToday());
            assertEquals("17h 30m Until Opening", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testClosingTomorrow() {
        try {
            String output = gymStatusHandler.getTimeUntilOpenOrCloseHelper(getClockClosingTomorrow());
            assertEquals("27h 30m Until Closing", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testClosingMidnight() {
        try {
            String output = gymStatusHandler.getTimeUntilOpenOrCloseHelper(getClockClosingMidnight());
            assertEquals("3h 30m Until Closing", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testClosingLaterThisWeek() {
        try {
            String output = gymStatusHandler.getTimeUntilOpenOrCloseHelper(getClockClosingLaterThisWeek());
            assertEquals("Open Till Sunday", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetGymSchedule() {
        try {
            String output = gymStatusHandler.getGymSchedule();
            assertNotNull("getGymSchedule should return a non-null object.", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockBeforeFirstHours() {
        return Clock.fixed(Instant.parse("2023-06-26T05:29:30Z"), ZoneOffset.UTC);
    }

    public Clock getClockBeforeFirstHoursMidnight() {
        return Clock.fixed(Instant.parse("2023-06-26T00:00:00Z"), ZoneOffset.UTC);
    }

    public Clock getClockDuringFirstHours() {
        return Clock.fixed(Instant.parse("2023-06-26T06:29:30Z"), ZoneOffset.UTC);
    }

    public Clock getClockBetweenHours() {
        return Clock.fixed(Instant.parse("2023-06-26T12:29:30Z"), ZoneOffset.UTC);
    }

    public Clock getClockDuringLastHours() {
        return Clock.fixed(Instant.parse("2023-06-26T19:29:30Z"), ZoneOffset.UTC);
    }

    public Clock getClockAfterAllHours() {
        return Clock.fixed(Instant.parse("2023-06-26T20:00:00Z"), ZoneOffset.UTC);
    }

    public Clock getClockNoHoursToday() {
        return Clock.fixed(Instant.parse("2023-06-27T12:29:30Z"), ZoneOffset.UTC);
    }

    public Clock getClockClosingTomorrow() {
        return Clock.fixed(Instant.parse("2023-06-28T06:29:30Z"), ZoneOffset.UTC);
    }

    public Clock getClockClosingMidnight() {
        return Clock.fixed(Instant.parse("2023-06-29T20:29:30Z"), ZoneOffset.UTC);
    }

    public Clock getClockClosingLaterThisWeek() {
        return Clock.fixed(Instant.parse("2023-06-30T06:29:30Z"), ZoneOffset.UTC);
    }
}
