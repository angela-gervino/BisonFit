package funkyflamingos.bisonfit.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import funkyflamingos.bisonfit.R;

public class AddWorkoutDialog extends AppCompatDialogFragment {
    private EditText newWorkoutName;
    private AddWorkoutDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.add_workout_dialog, null);

        newWorkoutName = view.findViewById(R.id.new_workout_name);

        dialogBuilder.setView(view)
                .setTitle("Add a new workout")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("CREATE WORKOUT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String workoutName = newWorkoutName.getText().toString();
                        listener.createNewWorkout(workoutName);
                    }
                });

        return dialogBuilder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddWorkoutDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "AddWorkoutDialogListener must be implemented");
        }
    }

    public interface AddWorkoutDialogListener {
        void createNewWorkout(String newWorkoutName);
    }
}
