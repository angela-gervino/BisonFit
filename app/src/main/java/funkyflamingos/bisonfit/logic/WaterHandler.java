package funkyflamingos.bisonfit.logic;

import funkyflamingos.bisonfit.persistence.stubs.WaterTrackerPersistenceStub;

import java.time.LocalDate;

public class WaterHandler {
    WaterTrackerPersistenceStub persistence;

    public WaterHandler()
    {
        persistence = new WaterTrackerPersistenceStub();
    }

    public void increment()
    {
        LocalDate today = LocalDate.now();

        if (persistence.getProgress(today) < persistence.getGoal())
            persistence.increment(today);
    }

    public int getProgress()
    {
        return persistence.getProgress(LocalDate.now());
    }

    public int getGoal()
    {
        return persistence.getGoal();
    }

}