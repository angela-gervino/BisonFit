package funkyflamingos.bisonfit.exceptions;

public class NullGymHoursException extends NullPointerException {
    public NullGymHoursException(String error) {
        super("GymHours is null: \n" + error);
    }
}
