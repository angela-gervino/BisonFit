package funkyflamingos.bisonfit.ui;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

import funkyflamingos.bisonfit.dso.TimeUntilOpenOrClose;
import funkyflamingos.bisonfit.logic.GymHoursHandler;
import funkyflamingos.bisonfit.logic.IGymHoursHandler;
import funkyflamingos.bisonfit.logic.IWorkoutHandler;
import funkyflamingos.bisonfit.logic.IUserRegistrationHandler;
import funkyflamingos.bisonfit.logic.IWaterHandler;
import funkyflamingos.bisonfit.logic.UserRegistrationHandler;
import funkyflamingos.bisonfit.logic.WaterHandler;
import funkyflamingos.bisonfit.logic.WorkoutHandler;

import funkyflamingos.bisonfit.dso.WorkoutHeader;
import funkyflamingos.bisonfit.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity implements AddWorkoutDialog.AddWorkoutDialogListener {
    private CircularProgressIndicator waterTrackerProgress;
    private IWaterHandler waterHandler;
    private IGymHoursHandler gymHoursHandler;
    private TextView lblGreetings;
    private Button btnGymHours;
    private IUserRegistrationHandler userNameHandler;
    private IWorkoutHandler workoutManager;
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

        workoutManager = new WorkoutHandler();
        List<WorkoutHeader> listOfWorkouts = workoutManager.getAllWorkoutHeaders();
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
                        TimeUntilOpenOrClose newStatus = gymHoursHandler.getTimeUntilOpenOrClose();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (newStatus.isOpen()) {
                                    if (newStatus.getDuration() != null)
                                        btnGymHours.setText(getFormattedTime(newStatus.getDuration().toString()) + " Until Closing");
                                    else if (newStatus.getNextDay() > 0)
                                        btnGymHours.setText("Open Till " + GymHoursActivity.dayIDToString(newStatus.getNextDay()));
                                    else
                                        btnGymHours.setText("Open All Week");
                                } else {
                                    if (newStatus.getDuration() != null)
                                        btnGymHours.setText(getFormattedTime(newStatus.getDuration().toString()) + " Until Opening");
                                    else if (newStatus.getNextDay() > 0)
                                        btnGymHours.setText("Closed Till " + GymHoursActivity.dayIDToString(newStatus.getNextDay()));
                                    else
                                        btnGymHours.setText("Closed All Week");
                                }
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

    private String getGreetingsMessage() {
        return "Hi " + userNameHandler.getUserName() + "!";
    }

    public void openGymHoursActivity(View v) {
        Intent intent = new Intent(this, GymHoursActivity.class);
        startActivity(intent);
    }

    public void toggleEditMode(View v) {
        Button editButton = (Button) v;
        String buttonText = editButton.getText().toString();

        ViewGroup parentView = (ViewGroup) v.getParent();
        RecyclerView recyclerView = parentView.findViewById(R.id.lstMyWorkouts);

        if (buttonText.equals("Edit")) {
            adapter.toggleButtonVisibilities(recyclerView, View.GONE, View.VISIBLE);
            editButton.setText("Done");
        } else if (buttonText.equals("Done")) {
            adapter.toggleButtonVisibilities(recyclerView, View.VISIBLE, View.GONE);
            editButton.setText("Edit");
        }
    }

    @Override
    public void createNewWorkout(String newWorkoutName) {
        workoutManager.addNewWorkout(newWorkoutName);
        adapter.updateWorkoutList(workoutManager.getAllWorkoutHeaders());
    }

    public void newWorkoutDialog(View view) {
        AddWorkoutDialog addWorkoutDialog = new AddWorkoutDialog();
        addWorkoutDialog.show(getSupportFragmentManager(), "Add Workout Dialog");
    }

    public void viewPreviousWorkoutsButtonClicked(View v) {
        Intent intent = new Intent(HomePageActivity.this, PreviousWorkoutsActivity.class);
        startActivity(intent);
    }

    // converts Duration.toString() to human readable
    private static String getFormattedTime(String durationToString) {
        String output = durationToString.substring(2);

        if (output.contains("M")) {
            output = output.replaceAll("M[0-9.S]*", "m").toLowerCase().replaceAll("h", "h ");
        } else {
            if (output.contains("H")) {
                output = output.replaceAll("H[0-9.S]*", "h 0m");
            } else {
                output = "<1 Minute";
            }
        }
        return output;
    }


}