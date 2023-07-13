package funkyflamingos.bisonfit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.logic.GymHoursHandler;
import funkyflamingos.bisonfit.logic.IGymHoursHandler;

public class GymHoursActivity extends AppCompatActivity {
    private TextView schedule;
    private IGymHoursHandler gymHoursHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_hours);

        gymHoursHandler = new GymHoursHandler();

        schedule = findViewById(R.id.txtGymSchedule);

        try {
            schedule.setText(gymHoursHandler.getGymSchedule());
        } catch (Exception e) {
            schedule.setText(e.getMessage());
        }
    }
}