package funkyflamingos.bisonfit.ui;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.dso.Hours;
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
            List<GymHours> gymSchedule = gymHoursHandler.getGymSchedule();
            schedule.setText(getPrintableSchedule(gymSchedule));
        } catch (Exception e) {
            schedule.setText(e.getMessage());
        }
    }

    // builds a string of the schedule to be displayed
    private static String getPrintableSchedule(List<GymHours> nextWeekHours) {
        StringBuilder printableSchedule = new StringBuilder();
        printableSchedule.append("**Today** ");
        for (GymHours gymHours : nextWeekHours) {
            int dayID = gymHours.getDayID();
            printableSchedule.append(dayIDToString(dayID));

            printableSchedule.append("\n");

            List<Hours> hoursList = gymHours.getHours();
            if (hoursList != null) {
                for (Hours hours : hoursList) {
                    printableSchedule.append(getClockFormattedTime(hours.getOpening(), false));
                    printableSchedule.append(" - ");
                    printableSchedule.append(getClockFormattedTime(hours.getClosing(), true));
                    printableSchedule.append("\n");
                }
            } else {
                printableSchedule.append("\t");
                printableSchedule.append("Closed\n");
            }
            printableSchedule.append("\n");
        }
        return printableSchedule.toString();
    }

    // Converts 1 to "Monday", 2 to "Tuesday", etc
    public static String dayIDToString(int dayID) {
        String dayOfWeek = DayOfWeek.of(dayID).toString();
        dayOfWeek = dayOfWeek.charAt(0) + dayOfWeek.substring(1).toLowerCase();
        return dayOfWeek;
    }

    // converts LocalTime to 24 hour human readable: hh:mm
    private static String getClockFormattedTime(LocalTime time, boolean isClosingTime) {
        String formattedTime = "";
        String hour = String.valueOf(time.getHour());

        if (isClosingTime && hour.equals("0")) {
            formattedTime += "24";
        } else {
            if (hour.length() < 2)
                formattedTime += " ";
            formattedTime += time.getHour();
        }

        formattedTime += ":";
        String minute = String.valueOf(time.getMinute());
        if (minute.length() < 2)
            formattedTime += "0";
        formattedTime += minute;
        return formattedTime;
    }
}