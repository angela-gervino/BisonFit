package funkyflamingos.bisonfit.persistence.stubs;

import funkyflamingos.bisonfit.persistence.IUserRegistrationPersistence;

public class UserRegistrationPersistenceStub implements IUserRegistrationPersistence {
    private String name;

    public UserRegistrationPersistenceStub() {
        name = null;
    }

    @Override
    public void setUserName(String userName) {
        name = userName;
    }

    @Override
    public String getUserName() {
        return name;
    }

    @Override
    public void clear() {
        name = null;
    }
}
