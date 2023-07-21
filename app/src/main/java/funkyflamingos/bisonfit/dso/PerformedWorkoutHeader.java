package funkyflamingos.bisonfit.dso;

import java.time.LocalDateTime;

public class PerformedWorkoutHeader extends WorkoutHeader {
    private final LocalDateTime dateStarted;

    // TODO: Remove one of the constructors. Only one will be needed, not sure which one yet.
    public PerformedWorkoutHeader(String name, int id, LocalDateTime dateStarted) {
        super(name, id);
        this.dateStarted = dateStarted;
    }

    public PerformedWorkoutHeader(WorkoutHeader workoutHeader, LocalDateTime dateStarted) {
        this(workoutHeader.getName(), workoutHeader.getId(), dateStarted);
    }

    public LocalDateTime getDateStarted() {
        return dateStarted;
    }
}
