package funkyflamingos.bisonfit.dso;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class GymHours {
    private int dayID;

    public static final int DAYS_PER_WEEK = 7;
    private List<Hours> hours;

    public GymHours(int dayID, List<Hours> hours) {
        this.dayID = dayID;
        this.hours = hours;
    }

    public int getDayID() {
        return dayID;
    }

    public List<Hours> getHours() {
        return hours;
    }

    public static int getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().getValue();
    }

    public static int getNextDayOfWeek(int dayOfWeek) {
        dayOfWeek++;
        dayOfWeek = (dayOfWeek - 1) % DAYS_PER_WEEK + 1;
        return dayOfWeek;
    }
}
