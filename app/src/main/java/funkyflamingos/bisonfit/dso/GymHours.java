package funkyflamingos.bisonfit.dso;

import androidx.annotation.NonNull;

import java.time.LocalTime;
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
}
