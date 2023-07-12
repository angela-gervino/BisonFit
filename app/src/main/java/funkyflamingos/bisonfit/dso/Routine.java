package funkyflamingos.bisonfit.dso;

import java.util.ArrayList;

public class Routine {
    private RoutineHeader header;

    public Routine(RoutineHeader header) {
        this.header = header;
    }

    public RoutineHeader getHeader() {
        return header;
    }

    public String getName() {
        return header.getName();
    }
}
