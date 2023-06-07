package funkyflamingos.bisonfit.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.RoutineHeader;

public class MyWorkoutsListAdapter extends RecyclerView.Adapter<MyWorkoutsListAdapter.ViewHolder> {

    private List<RoutineHeader> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblWorkout;

        public ViewHolder(View view) {
            super(view);
            //TODO: define click listener for lblWorkout
            lblWorkout = view.findViewById(R.id.lblWorkoutName);
        }

        public TextView getLabel() {
            return lblWorkout;
        }

    }

    public MyWorkoutsListAdapter(List<RoutineHeader> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_workout_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getLabel().setText(localDataSet.get(position).getRoutineName());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
