package funkyflamingos.bisonfit.logic;

import android.content.Context;

import funkyflamingos.bisonfit.persistence.IUserRegistrationPersistence;
import funkyflamingos.bisonfit.persistence.UserRegistrationPersistence;

public class UserNameHandler implements IUserNameHandler {

    private IUserRegistrationPersistence persistence;
    public UserNameHandler(Context context) {
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
