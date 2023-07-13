package funkyflamingos.bisonfit.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static funkyflamingos.bisonfit.logic.WaterHandlerTestHelper.initializeAlmostFullWaterHandler;
import static funkyflamingos.bisonfit.logic.WaterHandlerTestHelper.initializeFullWaterHandler;
import static funkyflamingos.bisonfit.logic.WaterHandlerTestHelper.initializeNonEmptyWaterHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;
import funkyflamingos.bisonfit.persistence.hsqldb.WaterTrackerPersistenceHSQLDB;
import funkyflamingos.bisonfit.utils.TestUtils;

public class WaterHandlerIT {
    private WaterHandler waterHandler;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        final IWaterTrackerPersistence persistence = new WaterTrackerPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
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

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }

}
