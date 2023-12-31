package funkyflamingos.bisonfit.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import static funkyflamingos.bisonfit.logic.WaterHandlerTestHelper.initializeAlmostFullWaterHandler;
import static funkyflamingos.bisonfit.logic.WaterHandlerTestHelper.initializeFullWaterHandler;
import static funkyflamingos.bisonfit.logic.WaterHandlerTestHelper.initializeNonEmptyWaterHandler;

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
        initializeNonEmptyWaterHandler(waterHandler);
        for (int i = 2; i < waterHandler.getGoal(); i++) {
            waterHandler.increment();
            assertEquals(i, waterHandler.getProgress());
        }
    }

    @Test
    public void testLastIncrement() {
        initializeAlmostFullWaterHandler(waterHandler);
        waterHandler.increment();
        assertEquals(8, waterHandler.getProgress());
    }

    @Test
    public void testIncrementAttemptWhenFull() {
        initializeFullWaterHandler(waterHandler);
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
        initializeFullWaterHandler(waterHandler);
        assertEquals(8, waterHandler.getProgress());
    }

    @Test
    public void testPartiallyCompletedProgress() {
        initializeNonEmptyWaterHandler(waterHandler);
        for (int i = 2; i < waterHandler.getGoal(); i++) {
            waterHandler.increment();
            assertEquals(i, waterHandler.getProgress());
        }
    }

    @Test
    public void testReachedGoalWhenTrue() {
        initializeFullWaterHandler(waterHandler);
        assertTrue(waterHandler.reachedGoal());
    }

    @Test
    public void testReachedGoalWhenFalse() {
        initializeAlmostFullWaterHandler(waterHandler);
        assertFalse(waterHandler.reachedGoal());
    }
}
