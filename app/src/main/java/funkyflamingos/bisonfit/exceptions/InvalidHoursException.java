package funkyflamingos.bisonfit.exceptions;

public class InvalidHoursException extends RuntimeException {
    public InvalidHoursException(String error) {
        super("Invalid Hours: \n" + error);
    }
}
