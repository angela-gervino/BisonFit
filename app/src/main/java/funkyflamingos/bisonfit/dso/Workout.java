package funkyflamingos.bisonfit.dso;

import java.util.ArrayList;

public class Workout {
    private WorkoutHeader header;
    private ArrayList<Exercise> exerciseList;

    public Workout(WorkoutHeader header) {
        this.header = header;
        exerciseList = new ArrayList<>();
    }

    public WorkoutHeader getHeader() {
        return header;
    }

    public String getName() {
        return header.getName();
    }

    public void addExercise(Exercise exerciseToAdd) {
        exerciseList.add(exerciseToAdd);
    }

    public ArrayList<Exercise> getAllExercises() {
        return exerciseList;
    }

    public Exercise getExercise(int index) {
        return exerciseList.get(index);
    }

}
