package funkyflamingos.bisonfit.logic;

import android.content.Context;

import funkyflamingos.bisonfit.persistence.IUserRegistrationPersistence;
import funkyflamingos.bisonfit.persistence.UserRegistrationPersistence;

public class UserRegistrationHandler implements IUserRegistrationHandler {

    private IUserRegistrationPersistence persistence;
    public UserRegistrationHandler(Context context) {
        persistence = new UserRegistrationPersistence(context);
    }

    @Override
    public boolean userHasRegistered() {
        return (persistence.getUserName() != null);
    }

    @Override
    public void setUserName(String userName) {
        persistence.setUserName(userName);
    }

    @Override
    public String getUserName() {
        return persistence.getUserName();
    }
}
