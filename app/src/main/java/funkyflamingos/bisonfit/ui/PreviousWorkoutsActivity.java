package funkyflamingos.bisonfit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.time.LocalDateTime;
import java.util.ArrayList;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.PerformedWorkoutHeader;

public class PreviousWorkoutsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_workouts);

        // set up recycler view
        RecyclerView recyclerView = findViewById(R.id.rvPrevWorkouts);
        ArrayList<PerformedWorkoutHeader> workoutHeadersToShow = createStubHeaderList();
        PreviousWorkoutsListAdapter adapter = new PreviousWorkoutsListAdapter(workoutHeadersToShow, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<PerformedWorkoutHeader> createStubHeaderList() {
        ArrayList<PerformedWorkoutHeader> list = new ArrayList<>();
        list.add(new PerformedWorkoutHeader("Upper Body", 0, LocalDateTime.now()));
        list.add(new PerformedWorkoutHeader("Lower Body", 1, LocalDateTime.now()));
        list.add(new PerformedWorkoutHeader("Full Body", 1, LocalDateTime.now()));

        return list;
    }
}