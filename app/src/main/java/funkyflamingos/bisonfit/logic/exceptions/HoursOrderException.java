package funkyflamingos.bisonfit.logic.exceptions;

public class HoursOrderException extends InvalidHoursException {
    public HoursOrderException(String error) {
        super("Hours are not in order: \n" + error);
    }
}
