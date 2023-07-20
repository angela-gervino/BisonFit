package funkyflamingos.bisonfit.ui;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import javax.xml.transform.dom.DOMLocator;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.Exercise;
import funkyflamingos.bisonfit.exceptions.InvalidGymHoursException;

public class ActiveWorkoutSetListAdapter extends RecyclerView.Adapter<ActiveWorkoutSetListAdapter.ViewHolder> {

    private final Exercise localExercise;

    public ActiveWorkoutSetListAdapter(Exercise dataSet) {
        localExercise = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.active_workout_set_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setTxtSetNum(position + 1);

        holder.getTxtField1().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int position = holder.getAdapterPosition();
                double newWeight;
                try { // if there is an error converting the number, just set it to 0
                    newWeight = Double.parseDouble(charSequence.toString());
                } catch (NumberFormatException e) {
                    newWeight = 0;
                }
                localExercise.getSet(position).setWeight(newWeight);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        holder.getTxtFiled2().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int position = holder.getAdapterPosition();
                int newReps;
                try { // if there is an error converting the number, just set it to 0
                    newReps = Integer.parseInt(charSequence.toString());
                } catch (NumberFormatException e) {
                    newReps = 0;
                }
                localExercise.getSet(position).setReps(newReps);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    public int getItemCount() {
        return localExercise.getAllSets().size();
    }

    //ViewHolder object holds the individual UI item to display and interact with
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtSetNum;
        private final EditText txtField1, txtFiled2;


        public ViewHolder(View view) {
            super(view);
            txtSetNum = view.findViewById(R.id.lblActiveWorkoutSetNum);
            txtField1 = view.findViewById(R.id.txtActiveWorkoutField1);
            txtFiled2 = view.findViewById(R.id.txtActiveWorkoutField2);
        }

        public void setTxtSetNum(int num) {
            txtSetNum.setText(Integer.toString(num));
        }

        public EditText getTxtField1() {
            return txtField1;
        }

        public EditText getTxtFiled2() {
            return txtFiled2;
        }
    }
}
