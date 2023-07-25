package funkyflamingos.bisonfit.persistence;

import java.util.ArrayList;

import funkyflamingos.bisonfit.dso.PerformedWorkoutHeader;
import funkyflamingos.bisonfit.dso.Workout;

public interface IPerformedWorkoutRecordPersistence {
    void addPerformedWorkout(Workout workout);

    ArrayList<PerformedWorkoutHeader> getPerformedWorkoutHeaders();

    Workout getPerformedWorkoutById(int id);
}
