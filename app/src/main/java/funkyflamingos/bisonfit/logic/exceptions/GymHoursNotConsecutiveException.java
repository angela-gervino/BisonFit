package funkyflamingos.bisonfit.logic.exceptions;

public class GymHoursNotConsecutiveException extends InvalidGymHoursException {
    public GymHoursNotConsecutiveException(String error) {
        super("GymHours are not consecutive days: \n");
    }
}
