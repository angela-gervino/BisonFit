package funkyflamingos.bisonfit.dso;

import java.util.ArrayList;

public class Routine {
    private RoutineHeader header;
    private final ArrayList<Exercise> exercises;
    private ArrayList<ExerciseHeader> exerciseHeaders;

    // will add more fields later

    // main constructor
    public Routine(RoutineHeader header) {
        this.header = header;
        exercises = new ArrayList<>();

        exerciseHeaders = new ArrayList<>();
        exerciseHeaders.add(new ExerciseHeader("Jumping Jacks", 1));
        exerciseHeaders.add(new ExerciseHeader("Bicep Curls", 2));
        exerciseHeaders.add(new ExerciseHeader("Deadlifts", 3));
        exerciseHeaders.add(new ExerciseHeader("Pushups", 4));
        exerciseHeaders.add(new ExerciseHeader("Yoga", 5));
        exerciseHeaders.add(new ExerciseHeader("Swimming", 6));
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

    public void addExerciseHeaders(ArrayList<ExerciseHeader> newExerciseHeaders) {
        exerciseHeaders.addAll(newExerciseHeaders);
    }

    public ArrayList<ExerciseHeader> getExerciseHeaders() {
        return exerciseHeaders;
    }
}
