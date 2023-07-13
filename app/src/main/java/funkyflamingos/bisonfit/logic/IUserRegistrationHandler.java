package funkyflamingos.bisonfit.logic;

public interface IUserRegistrationHandler {

    boolean userHasRegistered();

    boolean setUserName(String userName);

    String getUserName();

    boolean userNameValid(String userName);
}
