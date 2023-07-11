package funkyflamingos.bisonfit.exceptions;

public class GymHoursNotStartTodayException extends InvalidGymHoursException {
    public GymHoursNotStartTodayException(String error) {
        super("Gym hours do not start today: \n" + error);
    }
}
