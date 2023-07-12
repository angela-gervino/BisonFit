package funkyflamingos.bisonfit.dso;

public class RoutineHeader {
    String name;
    int id;

    public RoutineHeader(String name, int id) {
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
