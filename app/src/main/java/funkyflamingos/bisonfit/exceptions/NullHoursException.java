package funkyflamingos.bisonfit.exceptions;

public class NullHoursException extends NullPointerException {
    public NullHoursException(String error) {
        super("Hours is null: \n" + error);
    }
}
