package funkyflamingos.bisonfit.ui;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.logic.RoutineHandler;

public class MyWorkoutsListAdapter extends RecyclerView.Adapter<MyWorkoutsListAdapter.ViewHolder> {

    private List<RoutineHeader> localDataSet;
    private Context parentActivity;

    public MyWorkoutsListAdapter(List<RoutineHeader> dataSet, Context parentActivity) {
        localDataSet = dataSet;
        this.parentActivity = parentActivity;
    }

    public void updateWorkoutList(List<RoutineHeader> newWorkouts)
    {
        System.out.println("updating workout list");


        localDataSet = newWorkouts;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_workout_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getLabel().setText(localDataSet.get(position).getName());
        viewHolder.setDataItem(localDataSet.get(position));

        viewHolder.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(parentActivity, RoutineOverviewActivity.class);
                intent.putExtra("routineID", viewHolder.getDataItem().getId());
                parentActivity.startActivity(intent);
            }
        });

        viewHolder.getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                System.out.println("Deleting workout at index " + position);
                localDataSet.remove(position);// I think this can be removed once the deleteRoutine() method is implemented in
                //the RoutineHandler

                RoutineHandler routineHandler = new RoutineHandler();
                routineHandler.deleteRoutine(localDataSet.get(position).getId());
                notifyDataSetChanged();

                //updateWorkoutList(localDataSet);
            }
        });
    }

    public void toggleButtonVisibilities(RecyclerView recyclerView, int arrowImageVisibility, int deleteWorkoutVisibility)
    {
        int itemCount = recyclerView.getAdapter().getItemCount();

        for (int i = 0; i < itemCount; i++) {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
            if (viewHolder != null) {
                View deleteWorkoutButton = viewHolder.itemView.findViewById(R.id.deleteWorkoutButton);
                View arrowImage = viewHolder.itemView.findViewById(R.id.imgArrow);

                arrowImage.setVisibility(arrowImageVisibility);
                deleteWorkoutButton.setVisibility(deleteWorkoutVisibility);
            }
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


    //ViewHolder object holds the individual UI item to display and interact with
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RoutineHeader dataItem;
        private final TextView lblWorkout;
        private final ConstraintLayout layout;
        private final ImageButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            lblWorkout = view.findViewById(R.id.lblWorkoutName);
            layout = view.findViewById(R.id.workout_item_layout);
            deleteButton = view.findViewById(R.id.deleteWorkoutButton);
        }

        public void setDataItem(RoutineHeader dataItem) {
            this.dataItem = dataItem;
        }

        public RoutineHeader getDataItem() {
            return dataItem;
        }
        public TextView getLabel() {
            return lblWorkout;
        }

        public ConstraintLayout getLayout() {
            return layout;
        }

        public ImageButton getDeleteButton()
        {
            return deleteButton;
        }
    }
}
