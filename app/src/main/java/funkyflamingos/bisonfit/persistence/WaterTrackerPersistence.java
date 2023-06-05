package funkyflamingos.bisonfit.persistence;

import java.time.LocalDate;

public interface WaterTrackerPersistence {

    void increment(LocalDate date);

    int getGoal();

    int getProgress(LocalDate date);

}
