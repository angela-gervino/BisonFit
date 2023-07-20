package funkyflamingos.bisonfit.dso;

import java.time.Duration;

public class TimeUntilOpenOrClose {
    private Duration timeUntilOpenOrClose;
    private boolean isOpen;
    private int nextDay;

    public TimeUntilOpenOrClose() {
        this.timeUntilOpenOrClose = null;
        this.isOpen = false;
        this.nextDay = -1;
    }

    public Duration getTimeUntilOpenOrClose() {
        return timeUntilOpenOrClose;
    }

    public void setTimeUntilOpenOrClose(Duration timeUntilOpenOrClose) {
        this.timeUntilOpenOrClose = timeUntilOpenOrClose;
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
