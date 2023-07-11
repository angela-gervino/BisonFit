package funkyflamingos.bisonfit.logic.exceptions;

public class InvalidGymHoursException extends RuntimeException {
    public InvalidGymHoursException(String message) {
        super("Invalid GymHours: \n" + message);
    }

}
