package funkyflamingos.bisonfit.dso;

import static funkyflamingos.bisonfit.application.Constants.DAYS_PER_WEEK;


import java.time.LocalDate;
import java.util.List;

public class GymHours {
    private int dayID;

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
