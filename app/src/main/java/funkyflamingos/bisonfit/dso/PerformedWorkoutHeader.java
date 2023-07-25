package funkyflamingos.bisonfit.dso;

import java.time.LocalDateTime;

public class PerformedWorkoutHeader extends WorkoutHeader {
    private final LocalDateTime dateStarted;
    private LocalDateTime dateEnded;
    private int performedWorkoutId;

    // TODO: Remove one of the constructors. Only one will be needed, not sure which one yet.
    public PerformedWorkoutHeader(String name, int id, LocalDateTime dateStarted) {
        super(name, id);
        this.dateStarted = dateStarted;
        this.dateEnded = null;
        performedWorkoutId = -1;
    }

    public PerformedWorkoutHeader(WorkoutHeader workoutHeader, LocalDateTime dateStarted) {
        this(workoutHeader.getName(), workoutHeader.getId(), dateStarted);
    }

    public LocalDateTime getDateStarted() {
        return dateStarted;
    }

    public LocalDateTime getDateEnded() { return dateEnded; }

    public void setDateEnded(LocalDateTime dateEnded) { this.dateEnded = dateEnded; }

    public int getPerformedWorkoutId() { return performedWorkoutId; }

    public void setPerformedWorkoutId(int performedWorkoutId) { this.performedWorkoutId = performedWorkoutId; }
}
