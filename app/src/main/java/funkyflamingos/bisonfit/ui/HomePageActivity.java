package funkyflamingos.bisonfit.ui;

import funkyflamingos.bisonfit.logic.GymHoursHandler;
import funkyflamingos.bisonfit.logic.IUserRegistrationHandler;
import funkyflamingos.bisonfit.logic.UserRegistrationHandler;
import funkyflamingos.bisonfit.logic.WaterHandler;
import funkyflamingos.bisonfit.logic.RoutineHandler;

import funkyflamingos.bisonfit.dso.RoutineHeader;

import funkyflamingos.bisonfit.persistence.utils.DBHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import funkyflamingos.bisonfit.R;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private CircularProgressIndicator waterTrackerProgress;
    private WaterHandler waterHandler;
    private GymHoursHandler gymStatusHandler;
    private TextView gymStatusLbl;
    private TextView lblGreetings;
    private IUserRegistrationHandler userNameHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //creating the Database
        DBHelper.copyDatabaseToDevice(this);

        waterHandler = new WaterHandler();
        gymStatusHandler = new GymHoursHandler();
        waterTrackerProgress = findViewById(R.id.circularProgressView);
        gymStatusLbl = findViewById(R.id.lblGymStatus);
        lblGreetings = findViewById(R.id.lblGreetings);
        // android database
        userNameHandler = new UserRegistrationHandler(this);
        // new database
       // userNameHandler = new UserRegistrationHandler();

        RoutineHandler workoutManager = new RoutineHandler();
        List<RoutineHeader> listOfWorkouts = workoutManager.getAllRoutineHeaders();
        MyWorkoutsListAdapter adapter = new MyWorkoutsListAdapter(listOfWorkouts, this);
        RecyclerView recyclerView = findViewById(R.id.lstMyWorkouts);

        waterTrackerProgress.setMax(waterHandler.getGoal());

        // display user name
        lblGreetings.setText(getGreetingsMessage());

        // recyclerVew setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // start refresher thread
        new Thread() {
            public void run() {
                while(true) {
                    try {
                        String newStatus = gymStatusHandler.getGymStatus();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gymStatusLbl.setText(newStatus);
                            }
                        });
                        Thread.sleep(1000);
                    } catch (Exception e) {
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

    @Override
    public void onBackPressed() {
        // exit app on back press
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

    public String getGreetingsMessage() {
       return "Hi " + userNameHandler.getUserName() + "!";
    }
}