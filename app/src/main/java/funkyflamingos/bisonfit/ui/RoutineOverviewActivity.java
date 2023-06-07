package funkyflamingos.bisonfit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import funkyflamingos.bisonfit.R;

public class RoutineOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_overview);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            getActionBar().setTitle(extras.getString("routineName"));
        }
    }
}