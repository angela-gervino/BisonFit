package funkyflamingos.bisonfit.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.Exercise;
import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.WorkoutHeader;
import funkyflamingos.bisonfit.logic.IWorkoutHandler;
import funkyflamingos.bisonfit.logic.WorkoutHandler;

public class WorkoutOverviewActivity extends AppCompatActivity {
    private WorkoutHeader workoutHeader;
    private RecyclerView recyclerView;
    private WorkoutOverviewExercisesListAdapter adapter;
    private Button startBtn;
    private IWorkoutHandler workoutHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_overview);
        startBtn = findViewById(R.id.startWorkoutButton);

        workoutHandler = new WorkoutHandler();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int clickedWorkoutID = extras.getInt("workoutID");
            workoutHeader = workoutHandler.getWorkoutHeaderByID(clickedWorkoutID);
            String clickedWorkoutName = workoutHeader.getName();
            getActionBar().setTitle(clickedWorkoutName);
        }

        adapter = new WorkoutOverviewExercisesListAdapter(workoutHeader, workoutHandler.getExerciseHeaders(workoutHeader));
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
        intent.putExtra("workoutID", workoutHeader.getId());
        startActivity(intent);
    }

    public void openActiveWorkoutActivity(View v) {
        if(startButtonEnabled()) {
            Intent intent = new Intent(this, ActiveWorkoutActivity.class);
            intent.putExtra("workoutID", workoutHeader.getId());
            activeWorkoutResultLauncher.launch(intent);
        }
        else
            Toast.makeText(this, "Add some workouts first", Toast.LENGTH_SHORT).show();
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

    private boolean startButtonEnabled() {
        List<ExerciseHeader> exerciseHeaders = workoutHandler.getExerciseHeaders(workoutHeader);
        boolean result = false;

        for(ExerciseHeader header : exerciseHeaders)
            if (header.getSetCount() > 0) {
                result = true;
                break;
            }

        return  result;
    }
}