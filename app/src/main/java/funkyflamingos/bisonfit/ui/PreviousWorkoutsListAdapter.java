package funkyflamingos.bisonfit.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.List;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.PerformedWorkoutHeader;

public class PreviousWorkoutsListAdapter extends RecyclerView.Adapter<PreviousWorkoutsListAdapter.ViewHolder> {
    private final List<PerformedWorkoutHeader> performedWorkoutHeaderList;
    private final Context parentActivity;

    public PreviousWorkoutsListAdapter(List<PerformedWorkoutHeader> dataSet, Context parentActivity) {
        performedWorkoutHeaderList = dataSet;
        this.parentActivity = parentActivity;
    }

    private String capitalizeFirstLetterOnly(String input) {
        if (input.length() > 0) {
            char[] output = input.toLowerCase().toCharArray();
            output[0] = Character.toUpperCase(output[0]);

            return new String(output);
        } else
            return input;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.previous_workout_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.setWorkoutName(performedWorkoutHeaderList.get(position).getName());
        viewHolder.setPerformedWorkoutID(performedWorkoutHeaderList.get(position).getId());

        LocalDateTime dateStarted = performedWorkoutHeaderList.get(position).getDateStarted();
        String dateStartedString = capitalizeFirstLetterOnly(dateStarted.getDayOfWeek().toString()) + ", "
                + capitalizeFirstLetterOnly(dateStarted.getMonth().toString()) + " "
                + dateStarted.getDayOfMonth() + ", "
                + dateStarted.getYear();
        viewHolder.setWorkoutDate(dateStartedString);

        viewHolder.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parentActivity, ActiveWorkoutActivity.class);
                intent.putExtra("workoutID", viewHolder.getPerformedWorkoutID());
                intent.putExtra("previous", true);
                parentActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return performedWorkoutHeaderList.size();
    }


    //ViewHolder object holds the individual UI item to display and interact with
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private int performedWorkoutID;
        private final TextView lblWorkoutName;
        private final TextView lblWorkoutDate;
        private final ConstraintLayout layout;

        public ViewHolder(View view) {
            super(view);
            lblWorkoutName = view.findViewById(R.id.lblPrevWorkoutName);
            lblWorkoutDate = view.findViewById(R.id.lblPrevWorkoutDate);
            layout = view.findViewById(R.id.layoutPrevWorkoutListItem);
        }

        public void setPerformedWorkoutID(int id) {
            performedWorkoutID = id;
        }

        public int getPerformedWorkoutID() {
            return performedWorkoutID;
        }

        public void setWorkoutName(String name) {
            lblWorkoutName.setText(name);
        }

        public void setWorkoutDate(String dateStringToDisplay) {
            lblWorkoutDate.setText(dateStringToDisplay);
        }

        public ConstraintLayout getLayout() {
            return layout;
        }
    }
}
