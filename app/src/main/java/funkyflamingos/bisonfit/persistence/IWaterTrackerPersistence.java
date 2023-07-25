package funkyflamingos.bisonfit.persistence;

import java.time.LocalDate;

public interface IWaterTrackerPersistence {

    void increment(LocalDate date);

    int getGoal();

    int getProgress(LocalDate date);

    void clear(LocalDate date);
}
