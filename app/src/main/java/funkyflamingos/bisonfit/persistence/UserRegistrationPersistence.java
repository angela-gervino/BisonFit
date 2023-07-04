package funkyflamingos.bisonfit.persistence;

import android.content.Context;
import android.content.SharedPreferences;

public class UserRegistrationPersistence implements IUserRegistrationPersistence {

    SharedPreferences storage;
    Context context;
    private final String KEY;

    public UserRegistrationPersistence(Context context) {
        this.context = context;
        storage = context.getSharedPreferences("registration", Context.MODE_PRIVATE);
        KEY = "USER_NAME";
    }

    // constructor used for testing purposes
    public UserRegistrationPersistence(Context context, String key) {
        this.context = context;
        storage = context.getSharedPreferences("registration", Context.MODE_PRIVATE);
        KEY = key;
    }

    @Override
    public void setUserName(String userName) {
        SharedPreferences.Editor editor = storage.edit();
        editor.putString(KEY, userName);
        editor.apply();
    }

    @Override
    public String getUserName() {
        return storage.getString(KEY, null);
    }
}
