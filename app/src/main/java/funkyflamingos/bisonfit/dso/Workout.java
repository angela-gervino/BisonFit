package funkyflamingos.bisonfit.dso;

public class Workout {
    private WorkoutHeader header;

    public Workout(WorkoutHeader header) {
        this.header = header;
    }

    public WorkoutHeader getHeader() {
        return header;
    }

    public String getName() {
        return header.getName();
    }
}
