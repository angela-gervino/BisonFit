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

    private Workout workout;
    private Context parentActivity;

    public ActiveWorkoutExerciseListAdapter(Workout dataSet, Context parentActivity) {
        workout = dataSet;
        this.parentActivity = parentActivity;
    }

    public Workout getWorkoutObject() {
        return workout;
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
        holder.setDataItem(workout.getHeader());
        holder.getLabel().setText(workout.getExercise(position).getName());
        holder.setUpRecyclerView(parentActivity, workout.getExercise(position));
    }

    @Override
    public int getItemCount() {
        return workout.getAllExercises().size();
    }

    //ViewHolder object holds the individual UI item to display and interact with
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private WorkoutHeader dataItem;
        private final TextView lblExerciseName;
        private final RecyclerView rv;

        public ViewHolder(View view) {
            super(view);
            lblExerciseName = view.findViewById(R.id.lblWorkoutOverviewExerciseName);
            rv = view.findViewById(R.id.rvSetList);
        }

        public void setDataItem(WorkoutHeader dataItem) {
            this.dataItem = dataItem;
        }

        public WorkoutHeader getDataItem() {
            return dataItem;
        }
        public TextView getLabel() {
            return lblExerciseName;
        }

        public void setUpRecyclerView(Context context, Exercise exercise) {
            rv.setLayoutManager(new LinearLayoutManager(context));
            rv.setAdapter(new ActiveWorkoutSetListAdapter(exercise));
        }

    }
}
