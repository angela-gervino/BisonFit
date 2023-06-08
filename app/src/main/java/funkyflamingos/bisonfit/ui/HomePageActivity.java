package funkyflamingos.bisonfit.ui;

import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.logic.GymStatusHandler;
import funkyflamingos.bisonfit.logic.WaterHandler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.logic.RoutineHandler;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private CircularProgressIndicator waterTrackerProgress;
    private WaterHandler waterHandler;
    private GymStatusHandler gymStatusHandler;
    private TextView gymStatusLbl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        List<RoutineHeader> listOfWorkouts;
        MyWorkoutsListAdapter adapter;
        RecyclerView recyclerView;

        RoutineHandler workoutManager = new RoutineHandler();
        waterHandler = new WaterHandler();
        gymStatusHandler = new GymStatusHandler();
        waterTrackerProgress = findViewById(R.id.circularProgressView);
        gymStatusLbl = findViewById(R.id.lblGymStatus);
        waterTrackerProgress.setMax(waterHandler.getGoal());
        listOfWorkouts = workoutManager.getAllRoutineHeaders();

        gymStatusLbl.setText(gymStatusHandler.getGymStatus());

        adapter = new MyWorkoutsListAdapter(listOfWorkouts, this);
        recyclerView = findViewById(R.id.lstMyWorkouts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // start refresher thread
        new Thread() {
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(1000);
                        String newStatus = gymStatusHandler.getGymStatus();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gymStatusLbl.setText(newStatus);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void incrementAndUpdateWaterTracker(View v) {
        waterHandler.increment();
        waterTrackerProgress.setProgress(waterHandler.getProgress());
        if(waterHandler.reachedGoal()) { // set goal  as accomplished
            waterTrackerProgress.setIndicatorColor(ContextCompat.getColor(this, R.color.success_green));
        }
    }
}