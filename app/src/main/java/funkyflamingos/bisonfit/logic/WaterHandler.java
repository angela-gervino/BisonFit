package funkyflamingos.bisonfit.logic;

import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;
import funkyflamingos.bisonfit.persistence.stubs.WaterTrackerPersistenceStub;

import java.time.LocalDate;

public class WaterHandler {
    IWaterTrackerPersistence persistence;

    public WaterHandler() {
        persistence = new WaterTrackerPersistenceStub();
    }
    public WaterHandler(IWaterTrackerPersistence persistence) {
        this.persistence = persistence;
    }
    public void increment() {
        LocalDate today = LocalDate.now();

        if (persistence.getProgress(today) < persistence.getGoal())
            persistence.increment(today);
    }

    public int getProgress() {
        return persistence.getProgress(LocalDate.now());
    }

    public int getGoal() {
        return persistence.getGoal();
    }

    public boolean reachedGoal() {
        return getProgress() == getGoal();
    };
}