package funkyflamingos.bisonfit;

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
        IGymStatusPersistence database = new GymStatusPersistenceStub();
        hoursHandler = new HoursHandler(database);
    }

    @Test
    public void testGymIsOpen()
    {
        String output = hoursHandler.getGymStatus(getClockSetToAfternoon());
        assertEquals("9h 29m 30s Until Closing", output);
    }

    @Test
    public void testGymIsClosed()
    {
        String result = hoursHandler.getGymStatus(getClockSetToPastMidight());
        assertEquals("4h 33m 33s Until Opening", result);
    }

    @Test
    public void testGymIsClosed2()
    {
        String result = hoursHandler.getGymStatus(getClockSetToBeforeMidnight());
        assertEquals("6h 33m 33s Until Opening", result);
    }

    @Test
    public void testAtClosingTime()
    {
        String result = hoursHandler.getGymStatus(getClockSetToGymCloseTime());
        assertEquals("8h 0m 0s Until Opening", result);
    }

    @Test
    public void testAtOpeningTime()
    {
        String result = hoursHandler.getGymStatus(getClockSetToGymOpenTime());
        assertEquals("16h 0m 0s Until Closing", result);
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
}
