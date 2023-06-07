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

    public void increment(LocalDate date) {
        int newValue = progress.containsKey(date) ? progress.get(date) + 1 : 1;
        progress.put(date, newValue);
    }

    public int getGoal() {
        return goal;
    }

    public int getProgress(LocalDate date) {
        return progress.containsKey(date) ? progress.get(date) : 0;
    }
}