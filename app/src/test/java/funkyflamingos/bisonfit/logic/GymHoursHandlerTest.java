package funkyflamingos.bisonfit.logic;

import org.junit.Before;
import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.dso.TimeUntilOpenOrClose;
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;
import funkyflamingos.bisonfit.persistence.stubs.GymHoursPersistenceStub;

import static org.junit.Assert.*;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.WEDNESDAY;

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
            TimeUntilOpenOrClose output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockBeforeFirstHours());
            assertFalse(output.isOpen());
            assertEquals(Duration.ofMinutes(30), output.getTimeUntilOpenOrClose().truncatedTo(ChronoUnit.MINUTES));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBeforeFirstHoursMidnight() {
        try {
            TimeUntilOpenOrClose output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockBeforeFirstHoursMidnight());
            assertFalse(output.isOpen());
            assertEquals(Duration.ofHours(6), output.getTimeUntilOpenOrClose().truncatedTo(ChronoUnit.MINUTES));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDuringFirstHours() {
        try {
            TimeUntilOpenOrClose output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockDuringFirstHours());
            assertTrue(output.isOpen());
            assertEquals(Duration.ofHours(5).plusMinutes(30), output.getTimeUntilOpenOrClose().truncatedTo(ChronoUnit.MINUTES));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testBetweenHours() {
        try {
            TimeUntilOpenOrClose output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockBetweenHours());
            assertFalse(output.isOpen());
            assertEquals(Duration.ofMinutes(30), output.getTimeUntilOpenOrClose().truncatedTo(ChronoUnit.MINUTES));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDuringLastHours() {
        try {
            TimeUntilOpenOrClose output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockDuringLastHours());
            assertTrue(output.isOpen());
            assertEquals(Duration.ofMinutes(30), output.getTimeUntilOpenOrClose().truncatedTo(ChronoUnit.MINUTES));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAfterAllHours() {
        try {
            TimeUntilOpenOrClose output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockAfterAllHours());
            assertFalse(output.isOpen());
            assertNull(output.getTimeUntilOpenOrClose());
            assertEquals(WEDNESDAY.getValue(), output.getNextDay());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testNoHoursToday() {
        try {
            TimeUntilOpenOrClose output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockNoHoursToday());
            assertFalse(output.isOpen());
            assertEquals(Duration.ofHours(17).plusMinutes(30), output.getTimeUntilOpenOrClose().truncatedTo(ChronoUnit.MINUTES));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testClosingTomorrow() {
        try {
            TimeUntilOpenOrClose output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockClosingTomorrow());
            assertTrue(output.isOpen());
            assertEquals(Duration.ofHours(27).plusMinutes(30), output.getTimeUntilOpenOrClose().truncatedTo(ChronoUnit.MINUTES));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testClosingMidnight() {
        try {
            TimeUntilOpenOrClose output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockClosingMidnight());
            assertTrue(output.isOpen());
            assertEquals(Duration.ofHours(3).plusMinutes(30), output.getTimeUntilOpenOrClose().truncatedTo(ChronoUnit.MINUTES));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testClosingLaterThisWeek() {
        try {
            TimeUntilOpenOrClose output = gymHoursHandler.getTimeUntilOpenOrCloseHelper(getClockClosingLaterThisWeek());
            assertTrue(output.isOpen());
            assertNull(output.getTimeUntilOpenOrClose());
            assertEquals(SUNDAY.getValue(), output.getNextDay());
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
