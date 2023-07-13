package funkyflamingos.bisonfit.persistence.stubs;

import java.util.ArrayList;

import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.persistence.IExerciseLookupPersistence;

public class ExerciseLookupPersistenceStub implements IExerciseLookupPersistence {
    private ArrayList<ExerciseHeader> exerciseHeaders;

    public ExerciseLookupPersistenceStub() {
        exerciseHeaders = new ArrayList<>();
        exerciseHeaders.add(new ExerciseHeader("Bench Press", 1));
        exerciseHeaders.add(new ExerciseHeader("Bicep Curl", 2));
        exerciseHeaders.add(new ExerciseHeader("Chest Fly", 3));
        exerciseHeaders.add(new ExerciseHeader("Chest Press", 4));
        exerciseHeaders.add(new ExerciseHeader("Deadlift", 5));
        exerciseHeaders.add(new ExerciseHeader("Hammer Curl", 6));
        exerciseHeaders.add(new ExerciseHeader("Lat Pulldown", 7));
        exerciseHeaders.add(new ExerciseHeader("Lateral Raise", 8));
        exerciseHeaders.add(new ExerciseHeader("Leg Curl", 9));
        exerciseHeaders.add(new ExerciseHeader("Leg Extension", 10));
        exerciseHeaders.add(new ExerciseHeader("Leg Press", 11));
        exerciseHeaders.add(new ExerciseHeader("Shoulder Press", 12));
        exerciseHeaders.add(new ExerciseHeader("Burpee", 13));
        exerciseHeaders.add(new ExerciseHeader("Crunches", 14));
        exerciseHeaders.add(new ExerciseHeader("Cycling", 15));
        exerciseHeaders.add(new ExerciseHeader("Lunge", 16));
        exerciseHeaders.add(new ExerciseHeader("Mountain Climber", 17));
        exerciseHeaders.add(new ExerciseHeader("Plank", 18));
        exerciseHeaders.add(new ExerciseHeader("Pull-Up", 19));
        exerciseHeaders.add(new ExerciseHeader("Push-Up", 20));
        exerciseHeaders.add(new ExerciseHeader("Running", 21));
        exerciseHeaders.add(new ExerciseHeader("Sprinting", 22));
        exerciseHeaders.add(new ExerciseHeader("Squat", 23));
        exerciseHeaders.add(new ExerciseHeader("Swimming", 24));
        exerciseHeaders.add(new ExerciseHeader("Tricep Dip", 25));
        exerciseHeaders.add(new ExerciseHeader("Walking", 26));
    }

    @Override
    public ArrayList<ExerciseHeader> getAllExerciseHeaders() {
        return exerciseHeaders;
    }
}
