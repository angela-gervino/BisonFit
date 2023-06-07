package funkyflamingos.bisonfit.dso;

public class RoutineHeader {

    String routineName;
    int routineID;

    public RoutineHeader(String routineName, int routineID) {
        this.routineName = routineName;
        this.routineID = routineID;
    }

    public String getRoutineName() {
        return routineName;
    }

    public int getRoutineID() {
        return routineID;
    }
}
