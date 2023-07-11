package funkyflamingos.bisonfit.ui;

import funkyflamingos.bisonfit.logic.GymHoursHandler;
import funkyflamingos.bisonfit.logic.IGymHoursHandler;
import funkyflamingos.bisonfit.logic.IRoutineHandler;
import funkyflamingos.bisonfit.logic.IUserRegistrationHandler;
import funkyflamingos.bisonfit.logic.IWaterHandler;
import funkyflamingos.bisonfit.logic.UserRegistrationHandler;
import funkyflamingos.bisonfit.logic.WaterHandler;
import funkyflamingos.bisonfit.logic.RoutineHandler;

import funkyflamingos.bisonfit.dso.RoutineHeader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import funkyflamingos.bisonfit.R;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class HomePageActivity extends AppCompatActivity implements AddWorkoutDialog.AddWorkoutDialogListener {
    private CircularProgressIndicator waterTrackerProgress;
    private IWaterHandler waterHandler;
    private IGymHoursHandler gymHoursHandler;
    private TextView lblGreetings;
    private Button btnGymHours;
    private IUserRegistrationHandler userNameHandler;
    private IRoutineHandler workoutManager;
    private MyWorkoutsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        waterHandler = new WaterHandler();
        gymHoursHandler = new GymHoursHandler();
        waterTrackerProgress = findViewById(R.id.circularProgressView);
        btnGymHours = findViewById(R.id.btnGymHours);
        lblGreetings = findViewById(R.id.lblGreetings);
        userNameHandler = new UserRegistrationHandler();

        workoutManager = new RoutineHandler();
        List<RoutineHeader> listOfWorkouts = workoutManager.getAllRoutineHeaders();
        adapter = new MyWorkoutsListAdapter(listOfWorkouts, this);
        RecyclerView recyclerView = findViewById(R.id.lstMyWorkouts);

        setWaterTrackerProgress();
        waterTrackerProgress.setMax(waterHandler.getGoal());

        // display user name
        lblGreetings.setText(getGreetingsMessage());

        // recyclerVew setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // start refresher thread
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        String newStatus = gymHoursHandler.getTimeUntilOpenOrClose();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btnGymHours.setText(newStatus);
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
        setWaterTrackerProgress();
    }

    private void setWaterTrackerProgress() {
        waterTrackerProgress.setProgress(waterHandler.getProgress());
        if (waterHandler.reachedGoal()) { // set goal  as accomplished
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

    public void openGymHoursActivity(View v) {
        Intent intent = new Intent(this, GymHoursActivity.class);
        startActivity(intent);
    }

    @Override
    public void createNewWorkout(String newWorkoutName)
    {
        workoutManager.addNewRoutine(newWorkoutName);
        adapter.updateWorkoutList(workoutManager.getAllRoutineHeaders());
    }

    public void newWorkoutDialog(View view) {
        AddWorkoutDialog addWorkoutDialog = new AddWorkoutDialog();
        addWorkoutDialog.show(getSupportFragmentManager(), "Add Workout Dialog");
    }
}