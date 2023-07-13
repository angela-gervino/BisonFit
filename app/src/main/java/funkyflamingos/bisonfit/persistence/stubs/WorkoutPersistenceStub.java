package funkyflamingos.bisonfit.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;
import funkyflamingos.bisonfit.persistence.IWorkoutPersistence;

public class WorkoutPersistenceStub implements IWorkoutPersistence {

    List<Workout> allWorkouts;
    int nextId = 0;

    public WorkoutPersistenceStub() {
        allWorkouts = new ArrayList<Workout>();
    }

    @Override
    public List<WorkoutHeader> getAllWorkoutHeaders() {
        List<WorkoutHeader> allHeaders = new ArrayList<WorkoutHeader>();
        for (Workout workout : allWorkouts)
            allHeaders.add(workout.getHeader());
        return allHeaders;
    }

    @Override
    public Workout getWorkoutByID(int workoutID) {
        return allWorkouts.stream().filter(workout -> workout.getHeader().getId() == workoutID)
                .findFirst().orElse(null);
    }

    @Override
    public void addWorkout(String workoutName) {
        allWorkouts.add(new Workout(new WorkoutHeader(workoutName, nextId)));
        nextId++;
    }

    @Override
    public void deleteWorkoutById(int workoutId) {
        allWorkouts.remove(getIndexById(workoutId));
    }

    private int getIndexById(int workoutId) {
        int index = -1;
        for (int i = 0; i < allWorkouts.size() && index == -1; i++) {
            if (allWorkouts.get(i).getHeader().getId() == workoutId)
                index = i;
        }
        return index;
    }
}
