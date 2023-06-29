package funkyflamingos.bisonfit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.logic.RoutineHandler;

public class RoutineOverviewActivity extends AppCompatActivity {

    private Routine routine;

    private TextView workoutNameHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_overview);

        RoutineHandler routineHandler = new RoutineHandler();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int clickedRoutineID = extras.getInt("routineID");
            routine = routineHandler.getRoutineByID(clickedRoutineID);
            String clickedRoutineName = routine.getHeader().getName();
            getActionBar().setTitle(clickedRoutineName);

            workoutNameHeader = findViewById(R.id.lblWorkoutNameHeader);
            workoutNameHeader.setText(clickedRoutineName);
        }

        //List<ExerciseHeader> exerciseList = routine.getAllExerciseHeaders();
        List<ExerciseHeader> exerciseList = new ArrayList<ExerciseHeader>();

        for (int i = 0; i < 50; i++)
            exerciseList.add(new ExerciseHeader("Bicep Curls" + i, i));

        exerciseList.add(new ExerciseHeader("Jumping Jacks", 100));
        exerciseList.add(new ExerciseHeader("Yoga", 101));

        WorkoutOverviewExercisesListAdapter adapter = new WorkoutOverviewExercisesListAdapter(exerciseList, this);
        RecyclerView recyclerView = findViewById(R.id.lstMyExercises);


        //recyclerVew setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void openExerciseSelectionActivity(View v) {
        Intent intent = new Intent(this, ExerciseSelectionActivity.class);
        startActivity(intent);
    }

    public void openActiveWorkoutActivity(View v) {
        Intent intent = new Intent(this, ActiveWorkoutActivity.class);
        startActivity(intent);
    }
}