package funkyflamingos.bisonfit.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import funkyflamingos.bisonfit.R;
import funkyflamingos.bisonfit.dso.Exercise;
import funkyflamingos.bisonfit.dso.WorkoutHeader;

public class ActiveWorkoutSetListAdapter extends RecyclerView.Adapter<ActiveWorkoutSetListAdapter.ViewHolder> {

    private Exercise localDataSet;
    private Context parentActivity;

    public ActiveWorkoutSetListAdapter(Exercise dataSet, Context parentActivity) {
        localDataSet = dataSet;
        this.parentActivity = parentActivity;
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
        holder.setTxtSetNum(localDataSet.getSet(position).getSetNumber());
    }

    @Override
    public int getItemCount() {
        return localDataSet.getAllSets().size();
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

        public String getField1Input() {
            return txtField1.getText().toString();
        }

        public String getField2Input() {
            return txtFiled2.getText().toString();
        }
    }
}
