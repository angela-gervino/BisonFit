package funkyflamingos.bisonfit.dso;

public class WorkoutHeader {
    String name;
    int id;

    public WorkoutHeader(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
