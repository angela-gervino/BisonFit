package funkyflamingos.bisonfit.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;
import funkyflamingos.bisonfit.persistence.IWorkoutPersistence;

public class WorkoutPersistenceStub implements IWorkoutPersistence {
    private List<WorkoutHeader> allWorkouts;
    private int nextId;

    public WorkoutPersistenceStub() {
        allWorkouts = new ArrayList<>();
        nextId = 0;
    }

    @Override
    public List<WorkoutHeader> getAllWorkoutHeaders() {
        List<WorkoutHeader> allHeaders = new ArrayList<>();
        for (WorkoutHeader workoutHeader : allWorkouts)
            allHeaders.add(workoutHeader);
        return allHeaders;
    }

    @Override
    public WorkoutHeader getWorkoutHeaderByID(int workoutID) {
        return allWorkouts.stream().filter(workout -> workout.getId() == workoutID)
                .findFirst().orElse(null);
    }

    @Override
    public Workout getWorkoutByID(int workoutID) {
        WorkoutHeader header = getWorkoutHeaderByID(workoutID);
        return header != null ? new Workout(header) : null;
    }

    @Override
    public void addWorkout(String workoutName) {
        allWorkouts.add(new WorkoutHeader(workoutName, nextId));
        nextId++;
    }

    @Override
    public void deleteWorkoutById(int workoutId) {
        allWorkouts.remove(getIndexById(workoutId));
    }

    private int getIndexById(int workoutId) {
        int index = -1;
        for (int i = 0; i < allWorkouts.size() && index == -1; i++) {
            if (allWorkouts.get(i).getId() == workoutId)
                index = i;
        }
        return index;
    }
}
