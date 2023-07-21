package funkyflamingos.bisonfit.dso;

import java.time.Duration;

public class TimeUntilOpenOrClose {
    private Duration duration;
    private boolean isOpen;
    private int nextDay;

    public TimeUntilOpenOrClose() {
        this.duration = null;
        this.isOpen = false;
        this.nextDay = -1;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getNextDay() {
        return nextDay;
    }

    public void setNextDay(int nextDay) {
        this.nextDay = nextDay;
    }
}
