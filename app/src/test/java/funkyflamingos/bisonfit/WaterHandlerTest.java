package funkyflamingos.bisonfit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import funkyflamingos.bisonfit.logic.WaterHandler;

public class WaterHandlerTest {
    private WaterHandler waterHandler;

    @Before
    public void setup() {
        System.out.println("Setting up for WaterHandler Test\n");
        waterHandler = new WaterHandler();
    }

    @Test
    public void testFirstIncrement() {
        System.out.println("Starting testFirstIncrement()... ");
        waterHandler.increment();
        assertEquals(1, waterHandler.getProgress());
        System.out.println("Finished testFirstIncrement()\n");
    }

    @Test
    public void testMiddleIncrements() {
        initializeNonEmptyWaterHandler();
        System.out.println("Starting testMiddleIncrements()... ");
        for (int i = 2; i < waterHandler.getGoal() - 1; i++) {
            waterHandler.increment();
            assertEquals(i, waterHandler.getProgress());
        }

        System.out.println("Finished testMiddleIncrements()\n");
    }

    @Test
    public void testLastIncrement() {
        initializeAlmostFullWaterHandler();
        System.out.println("Starting testLastIncrement()... ");
        waterHandler.increment();
        assertEquals(8, waterHandler.getProgress());
        System.out.println("Finished testLastIncrement()\n");
    }

    @Test
    public void testIncrementAttemptWhenFull() {
        initializeFullWaterHandler();
        System.out.println("Starting testIncrementAttemptWhenFull()... ");
        waterHandler.increment();
        assertEquals(8, waterHandler.getProgress());
        System.out.println("Finished testIncrementAttemptWhenFull()\n");
    }

    @Test
    public void testGetGoal() {
        System.out.println("Starting testGetGoal()... ");
        assertEquals(8, waterHandler.getGoal());
        System.out.println("Finished testGetGoal()\n");
    }

    @Test
    public void testNoProgress()
    {
        System.out.println("Starting testNoProgress()... ");
        assertEquals(0, waterHandler.getProgress());
        System.out.println("Finished testNoProgress()\n");
    }

    @Test
    public void testCompletedProgress()
    {
        initializeFullWaterHandler();
        System.out.println("Starting testCompletedProgress()... ");
        assertEquals(8, waterHandler.getProgress());
        System.out.println("Finished testCompletedProgress()\n");
    }

    @Test
    public void testPartiallyCompletedProgress()
    {
        initializeNonEmptyWaterHandler();
        System.out.println("Starting testPartiallyCompletedProgress()... ");
        for (int i = 0; i < waterHandler.getGoal(); i++) {
            waterHandler.increment();
            assertEquals(i, waterHandler.getProgress());
        }

        System.out.println("Finished testPartiallyCompletedProgress()\n");
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
