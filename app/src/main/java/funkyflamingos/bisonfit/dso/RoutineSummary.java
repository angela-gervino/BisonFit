package funkyflamingos.bisonfit.dso;

public class RoutineSummary {

    String routineName;

    int routineID;

    public RoutineSummary(String routineName, int routineID) {
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
