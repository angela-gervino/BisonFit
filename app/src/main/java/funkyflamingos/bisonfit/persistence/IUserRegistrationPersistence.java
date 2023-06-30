package funkyflamingos.bisonfit.persistence;

public interface IUserRegistrationPersistence {

    // returns null if user is not registered
    public void setUserName(String userName);
    public String getUserName();
}
