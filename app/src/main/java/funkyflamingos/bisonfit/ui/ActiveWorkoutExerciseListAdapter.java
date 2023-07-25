package funkyflamingos.bisonfit.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.Exercise;
import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;

public class ActiveWorkoutExerciseListAdapter extends RecyclerView.Adapter<ActiveWorkoutExerciseListAdapter.ViewHolder> {

    private final Workout workout;
    private final Context parentActivity;
    private final boolean showPreviousWorkoutData;

    public ActiveWorkoutExerciseListAdapter(Workout dataSet, Context parentActivity, boolean previousWorkout) {
        workout = dataSet;
        this.parentActivity = parentActivity;
        showPreviousWorkoutData = previousWorkout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.active_workout_exercise_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getLabel().setText(workout.getExercise(position).getName());
        holder.setUpRecyclerView(parentActivity, workout.getExercise(position), showPreviousWorkoutData);
    }

    @Override
    public int getItemCount() {
        return workout.getAllExercises().size();
    }

    //ViewHolder object holds the individual UI item to display and interact with
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblExerciseName;
        private final RecyclerView rv;

        public ViewHolder(View view) {
            super(view);
            lblExerciseName = view.findViewById(R.id.lblWorkoutOverviewExerciseName);
            rv = view.findViewById(R.id.rvSetList);
        }

        public TextView getLabel() {
            return lblExerciseName;
        }

        public void setUpRecyclerView(Context context, Exercise exercise, boolean previousWorkout) {
            rv.setLayoutManager(new LinearLayoutManager(context));
            rv.setAdapter(new ActiveWorkoutSetListAdapter(exercise, previousWorkout));
        }
    }
}
