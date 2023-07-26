package funkyflamingos.bisonfit.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.logic.IWorkoutHandler;
import funkyflamingos.bisonfit.logic.WorkoutHandler;


public class ActiveWorkoutActivity extends AppCompatActivity {

    Workout workout;
    IWorkoutHandler workoutHandler;
    Button finishButton;
    boolean showPreviousWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_workout_page);
        RecyclerView rvExercises = findViewById(R.id.rvActiveWorkoutExerciseList);
        finishButton = findViewById(R.id.btnFinishWorkout);

        int workoutID = getIntent().getIntExtra("workoutID", 0);
        showPreviousWorkout = getIntent().getBooleanExtra("previous", false);

        workoutHandler = new WorkoutHandler();

        if(showPreviousWorkout) {
            workout = workoutHandler.getPerformedWorkout(workoutID);
        }
        else {
            workout = workoutHandler.getWorkoutToPerform(workoutID);
        }

        ActiveWorkoutExerciseListAdapter adapter = new ActiveWorkoutExerciseListAdapter(workout, this, showPreviousWorkout);

        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        rvExercises.setAdapter(adapter);

        getActionBar().setTitle(workout.getHeader().getName());

        if(showPreviousWorkout)
            finishButton.setVisibility(View.GONE);
    }

    public void finishWorkoutBtnClicked(View v) {
        // TODO: save finish time
        boolean saved = workoutHandler.savePerformedWorkout(workout);
        if (!saved) {
            Toast.makeText(this, "There was nothing to save", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        if(!showPreviousWorkout)
            Toast.makeText(this, "Finish this workout to exit", Toast.LENGTH_SHORT).show();
        else
            super.onBackPressed();
    }
}
