package funkyflamingos.bisonfit.logic.exceptions;

public class GymHoursOverAWeekException extends InvalidGymHoursException {
    public GymHoursOverAWeekException(String error) {
        super("GymHours is over 7 days: \n" + error);
    }
}
