package funkyflamingos.bisonfit.logic.exceptions;

public class HoursEmptyException extends InvalidHoursException {
    public HoursEmptyException(String error) {
        super("Lits of hours is empty: \n" + error);
    }
}
