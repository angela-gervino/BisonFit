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
            assertNotNull(gymStatusHandler.getGymStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBeforeFirstHours() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockBeforeFirstHours());
            assertEquals("30m Until Opening", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockBeforeFirstHours() {
        return Clock.fixed(Instant.parse("2023-06-26T05:29:30Z"), ZoneOffset.UTC);
    }

    @Test
    public void testBeforeFirstHoursMidnight() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockBeforeFirstHoursMidnight());
            assertEquals("6h 0m Until Opening", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockBeforeFirstHoursMidnight() {
        return Clock.fixed(Instant.parse("2023-06-26T00:00:00Z"), ZoneOffset.UTC);
    }

    @Test
    public void testDuringFirstHours() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockDuringFirstHours());
            assertEquals("5h 30m Until Closing", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockDuringFirstHours() {
        return Clock.fixed(Instant.parse("2023-06-26T06:29:30Z"), ZoneOffset.UTC);
    }

    @Test
    public void testBetweenHours() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockBetweenHours());
            assertEquals("30m Until Opening", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockBetweenHours() {
        return Clock.fixed(Instant.parse("2023-06-26T12:29:30Z"), ZoneOffset.UTC);
    }

    @Test
    public void testDuringLastHours() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockDuringLastHours());
            assertEquals("30m Until Closing", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockDuringLastHours() {
        return Clock.fixed(Instant.parse("2023-06-26T19:29:30Z"), ZoneOffset.UTC);
    }

    @Test
    public void testAfterAllHours() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockAfterAllHours());
            assertEquals("Closed Till Wednesday", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockAfterAllHours() {
        return Clock.fixed(Instant.parse("2023-06-26T20:00:00Z"), ZoneOffset.UTC);
    }

    @Test
    public void testNoHoursToday() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockNoHoursToday());
            assertEquals("17h 30m Until Opening", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockNoHoursToday() {
        return Clock.fixed(Instant.parse("2023-06-27T12:29:30Z"), ZoneOffset.UTC);
    }

    @Test
    public void testClosingTomorrow() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockClosingTomorrow());
            assertEquals("27h 30m Until Closing", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockClosingTomorrow() {
        return Clock.fixed(Instant.parse("2023-06-28T06:29:30Z"), ZoneOffset.UTC);
    }

    @Test
    public void testClosingMidnight() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockClosingMidnight());
            assertEquals("3h 30m Until Closing", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockClosingMidnight() {
        return Clock.fixed(Instant.parse("2023-06-29T20:29:30Z"), ZoneOffset.UTC);
    }

    @Test
    public void testClosingLaterThisWeek() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockClosingLaterThisWeek());
            assertEquals("Open Till Sunday", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockClosingLaterThisWeek() {
        return Clock.fixed(Instant.parse("2023-06-30T06:29:30Z"), ZoneOffset.UTC);
    }
}
