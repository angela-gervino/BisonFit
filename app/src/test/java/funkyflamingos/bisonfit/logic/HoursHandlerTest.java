package funkyflamingos.bisonfit.logic;

import org.junit.Before;
import org.junit.Test;
import java.time.*;

import funkyflamingos.bisonfit.logic.HoursHandler;
import funkyflamingos.bisonfit.persistence.IGymStatusPersistence;
import funkyflamingos.bisonfit.persistence.stubs.GymStatusPersistenceStub;

import static org.junit.Assert.*;

public class HoursHandlerTest {
    HoursHandler hoursHandler;

    @Before
    public void setup()
    {
        IGymStatusPersistence persistence = new GymStatusPersistenceStub();
        hoursHandler = new HoursHandler(persistence);
    }

    @Test
    public void testGymIsOpen()
    {
        String output = hoursHandler.getGymStatus(getClockSetToAfternoon());
        assertEquals("9h 29m Until Closing", output);
    }

    @Test
    public void testGymIsClosed()
    {
        String result = hoursHandler.getGymStatus(getClockSetToPastMidight());
        assertEquals("4h 33m Until Opening", result);
    }

    @Test
    public void testGymIsClosed2()
    {
        String result = hoursHandler.getGymStatus(getClockSetToBeforeMidnight());
        assertEquals("6h 33m Until Opening", result);
    }

    @Test
    public void testAtClosingTime()
    {
        String result = hoursHandler.getGymStatus(getClockSetToGymCloseTime());
        assertEquals("8h 0m Until Opening", result);
    }

    @Test
    public void testAtOpeningTime()
    {
        String result = hoursHandler.getGymStatus(getClockSetToGymOpenTime());
        assertEquals("16h 0m Until Closing", result);
    }

    @Test
    public void testGymAlmostClosed()
    {
        String result = hoursHandler.getGymStatus(getClockSetToGymAlmostClosed());
        assertEquals("<1 Minute Until Closing", result);
    }

    @Test
    public void testGymAlmostOpened()
    {
        String result = hoursHandler.getGymStatus(getClockSetToGymAlmostOpened());
        assertEquals("<1 Minute Until Opening", result);
    }

    public Clock getClockSetToAfternoon()
    {
        return Clock.fixed(Instant.parse("2018-08-22T12:30:30Z"), ZoneOffset.UTC);
    }

    public Clock getClockSetToPastMidight()
    {
        return Clock.fixed(Instant.parse("2023-06-07T01:26:27Z"), ZoneOffset.UTC);
    }

    public Clock getClockSetToBeforeMidnight()
    {
        return Clock.fixed(Instant.parse("2023-06-07T23:26:27Z"), ZoneOffset.UTC);
    }

    public Clock getClockSetToGymCloseTime()
    {
        return Clock.fixed(Instant.parse("2023-06-08T22:00:00Z"), ZoneOffset.UTC);
    }

    public Clock getClockSetToGymOpenTime()
    {
        return Clock.fixed(Instant.parse("2023-06-08T06:00:00Z"), ZoneOffset.UTC);
    }

    public Clock getClockSetToGymAlmostClosed()
    {
        return Clock.fixed(Instant.parse("2023-06-06T21:59:01Z"), ZoneOffset.UTC);
    }

    public Clock getClockSetToGymAlmostOpened()
    {
        return Clock.fixed(Instant.parse("2023-06-09T05:59:22Z"), ZoneOffset.UTC);
    }
}
