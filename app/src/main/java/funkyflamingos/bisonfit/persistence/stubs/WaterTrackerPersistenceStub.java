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

    public void increment(LocalDate date) {
        Integer datesProgress = progress.getOrDefault(date, 0);
        progress.put(date, datesProgress != null ? ++datesProgress : 1);
    }
    public int getProgress(LocalDate date) {
        Integer datesProgress = progress.getOrDefault(date, 0);
        return datesProgress != null ? datesProgress : 0;
    }
}