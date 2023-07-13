package funkyflamingos.bisonfit.logic;

import funkyflamingos.bisonfit.application.Constants;
import funkyflamingos.bisonfit.persistence.IUserRegistrationPersistence;
import funkyflamingos.bisonfit.persistence.hsqldb.UserRegistrationPersistenceHSQLDB;
import funkyflamingos.bisonfit.utils.TestUtils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import java.io.File;
import java.io.IOException;

public class UserRegistrationIT {
    private UserRegistrationHandler userRegistrationHandler;
    private File tempDB;

    @Before
    public void setup() throws IOException {
        tempDB = TestUtils.copyDB();
        final IUserRegistrationPersistence persistence = new UserRegistrationPersistenceHSQLDB(tempDB.getAbsolutePath().replace(".script", ""));
        userRegistrationHandler = new UserRegistrationHandler(persistence);
    }

    @After
    public void tearDown() {
        tempDB.delete();
    }

    @Test
    public void testNotRegistered() {
        boolean registered = userRegistrationHandler.userHasRegistered();
        assertFalse(registered);
    }

    @Test
    public void testRegistrationStatusSaves() {
        userRegistrationHandler.setUserName(getNameProperLengthMedium('c'));
        boolean registered = userRegistrationHandler.userHasRegistered();
        assertTrue(registered);
    }
    @Test
    public void testRegistrationReturnStatus() {
        boolean nameSet = userRegistrationHandler.setUserName(getNameProperLengthMedium('c'));
        assertTrue(nameSet);
    }

    @Test
    public void testRegistrationStatusStillSavedAfterSecondTime() {
        userRegistrationHandler.setUserName(getNameProperLengthMedium('i'));
        userRegistrationHandler.setUserName(getNameProperLengthMedium('l'));
        boolean registered = userRegistrationHandler.userHasRegistered();
        assertTrue(registered);
    }

    @Test
    public void testRegistrationReturnAfterSecondTime() {
        userRegistrationHandler.setUserName(getNameProperLengthMedium('i'));
        boolean registered =  userRegistrationHandler.setUserName(getNameProperLengthMedium('l'));
        assertTrue(registered);
    }

    @Test
    public void testNoNameBeforeRegistration() {
        String userName = userRegistrationHandler.getUserName();
        assertNull(userName);
    }

    @Test
    public void testNameSavesAtRegistration() {
        String userNameExpected = getNameProperLengthMedium('e');
        userRegistrationHandler.setUserName(userNameExpected);
        String userName = userRegistrationHandler.getUserName();
        assertEquals(userNameExpected, userName);
    }

    @Test
    public void testNameUpdatedAfterSecondSet() {
        String userNameExpected = getNameProperLengthMedium('1');
        userRegistrationHandler.setUserName(getNameProperLengthMedium('2'));
        userRegistrationHandler.setUserName(userNameExpected);
        String userName = userRegistrationHandler.getUserName();
        assertEquals(userNameExpected, userName);
    }

    @Test
    public void testNameTooLongReturnValue() {
        boolean nameSet = userRegistrationHandler.setUserName(getNameTooLong());
        assertFalse(nameSet);
    }

    @Test
    public void testNameTooLongDoesNotSave() {
        userRegistrationHandler.setUserName(getNameTooLong());
        String userName = userRegistrationHandler.getUserName();
        assertNull(userName);
    }

    @Test
    public void testRegistrationStatusNameTooLong() {
        userRegistrationHandler.setUserName(getNameTooLong());
        boolean registered = userRegistrationHandler.userHasRegistered();
        assertFalse(registered);
    }

    @Test
    public void testNameTooShortReturnValue() {
        boolean nameSet = userRegistrationHandler.setUserName(getNameTooShort());
        assertFalse(nameSet);
    }

    @Test
    public void testNameTooShortDoesNotSave() {
        userRegistrationHandler.setUserName(getNameTooShort());
        String userName = userRegistrationHandler.getUserName();
        assertNull(userName);
    }

    @Test
    public void testRegistrationStatusNameTooShort() {
        userRegistrationHandler.setUserName(getNameTooShort());
        boolean registered = userRegistrationHandler.userHasRegistered();
        assertFalse(registered);
    }

    @Test
    public void testShortName() {
        String username = getNameProperLengthShort('s');
        userRegistrationHandler.setUserName(username);
        String result = userRegistrationHandler.getUserName();
        assertEquals(username, result);
    }

    @Test
    public void testLongName() {
        String username = getNameProperLengthLong('l');
        userRegistrationHandler.setUserName(username);
        String result = userRegistrationHandler.getUserName();
        assertEquals(username, result);
    }

    @Test
    public void testUserNameValidProperMedium() {
        String username = getNameProperLengthMedium('m');
        boolean valid = userRegistrationHandler.userNameValid(username);
        assertTrue(valid);
    }

    @Test
    public void testUserNameValidProperLong() {
        String username = getNameProperLengthLong('s');
        boolean valid = userRegistrationHandler.userNameValid(username);
        assertTrue(valid);
    }

    @Test
    public void testUserNameValidProperShort() {
        String username = getNameProperLengthLong('l');
        boolean valid = userRegistrationHandler.userNameValid(username);
        assertTrue(valid);
    }

    @Test
    public void testUserNameTooShort() {
        String username = getNameTooShort();
        boolean valid = userRegistrationHandler.userNameValid(username);
        assertFalse(valid);
    }

    @Test
    public void testUserNameTooLong() {
        String username = getNameTooLong();
        boolean valid = userRegistrationHandler.userNameValid(username);
        assertFalse(valid);
    }

    @Test
    public void testUserNameAllSpaces() {
        String username = getNameProperLengthMedium(' ');
        userRegistrationHandler.setUserName(username);
        String result = userRegistrationHandler.getUserName();
        assertNull(result);
    }

    @Test
    public void testUserNameTooLongBecauseOfSpaces() {
        StringBuilder username = new StringBuilder(getNameProperLengthLong(' '));
        for(int i = 0; i < Constants.MIN_USERNAME_LENGTH + 1; i++) {
            username.append('n');
        }
        for(int i = 0; i < Constants.MAX_USERNAME_LENGTH; i++) {
            username.append(' ');
        }

        boolean result = userRegistrationHandler.setUserName(username.toString());
        assertTrue(result);
    }

    private String getNameTooLong() {
        StringBuilder username = new StringBuilder();
        for(int i = 0; i < Constants.MAX_USERNAME_LENGTH; i++) {
            username.append("i");
        }
        username.append("i");

        return username.toString();
    }

    private String getNameTooShort() {
        StringBuilder username = new StringBuilder("");
        for(int i = 0; (i < Constants.MIN_USERNAME_LENGTH - 1); i++) {
            username.append("i");
        }

        return username.toString();
    }

    private String getNameProperLengthShort(char c) {
        StringBuilder username = new StringBuilder("");
        for(int i = 0; i < Constants.MIN_USERNAME_LENGTH; i++) {
            username.append(c);
        }

        return username.toString();
    }

    private String getNameProperLengthLong(char c) {
        StringBuilder username = new StringBuilder("");
        for(int i = 0; i < Constants.MAX_USERNAME_LENGTH; i++) {
            username.append(c);
        }

        return username.toString();
    }

    private String getNameProperLengthMedium(char c) {
        StringBuilder username = new StringBuilder("");
        int length = Constants.MIN_USERNAME_LENGTH +
                ((Constants.MAX_USERNAME_LENGTH - Constants.MIN_USERNAME_LENGTH) / 2);
        for(int i = 0; i < length; i++) {
            username.append(c);
        }

        return username.toString();
    }
}
