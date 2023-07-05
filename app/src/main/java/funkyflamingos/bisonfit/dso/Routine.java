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


    //need a function that returns all exercise headers as an ArrayList

    //need a function that takes an index and removed the exercise header at that index
}
