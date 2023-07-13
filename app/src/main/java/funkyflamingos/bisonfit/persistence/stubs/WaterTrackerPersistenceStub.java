package funkyflamingos.bisonfit.persistence.stubs;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;

import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;

public class WaterTrackerPersistenceStub implements IWaterTrackerPersistence {
    private Map<LocalDate, Integer> progress;
    private int goal;

    public WaterTrackerPersistenceStub() {
        progress = new HashMap<>();
        goal = 8;
    }

    @Override
    public int getGoal() {
        return goal;
    }

    /* This function takes a LocalDate and increments the amount of water
    * drank on that day (by updating the progress map. */
    @Override
    public void increment(LocalDate date) {
        Integer datesProgress = progress.getOrDefault(date, 0);
        progress.put(date, datesProgress != null ? ++datesProgress : 1);
    }

    /* This function takes a LocalDate and returns the amount of water
    * drank on that day (by checking the progress map) */
    @Override
    public int getProgress(LocalDate date) {
        Integer datesProgress = progress.getOrDefault(date, 0);
        return datesProgress != null ? datesProgress : 0;
    }
}