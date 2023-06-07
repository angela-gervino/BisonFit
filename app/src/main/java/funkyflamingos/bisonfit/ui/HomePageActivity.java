package funkyflamingos.bisonfit.ui;

import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.logic.WaterHandler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.logic.RoutineHandler;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private CircularProgressIndicator waterTrackerProgress;

    private WaterHandler waterHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        List<RoutineHeader> listOfWorkouts;
        MyWorkoutsListAdapter adapter;
        RecyclerView recyclerView;

        waterTrackerProgress = findViewById(R.id.circularProgressView);
        waterHandler = new WaterHandler();
        RoutineHandler workoutManager = new RoutineHandler();
        listOfWorkouts = workoutManager.getAllRoutineHeaders();

        adapter = new MyWorkoutsListAdapter(listOfWorkouts, this);
        recyclerView = findViewById(R.id.lstMyWorkouts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void incrementAndUpdateWaterTracker(View v) {
        waterHandler.increment();
        waterTrackerProgress.setProgress(waterHandler.getProgress());
    }
}