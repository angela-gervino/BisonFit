package funkyflamingos.bisonfit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.logic.IRoutineHandler;
import funkyflamingos.bisonfit.logic.RoutineHandler;

public class ExerciseSelectionActivity extends AppCompatActivity {
    private int routineID;
    private IRoutineHandler routineHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_selection_page);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            routineID = extras.getInt("routineID");
        }

        routineHandler = new RoutineHandler();

        ExerciseSelectionListAdapter adapter = new ExerciseSelectionListAdapter(routineHandler.getAllExerciseHeaders(), this);

        RecyclerView recyclerView = findViewById(R.id.lstExercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void addExercises(View v) {
        routineHandler.addSelectedExercisesToRoutine(routineHandler.getRoutineByID(routineID).getHeader());

        finish();
    }
}
