package funkyflamingos.bisonfit.logic.exceptions;

public class InvalidHoursException extends RuntimeException {
    public InvalidHoursException(String error) {
        super("Invalid Hours: \n" + error);
    }
}
