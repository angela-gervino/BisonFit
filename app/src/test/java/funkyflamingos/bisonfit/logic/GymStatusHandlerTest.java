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
//
//    @Test
//    public void testGetGymStatus() {
//        assertNotNull(gymStatusHandler.getGymStatus());
//    }
//
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
//    public Clock getClockSetToAfternoon() {
//        return Clock.fixed(Instant.parse("2018-08-22T12:30:30Z"), ZoneOffset.UTC);
//    }
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
