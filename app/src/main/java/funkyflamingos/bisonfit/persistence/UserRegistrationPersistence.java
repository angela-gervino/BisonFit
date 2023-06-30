package funkyflamingos.bisonfit.persistence;

import android.content.Context;
import android.content.SharedPreferences;

public class UserRegistrationPersistence implements IUserRegistrationPersistence {

    SharedPreferences storage;
    Context context;
    private final String KEY = "USER_NAME";

    public UserRegistrationPersistence(Context context) {
        this.context = context;
        storage = context.getSharedPreferences("registration", Context.MODE_PRIVATE);
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
