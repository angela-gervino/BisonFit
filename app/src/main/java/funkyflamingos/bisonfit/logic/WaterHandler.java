package funkyflamingos.bisonfit.logic;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;

import java.time.LocalDate;

public class WaterHandler implements IWaterHandler {
    IWaterTrackerPersistence persistence;


    // Constructor for the stub
    public WaterHandler(IWaterTrackerPersistence persistence) {
        this.persistence = persistence;
    }


    // Constructor for the database
    public WaterHandler() {
        persistence = Services.getWaterTrackPersistence();
    }

    @Override
    public void increment() {
        LocalDate today = LocalDate.now();

        if (persistence.getProgress(today) < persistence.getGoal())
            persistence.increment(today);
    }

    @Override
    public int getProgress() {
        return persistence.getProgress(LocalDate.now());
    }

    @Override
    public int getGoal() {
        return persistence.getGoal();
    }

    @Override
    public boolean reachedGoal() {
        return getProgress() == getGoal();
    }

}