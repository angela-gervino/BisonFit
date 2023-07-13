package funkyflamingos.bisonfit.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.logic.IWorkoutHandler;
import funkyflamingos.bisonfit.logic.WorkoutHandler;

public class ExerciseSelectionActivity extends AppCompatActivity {
    private int workoutID;
    private IWorkoutHandler workoutHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_selection_page);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            workoutID = extras.getInt("workoutID");
        }

        workoutHandler = new WorkoutHandler();

        ExerciseSelectionListAdapter adapter = new ExerciseSelectionListAdapter(workoutHandler.getAllExerciseHeaders(), this);

        RecyclerView recyclerView = findViewById(R.id.lstExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void addExercises(View v) {
        workoutHandler.addSelectedExercisesToWorkout(workoutID);
        finish();
    }
}
