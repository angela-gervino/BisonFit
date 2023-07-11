package funkyflamingos.bisonfit.logic;

public interface IWaterHandler {
    void increment();

    int getProgress();

    int getGoal();

    boolean reachedGoal();
}
