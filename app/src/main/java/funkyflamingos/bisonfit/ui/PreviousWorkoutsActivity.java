package funkyflamingos.bisonfit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.PerformedWorkoutHeader;
import funkyflamingos.bisonfit.logic.IWorkoutHandler;
import funkyflamingos.bisonfit.logic.WorkoutHandler;

public class PreviousWorkoutsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_workouts);

        IWorkoutHandler workoutHandler = new WorkoutHandler();

        // set up recycler view
        RecyclerView recyclerView = findViewById(R.id.rvPrevWorkouts);
        List<PerformedWorkoutHeader> workoutHeadersToShow = workoutHandler.getPerformedWorkoutHeaders();
        PreviousWorkoutsListAdapter adapter = new PreviousWorkoutsListAdapter(workoutHeadersToShow, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}