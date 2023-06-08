package funkyflamingos.bisonfit.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import funkyflamingos.bisonfit.persistence.stubs.WaterTrackerPersistenceStub;

public class WaterHandlerTest {
    private WaterHandler waterHandler;

    @Before
    public void setup() {
        WaterTrackerPersistenceStub persistence = new WaterTrackerPersistenceStub();
        waterHandler = new WaterHandler(persistence);
    }

    @Test
    public void testFirstIncrement() {
        waterHandler.increment();
        assertEquals(1, waterHandler.getProgress());
    }

    @Test
    public void testMiddleIncrements() {
        initializeNonEmptyWaterHandler();
        for (int i = 2; i < waterHandler.getGoal(); i++) {
            waterHandler.increment();
            assertEquals(i, waterHandler.getProgress());
        }
    }

    @Test
    public void testLastIncrement() {
        initializeAlmostFullWaterHandler();
        waterHandler.increment();
        assertEquals(8, waterHandler.getProgress());
    }

    @Test
    public void testIncrementAttemptWhenFull() {
        initializeFullWaterHandler();
        waterHandler.increment();
        assertEquals(8, waterHandler.getProgress());
    }

    @Test
    public void testGetGoal() {
        assertEquals(8, waterHandler.getGoal());
    }

    @Test
    public void testNoProgress() {
        assertEquals(0, waterHandler.getProgress());
    }

    @Test
    public void testCompletedProgress() {
        initializeFullWaterHandler();
        assertEquals(8, waterHandler.getProgress());
    }

    @Test
    public void testPartiallyCompletedProgress() {
        initializeNonEmptyWaterHandler();
        for (int i = 2; i < waterHandler.getGoal(); i++) {
            waterHandler.increment();
            assertEquals(i, waterHandler.getProgress());
        }
    }

    @Test
    public void testReachedGoalWhenTrue() {
        initializeFullWaterHandler();
        assertTrue(waterHandler.reachedGoal());
    }

    @Test
    public void testReachedGoalWhenFalse() {
        initializeAlmostFullWaterHandler();
        assertFalse(waterHandler.reachedGoal());
    }

    private void initializeNonEmptyWaterHandler() {
        waterHandler.increment();
    }

    private void initializeAlmostFullWaterHandler() {
        for (int i = 0; i < waterHandler.getGoal() - 1; i++)
            waterHandler.increment();
    }

    private void initializeFullWaterHandler() {
        for (int i = 0; i < waterHandler.getGoal(); i++)
            waterHandler.increment();
    }
}
