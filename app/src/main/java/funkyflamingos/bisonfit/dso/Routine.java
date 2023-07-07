package funkyflamingos.bisonfit.dso;

import java.util.ArrayList;
import java.util.Date;

import funkyflamingos.bisonfit.R;

public class Routine {
    private RoutineHeader header;
    private final ArrayList<Exercise> exercises;

    // main constructor
    public Routine(RoutineHeader header) {
        this.header = header;
        exercises = new ArrayList<>();
    }

    public Routine(String name, int id) {
        this(new RoutineHeader(name, id));
    }

    public RoutineHeader getHeader() {
        return header;
    }

    public Exercise getExercise(int pos) {
        return exercises.get(pos);
    }

    public ArrayList<Exercise> getAllExercises() {
        return exercises;
    }

    public String getName() {
        return header.getName();
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    //need a function that returns all exercise headers as an ArrayList

    //need a function that takes an index and removed the exercise header at that index
}
