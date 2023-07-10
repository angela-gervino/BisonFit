package funkyflamingos.bisonfit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.logic.IUserRegistrationHandler;
import funkyflamingos.bisonfit.logic.UserRegistrationHandler;
import funkyflamingos.bisonfit.persistence.utils.DBHelper;

public class WelcomeActivity extends AppCompatActivity {

    private IUserRegistrationHandler userNameHandler;
    private String name;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        DBHelper.copyDatabaseToDevice(this);
        userNameHandler = new UserRegistrationHandler();
        editText = findViewById(R.id.nameEditText);
        button = findViewById(R.id.btnStartWelcomeActivity);

        if (userNameHandler.userHasRegistered()) {
            startHomePageActivity();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String newText = editText.getText().toString();
                button.setEnabled(userNameHandler.userNameValid(newText));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void btnStartClicked(View v) {
        name = editText.getText().toString();
        if (userNameHandler.setUserName(name)) {
            startHomePageActivity();
        }
    }

    private void startHomePageActivity() {
        Intent intent = new Intent(WelcomeActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}