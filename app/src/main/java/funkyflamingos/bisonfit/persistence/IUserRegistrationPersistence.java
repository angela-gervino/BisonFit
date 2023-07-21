package funkyflamingos.bisonfit.persistence;

public interface IUserRegistrationPersistence {

    // returns null if user is not registered
    void setUserName(String userName);

    String getUserName();

    void clearTable();
}
