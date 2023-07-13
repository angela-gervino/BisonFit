package funkyflamingos.bisonfit.persistence.stubs;

import java.util.ArrayList;
import java.util.HashMap;

import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.WorkoutHeader;
import funkyflamingos.bisonfit.persistence.ISavedWorkoutExercises;

public class SavedWorkoutExercisesPersistenceStub implements ISavedWorkoutExercises {
    private int nextIndex;
    private ArrayList<int[]> table;
    private HashMap<Integer, String> idToExerciseName;

    public SavedWorkoutExercisesPersistenceStub() {
        nextIndex = 0;
        table = new ArrayList<>();
        idToExerciseName = new HashMap<>();
    }

    @Override
    public ArrayList<ExerciseHeader> getExercisesByWorkout(WorkoutHeader workoutHeader) {
        ArrayList<ExerciseHeader> exerciseHeaders = new ArrayList<>();
        for (int[] entry : table) {
            if (entry[0] == workoutHeader.getId()) {
                ExerciseHeader exerciseHeader = new ExerciseHeader(idToExerciseName.get(entry[1]), entry[1], entry[3], entry[2]);
                exerciseHeaders.add(exerciseHeader);
            }
        }
        return exerciseHeaders;
    }

    @Override
    public void addExercises(ArrayList<ExerciseHeader> exerciseHeaders, WorkoutHeader workoutHeader) {
        for (ExerciseHeader exerciseHeader : exerciseHeaders) {
            int[] temp = {workoutHeader.getId(), exerciseHeader.getId(), nextIndex, exerciseHeader.getSetCount()};
            table.add(temp);
            nextIndex++;

            if (!idToExerciseName.containsKey(exerciseHeader.getId()))
                idToExerciseName.put(exerciseHeader.getId(), exerciseHeader.getName());
        }
    }

    @Override
    public void deleteExercise(ExerciseHeader exerciseHeader, WorkoutHeader workoutHeader) {
        boolean found = false;
        for (int i = 0; i < table.size() && !found; i++) {
            int[] entry = table.get(i);
            if (entry[0] == workoutHeader.getId() && entry[1] == exerciseHeader.getId() && entry[2] == exerciseHeader.getIndex()) {
                table.remove(i);
                found = true;
            }
        }
    }

    @Override
    public void deleteWorkout(WorkoutHeader workoutHeader) {
        ArrayList<Integer> indices = getIndices(workoutHeader.getId());
        for (int index : indices)
            table.remove(index);
    }

    private ArrayList<Integer> getIndices(int workoutId) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i)[0] == workoutId)
                indices.add(0, i);
        }
        return indices;
    }
}
