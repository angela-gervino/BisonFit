package funkyflamingos.bisonfit.persistence.stubs;

import static funkyflamingos.bisonfit.application.Constants.RECOMMENDED_CUPS_OF_WATER_PER_DAY;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;

import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;

public class WaterTrackerPersistenceStub implements IWaterTrackerPersistence {
    private Map<LocalDate, Integer> progress;

    public WaterTrackerPersistenceStub() {
        progress = new HashMap<>();
    }

    @Override
    public int getGoal() {
        return RECOMMENDED_CUPS_OF_WATER_PER_DAY;
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

    @Override
    public void clear(LocalDate date) {
        progress.put(date, 0);
    }

}