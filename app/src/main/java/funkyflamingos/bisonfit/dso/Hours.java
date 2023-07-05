package funkyflamingos.bisonfit.dso;

import java.time.LocalTime;

public class Hours {
    private LocalTime opening;
    private LocalTime closing;

    public Hours(LocalTime opening, LocalTime closing) {
        this.opening = opening;
        this.closing = closing;
    }

    public LocalTime getOpening() {
        return opening;
    }

    public LocalTime getClosing() {
        return closing;
    }
}
