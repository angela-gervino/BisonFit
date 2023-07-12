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
import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.logic.RoutineHandler;

public class WorkoutOverviewExercisesListAdapter extends RecyclerView.Adapter<WorkoutOverviewExercisesListAdapter.ViewHolder> {
    private List<ExerciseHeader> localDataSet;
    private Context parentActivity;

    private RoutineHeader routineHeader;

    public WorkoutOverviewExercisesListAdapter(RoutineHeader routineHeader, List<ExerciseHeader> dataSet, Context parentActivity) {
        localDataSet = dataSet;
        this.parentActivity = parentActivity;
        this.routineHeader = routineHeader;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.workout_overview_page_exercise_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getLabel().setText(localDataSet.get(position).getName());
        viewHolder.setDataItem(localDataSet.get(position));
        viewHolder.getSetCount().setText(localDataSet.get(position).getSetCountTextShort());

        // When the delete button (garbage can icon) is clicked on an exercise
        viewHolder.getDeleteExerciseButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                System.out.println("Deleting exercise at index " + position);

                RoutineHandler routineHandler = new RoutineHandler();
                routineHandler.deleteExercise(localDataSet.get(position), routineHeader);

                localDataSet.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void refreshData() {
        RoutineHandler routineHandler = new RoutineHandler();
        routineHandler.unselectAllExercises();
        localDataSet = routineHandler.getExerciseHeaders(routineHeader);
    }


    //ViewHolder object holds the individual UI item to display and interact with
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ExerciseHeader dataItem;
        private final TextView lblExercise;
        private final TextView setCount;
        private final ConstraintLayout layout;

        private ImageButton deleteExerciseButton;

        public ViewHolder(View view) {
            super(view);
            lblExercise = view.findViewById(R.id.lblExerciseName);
            layout = view.findViewById(R.id.exercise_item_layout);
            setCount = view.findViewById(R.id.setCountWorkoutOverview);
            deleteExerciseButton = view.findViewById(R.id.deleteExerciseButton);
        }

        public void setDataItem(ExerciseHeader dataItem) {
            this.dataItem = dataItem;
        }

        public ExerciseHeader getDataItem() {
            return dataItem;
        }
        public TextView getLabel() {
            return lblExercise;
        }

        public ConstraintLayout getLayout() {
            return layout;
        }

        public TextView getSetCount() {
            return setCount;
        }

        public ImageButton getDeleteExerciseButton() {
            return deleteExerciseButton;
        }
    }
}

