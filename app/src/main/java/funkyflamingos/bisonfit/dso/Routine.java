package funkyflamingos.bisonfit.dso;

import java.util.Date;

public class Routine {
    RoutineHeader header;

    // will add more fields later

    public Routine(RoutineHeader header) {
        this.header = header;
    }

    public RoutineHeader getHeader() {
        return header;
    }
}
