package funkyflamingos.bisonfit.exceptions;

public class InvalidGymHoursException extends RuntimeException {
    public InvalidGymHoursException(String message) {
        super("Invalid GymHours: \n" + message);
    }

}
