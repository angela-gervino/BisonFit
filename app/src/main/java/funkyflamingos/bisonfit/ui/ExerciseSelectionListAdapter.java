package funkyflamingos.bisonfit.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.ExerciseHeader;

public class ExerciseSelectionListAdapter extends RecyclerView.Adapter<ExerciseSelectionListAdapter.ViewHolder> {
    private List<ExerciseHeader> localDataSet;

    public ExerciseSelectionListAdapter(List<ExerciseHeader> dataSet, Context parentActivity) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.exercise_selection_page_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getExerciseName().setText(localDataSet.get(position).getName());
        viewHolder.getSetCountText().setText(localDataSet.get(position).getSetCountText());

        ExerciseHeader exercise = localDataSet.get(position);

        viewHolder.getAddSetButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise.incrementSet();
                viewHolder.getSetCountText().setText(exercise.getSetCountText());
            }
        });

        viewHolder.getSubtractSetButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise.decrementSet();
                viewHolder.getSetCountText().setText(exercise.getSetCountText());
            }
        });

        viewHolder.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise.toggleSelected();
                viewHolder.getLayout().setBackgroundResource(exercise.isSelected() ? R.drawable.card_background_selected : R.drawable.card_background);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layout;
        private ImageButton addSet;
        private ImageButton subtractSet;
        private TextView exerciseName;
        private TextView setCountText;
        private ImageButton exerciseImage;

        public ViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.exercise_item_layout);
            addSet = view.findViewById(R.id.addSet);
            subtractSet = view.findViewById(R.id.subtractSet);
            exerciseName = view.findViewById(R.id.exerciseName);
            setCountText = view.findViewById(R.id.setCountText);
            exerciseImage = view.findViewById(R.id.exerciseImage);
        }

        public ConstraintLayout getLayout() {
            return layout;
        }

        public ImageButton getAddSetButton() {
            return addSet;
        }

        public ImageButton getSubtractSetButton() {
            return subtractSet;
        }

        public TextView getExerciseName() {
            return exerciseName;
        }

        public TextView getSetCountText() {
            return setCountText;
        }
    }
}


