package funkyflamingos.bisonfit.dso;

import androidx.annotation.NonNull;

import java.time.LocalTime;

public class GymHours {
    private LocalTime opening;
    private LocalTime closing;
    private int dayID;

    public GymHours ( int dayID, LocalTime opening, LocalTime closing){
        this.dayID = dayID;
        this.opening = opening;
        this.closing = closing;
    }

    public int getDayID(){
        return dayID;
    }

    public LocalTime getOpening(){
        return opening;
    }

    public LocalTime getClosing(){
        return closing;
    }
}
