package funkyflamingos.bisonfit.persistence.stubs;

import funkyflamingos.bisonfit.persistence.IUserRegistrationPersistence;

public class UserRegistrationPersistenceStub implements IUserRegistrationPersistence {

    String name = null;

    public UserRegistrationPersistenceStub() {

    }

    @Override
    public void setUserName(String userName) {
        name = userName;
    }

    @Override
    public String getUserName() {
        return name;
    }
}
