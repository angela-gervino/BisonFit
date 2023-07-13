package funkyflamingos.bisonfit.persistence.stubs;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;

import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;

public class WaterTrackerPersistenceStub implements IWaterTrackerPersistence {
    Map<LocalDate, Integer> progress;
    int goal;

    public WaterTrackerPersistenceStub() {
        progress = new HashMap<>();
        goal = 8;
    }

    public int getGoal() {
        return goal;
    }

    /* This function takes a LocalDate and increments the amount of water
     * drank on that day (by updating the progress map. */
    public void increment(LocalDate date) {
        Integer datesProgress = progress.getOrDefault(date, 0);
        progress.put(date, datesProgress != null ? ++datesProgress : 1);
    }

    /* This function takes a LocalDate and returns the amount of water
     * drank on that day (by checking the progress map) */
    public int getProgress(LocalDate date) {
        Integer datesProgress = progress.getOrDefault(date, 0);
        return datesProgress != null ? datesProgress : 0;
    }
}