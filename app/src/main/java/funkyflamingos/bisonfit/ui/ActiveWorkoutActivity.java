package funkyflamingos.bisonfit.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.Exercise;
import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;


public class ActiveWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_workout_page);
        RecyclerView rvExercises = findViewById(R.id.rvActiveWorkoutExerciseList);
        Workout workout = createStubRoutine(); // TODO: remove and draw from db
        ActiveWorkoutExerciseListAdapter adapter = new ActiveWorkoutExerciseListAdapter(workout, this);

        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        rvExercises.setAdapter(adapter);

        getActionBar().setTitle("Test");

    }



    private Workout createStubRoutine() {
        Workout routine = new Workout(new WorkoutHeader("Full Body", 1));

        routine.addExercise(new Exercise("Biceps Curls", 0, 3));
        routine.addExercise(new Exercise("Triceps Extensions", 1, 3));
        routine.addExercise(new Exercise("Bench Press Curls", 2, 5));
        routine.addExercise(new Exercise("Run", 3, 5));
        routine.addExercise(new Exercise("Squats", 4, 1));
        routine.addExercise(new Exercise("Push ups", 5, 1));

        return  routine;
    }
}
