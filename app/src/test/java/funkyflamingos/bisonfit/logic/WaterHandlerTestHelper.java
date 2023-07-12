package funkyflamingos.bisonfit.logic;

public class WaterHandlerTestHelper {
    /**
     * Static helper methods used by both integration and unit tests.
     */
    public static void initializeNonEmptyWaterHandler(WaterHandler waterHandler) {
        waterHandler.increment();
    }

    public static void initializeAlmostFullWaterHandler(WaterHandler waterHandler) {
        for (int i = 0; i < waterHandler.getGoal() - 1; i++)
            waterHandler.increment();
    }

    public static void initializeFullWaterHandler(WaterHandler waterHandler) {
        for (int i = 0; i < waterHandler.getGoal(); i++)
            waterHandler.increment();
    }
}
