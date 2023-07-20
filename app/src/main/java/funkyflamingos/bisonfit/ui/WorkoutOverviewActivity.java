package funkyflamingos.bisonfit.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.logic.IWorkoutHandler;
import funkyflamingos.bisonfit.logic.WorkoutHandler;

public class WorkoutOverviewActivity extends AppCompatActivity {
    private Workout workout;
    private RecyclerView recyclerView;
    private WorkoutOverviewExercisesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_overview);

        IWorkoutHandler workoutHandler = new WorkoutHandler();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int clickedWorkoutID = extras.getInt("workoutID");
            workout = workoutHandler.getWorkoutByID(clickedWorkoutID);
            String clickedWorkoutName = workout.getHeader().getName();
            getActionBar().setTitle(clickedWorkoutName);
        }

        adapter = new WorkoutOverviewExercisesListAdapter(workout.getHeader(), workoutHandler.getExerciseHeaders(workout.getHeader()));
        recyclerView = findViewById(R.id.lstMyExercises);

        //recyclerVew setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.refreshData();
        adapter.notifyDataSetChanged();
    }

    public void openExerciseSelectionActivity(View v) {
        Intent intent = new Intent(this, ExerciseSelectionActivity.class);
        intent.putExtra("workoutID", workout.getHeader().getId());
        startActivity(intent);
    }

    public void openActiveWorkoutActivity(View v) {
        Intent intent = new Intent(this, ActiveWorkoutActivity.class);
        intent.putExtra("workoutID", workout.getHeader().getId());
        activeWorkoutResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> activeWorkoutResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    finish();
                }
            }
    );
}