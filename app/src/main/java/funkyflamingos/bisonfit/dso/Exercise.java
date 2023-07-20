package funkyflamingos.bisonfit.dso;

import java.util.ArrayList;

public class Exercise {
    private ExerciseHeader header;
    private ArrayList <ExerciseSet> sets;

    public Exercise(String name, int id, int setCount) {
        this(name, id);

        for(int i = 0; i < setCount; i++) {
            sets.add(new ExerciseSet(0, 0));
        }
    }

    // TODO: remove one of the constructors
    public Exercise(String name, int id) {
        this(new ExerciseHeader(name, id));
    }

    // go to constructor
    public Exercise(ExerciseHeader header) {
        this.header = header;
        sets = new ArrayList<>();
    }

    public ExerciseSet getSet(int pos) {
        return sets.get(pos);
    }

    public ArrayList<ExerciseSet> getAllSets() {
        return sets;
    }

    public ExerciseHeader getHeader() {
        return header;
    }

    public String getName() {
        return header.getName();
    }

    public int getID() {
        return header.getId();
    }
}
