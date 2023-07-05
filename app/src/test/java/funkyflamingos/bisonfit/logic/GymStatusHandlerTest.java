package funkyflamingos.bisonfit.logic;

import org.junit.Before;
import org.junit.Test;

import java.time.*;

import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;
import funkyflamingos.bisonfit.persistence.stubs.GymHoursPersistenceStub;

import static org.junit.Assert.*;

public class GymStatusHandlerTest {
    GymStatusHandler gymStatusHandler;

    @Before
    public void setup() {
        IGymHoursPersistence persistence = new GymHoursPersistenceStub();
        gymStatusHandler = new GymStatusHandler(persistence);
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
    public void testBeforeFirstHoursMonday() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockBeforeFirstHoursMonday());
            assertEquals("30m Until Opening", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockBeforeFirstHoursMonday() {
        return Clock.fixed(Instant.parse("2023-06-26T05:29:30Z"), ZoneOffset.UTC);
    }
    @Test
    public void testBeforeFirstHoursMondayMidnight() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockBeforeFirstHoursMondayMidnight());
            assertEquals("6h 0m Until Opening", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockBeforeFirstHoursMondayMidnight() {
        return Clock.fixed(Instant.parse("2023-06-26T00:00:00Z"), ZoneOffset.UTC);
    }
    @Test
    public void testDuringFirstHoursMonday() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockDuringFirstHoursMonday());
            assertEquals("5h 30m Until Closing", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockDuringFirstHoursMonday() {
        return Clock.fixed(Instant.parse("2023-06-26T06:29:30Z"), ZoneOffset.UTC);
    }

    @Test
    public void testBetweenHoursMonday() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockBetweenHoursMonday());
            assertEquals("30m Until Opening", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockBetweenHoursMonday() {
        return Clock.fixed(Instant.parse("2023-06-26T12:29:30Z"), ZoneOffset.UTC);
    }

    @Test
    public void testDuringLastHoursMonday() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockDuringLastHoursMonday());
            assertEquals("30m Until Closing", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockDuringLastHoursMonday() {
        return Clock.fixed(Instant.parse("2023-06-26T19:29:30Z"), ZoneOffset.UTC);
    }

    @Test
    public void testAfterAllHoursMonday() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockAfterAllHoursMonday());
            assertEquals("Closed Till Wednesday", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockAfterAllHoursMonday() {
        return Clock.fixed(Instant.parse("2023-06-26T20:00:00Z"), ZoneOffset.UTC);
    }

    @Test
    public void testNoHoursTodayTuesday() {
        try {
            String output = gymStatusHandler.getGymStatus(getClockNoHoursTodayTuesday());
            assertEquals("17h 30m Until Opening", output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Clock getClockNoHoursTodayTuesday() {
        return Clock.fixed(Instant.parse("2023-06-27T12:29:30Z"), ZoneOffset.UTC);
    }
//    @Test
//    public void testOpenNowFirstHours() {
//        try {
//            String output = gymStatusHandler.getGymStatus(getClockSetToMondayFirstHours());
//            assertEquals("5h 30m Until Closing", output);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Clock getClockSetToMondayFirstHours() {
//        return Clock.fixed(Instant.parse("2023-06-26T06:29:30Z"), ZoneOffset.UTC);
//    }



    /**
     * currentTime == noon
     *  opens today
     *  opens today between hours
     *  opens tomorrow
     *  opens another day
     *  closes today
     *  closes today between hours
     *  closes tomorrow
     *  closes another day
     * currentTime == midnight
     */

//    @Test
//    public void testGymIsOpen() {
//        String output = gymStatusHandler.getGymStatus(getClockSetToAfternoon());
//        assertEquals("9h 29m Until Closing", output);
//    }
//
//    @Test
//    public void testGymIsClosed() {
//        String result = gymStatusHandler.getGymStatus(getClockSetToPastMidight());
//        assertEquals("4h 33m Until Opening", result);
//    }
//
//    @Test
//    public void testGymIsClosed2() {
//        String result = gymStatusHandler.getGymStatus(getClockSetToBeforeMidnight());
//        assertEquals("6h 33m Until Opening", result);
//    }
//
//    @Test
//    public void testAtClosingTime() {
//        String result = gymStatusHandler.getGymStatus(getClockSetToGymCloseTime());
//        assertEquals("8h 0m Until Opening", result);
//    }
//
//    @Test
//    public void testAtOpeningTime() {
//        String result = gymStatusHandler.getGymStatus(getClockSetToGymOpenTime());
//        assertEquals("16h 0m Until Closing", result);
//    }
//
//    @Test
//    public void testGymAlmostClosed() {
//        String result = gymStatusHandler.getGymStatus(getClockSetToGymAlmostClosed());
//        assertEquals("<1 Minute Until Closing", result);
//    }
//
//    @Test
//    public void testGymAlmostOpened() {
//        String result = gymStatusHandler.getGymStatus(getClockSetToGymAlmostOpened());
//        assertEquals("<1 Minute Until Opening", result);
//    }
//

//
//    public Clock getClockSetToPastMidight() {
//        return Clock.fixed(Instant.parse("2023-06-07T01:26:27Z"), ZoneOffset.UTC);
//    }
//
//    public Clock getClockSetToBeforeMidnight() {
//        return Clock.fixed(Instant.parse("2023-06-07T23:26:27Z"), ZoneOffset.UTC);
//    }
//
//    public Clock getClockSetToGymCloseTime() {
//        return Clock.fixed(Instant.parse("2023-06-08T22:00:00Z"), ZoneOffset.UTC);
//    }
//
//    public Clock getClockSetToGymOpenTime() {
//        return Clock.fixed(Instant.parse("2023-06-08T06:00:00Z"), ZoneOffset.UTC);
//    }
//
//    public Clock getClockSetToGymAlmostClosed() {
//        return Clock.fixed(Instant.parse("2023-06-06T21:59:01Z"), ZoneOffset.UTC);
//    }
//
//    public Clock getClockSetToGymAlmostOpened() {
//        return Clock.fixed(Instant.parse("2023-06-09T05:59:22Z"), ZoneOffset.UTC);
//    }
}
