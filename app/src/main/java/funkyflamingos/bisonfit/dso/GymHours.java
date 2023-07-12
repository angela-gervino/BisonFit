package funkyflamingos.bisonfit.dso;

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
}
