package funkyflamingos.bisonfit.dso;

public class ExerciseSet {
    private double weight;
    private int reps;
    private int setNumber;

    public ExerciseSet (double weight, int reps, int setNumber) {
        this.weight = weight;
        this.reps = reps;
        this.setNumber = setNumber;
    }

    public double getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }

    public int getSetNumber() {
        return  setNumber;
    }
}
