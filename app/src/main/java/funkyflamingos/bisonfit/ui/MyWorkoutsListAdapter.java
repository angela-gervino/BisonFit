package funkyflamingos.bisonfit.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.RoutineHeader;

public class MyWorkoutsListAdapter extends RecyclerView.Adapter<MyWorkoutsListAdapter.ViewHolder> {

    private List<RoutineHeader> localDataSet;
    private Context parentActivity;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblWorkout;
        private final ConstraintLayout layout;
        private final Context parentActivity;

        public ViewHolder(View view, Context parentActivity) {
            super(view);
            lblWorkout = view.findViewById(R.id.lblWorkoutName);
            layout = view.findViewById(R.id.workout_item_layout);
            this.parentActivity = parentActivity;
        }

        public TextView getLabel() {
            return lblWorkout;
        }

        public ConstraintLayout getLayout() {
            return layout;
        }
    }

    public MyWorkoutsListAdapter(List<RoutineHeader> dataSet, Context parentActivity) {
        localDataSet = dataSet;
        this.parentActivity = parentActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_workout_list_item, viewGroup, false);
        return new ViewHolder(view, parentActivity);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getLabel().setText(localDataSet.get(position).getName());

        viewHolder.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(parentActivity, RoutineOverviewActivity.class);
                intent.putExtra("routineName",viewHolder.getLabel().getText().toString());
                parentActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
