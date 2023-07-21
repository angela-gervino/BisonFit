package funkyflamingos.bisonfit.dso;

import java.time.LocalTime;

public class PerformedWorkoutHeader extends WorkoutHeader {
    private final LocalTime dateStarted;

    // TODO: Remove one of the constructors. Only one will be needed, not sure which one yet.
    public PerformedWorkoutHeader(String name, int id, LocalTime dateStarted) {
        super(name, id);
        this.dateStarted = dateStarted;
    }

    public PerformedWorkoutHeader(WorkoutHeader workoutHeader, LocalTime dateStarted) {
        this(workoutHeader.getName(), workoutHeader.getId(), dateStarted);
    }

    public LocalTime getDateStarted() {
        return dateStarted;
    }
}
