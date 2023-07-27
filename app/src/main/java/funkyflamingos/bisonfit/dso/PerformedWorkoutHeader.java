package funkyflamingos.bisonfit.dso;

import java.time.LocalDateTime;

public class PerformedWorkoutHeader extends WorkoutHeader {
    private LocalDateTime dateStarted;
    private LocalDateTime dateEnded;

    public PerformedWorkoutHeader(String name, int id) {
        super(name, id);
        this.dateEnded = null;
    }

    public PerformedWorkoutHeader(WorkoutHeader workoutHeader) {
        this(workoutHeader.getName(), workoutHeader.getId());
    }

    public LocalDateTime getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(LocalDateTime dateStarted) {
        this.dateStarted = dateStarted;
    }

    public LocalDateTime getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(LocalDateTime dateEnded) {
        this.dateEnded = dateEnded;
    }

}
