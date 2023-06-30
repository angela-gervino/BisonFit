package funkyflamingos.bisonfit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.logic.IUserNameHandler;
import funkyflamingos.bisonfit.logic.UserNameHandler;

public class WelcomeActivity extends AppCompatActivity {

    private IUserNameHandler userNameHandler;
    private String name;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        userNameHandler = new UserNameHandler(this);
        editText = findViewById(R.id.nameEditText);

        if (userNameHandler.userHasRegistered()) {
            startHomePageActivity();
        }
        else {
        }
    }

    public void btnStartClicked(View v) {
        name = editText.getText().toString();
        if (!name.equals("")) {
            userNameHandler.setUserName(name);
            startHomePageActivity();
        }
    }

    private void startHomePageActivity() {
        Intent intent = new Intent(WelcomeActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}