package funkyflamingos.bisonfit.logic;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;
import funkyflamingos.bisonfit.persistence.stubs.WaterTrackerPersistenceStub;

import java.time.LocalDate;

public class WaterHandler implements IWaterHandler {
    IWaterTrackerPersistence persistence;


    // Constructor for the stub

   // public WaterHandler() {
  //      persistence = new WaterTrackerPersistenceStub();
   // }

    public WaterHandler(IWaterTrackerPersistence persistence) {
        this.persistence = persistence;
    }


    // Constructor for the database
   public WaterHandler(){
        persistence = Services.getWaterTrackPersistence();
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
    }

    ;
}