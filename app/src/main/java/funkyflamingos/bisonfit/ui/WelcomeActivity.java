package funkyflamingos.bisonfit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.logic.IUserRegistrationHandler;
import funkyflamingos.bisonfit.logic.UserRegistrationHandler;
import funkyflamingos.bisonfit.persistence.utils.DBHelper;

public class WelcomeActivity extends AppCompatActivity {

    private IUserRegistrationHandler userNameHandler;
    private EditText editText;
    private Button button;
    private TextView welcomeLbl2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        DBHelper.copyDatabaseToDevice(this);
        userNameHandler = new UserRegistrationHandler();
        editText = findViewById(R.id.nameEditText);
        button = findViewById(R.id.btnStartWelcomeActivity);
        welcomeLbl2 = findViewById(R.id.lblWelcome2);

        if (userNameHandler.userHasRegistered()) {
            startHomePageActivity();
        } else {
            editText.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            welcomeLbl2.setVisibility(View.VISIBLE);
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
        String name = editText.getText().toString();
        if (userNameHandler.setUserName(name)) {
            startHomePageActivity();
        }
    }

    private void startHomePageActivity() {
        Intent intent = new Intent(WelcomeActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}