package funkyflamingos.bisonfit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.logic.RoutineHandler;

public class RoutineOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_overview);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            RoutineHandler routineHandler = new RoutineHandler();
            int clickedRoutineID = extras.getInt("routineID");
            String clickedRoutineName = routineHandler.getRoutineByID(clickedRoutineID).getHeader().getName();
            getActionBar().setTitle(clickedRoutineName);
        }
    }
}