package funkyflamingos.bisonfit.persistence;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.RoutineHeader;

public interface ISavedRoutineExercises {
    public ArrayList<ExerciseHeader> getExercisesByRoutine(RoutineHeader routineHeader);

    public void addExercises(ArrayList<ExerciseHeader> exerciseHeaders, RoutineHeader routineHeader);

    public void deleteExercise(ExerciseHeader exerciseHeader, RoutineHeader routineHeader);

    public void deleteRoutine(RoutineHeader routineHeader);

    public int getNumSets(RoutineHeader routineHeader);
}
