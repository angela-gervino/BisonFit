package funkyflamingos.bisonfit.logic;

import android.content.Context;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;
import funkyflamingos.bisonfit.persistence.IUserRegistrationPersistence;

public class UserRegistrationHandler implements IUserRegistrationHandler {

    private IUserRegistrationPersistence persistence;

    public UserRegistrationHandler(IUserRegistrationPersistence persistence) {
        this.persistence = persistence;
    }

    public UserRegistrationHandler(){
        persistence = Services.getUserRegistrationPersistence();
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
