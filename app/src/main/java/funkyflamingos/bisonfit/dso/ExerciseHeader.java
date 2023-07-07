package funkyflamingos.bisonfit.dso;

public class ExerciseHeader {

    private String name;
    private int id;

    public ExerciseHeader(String name, int id) {
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

