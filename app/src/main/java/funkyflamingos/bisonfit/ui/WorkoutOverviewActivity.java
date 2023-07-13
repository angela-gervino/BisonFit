package funkyflamingos.bisonfit.ui;

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

    private Workout routine;

    private RecyclerView recyclerView;
    private WorkoutOverviewExercisesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_overview);

        IWorkoutHandler routineHandler = new WorkoutHandler();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int clickedRoutineID = extras.getInt("routineID");
            routine = routineHandler.getRoutineByID(clickedRoutineID);
            String clickedRoutineName = routine.getHeader().getName();
            getActionBar().setTitle(clickedRoutineName);
        }

        adapter = new WorkoutOverviewExercisesListAdapter(routine.getHeader(), routineHandler.getExerciseHeaders(routine.getHeader()));
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
        intent.putExtra("routineID", routine.getHeader().getId());
        startActivity(intent);
    }

    public void openActiveWorkoutActivity(View v) {
        Intent intent = new Intent(this, ActiveWorkoutActivity.class);
        startActivity(intent);
    }
}