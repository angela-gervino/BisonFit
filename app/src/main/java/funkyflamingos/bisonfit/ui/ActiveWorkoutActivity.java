package funkyflamingos.bisonfit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.Exercise;
import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;
import funkyflamingos.bisonfit.logic.IWorkoutHandler;
import funkyflamingos.bisonfit.logic.WorkoutHandler;


public class ActiveWorkoutActivity extends AppCompatActivity {

    Workout workout;
    IWorkoutHandler workoutHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_workout_page);
        RecyclerView rvExercises = findViewById(R.id.rvActiveWorkoutExerciseList);

        int workoutID = getIntent().getIntExtra("workoutID", 0);

        workoutHandler = new WorkoutHandler();
        workout = workoutHandler.getWorkoutToPerform(workoutID);
        ActiveWorkoutExerciseListAdapter adapter = new ActiveWorkoutExerciseListAdapter(workout, this);

        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        rvExercises.setAdapter(adapter);

        getActionBar().setTitle(workout.getHeader().getName());

    }

    public void finishWorkoutBtnClicked(View v) {
        boolean saved = workoutHandler.savePerformedWorkout(workout);
        if (!saved) {
            Toast.makeText(this, "There was nothing to save", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Finish this workout to exit", Toast.LENGTH_SHORT).show();
    }
}
