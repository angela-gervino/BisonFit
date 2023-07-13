package funkyflamingos.bisonfit.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.WorkoutHeader;
import funkyflamingos.bisonfit.persistence.ISavedWorkoutExercises;

public class SavedWorkoutExercisesPersistenceHSQLDB implements ISavedWorkoutExercises {
    private final String dbPath;

    public SavedWorkoutExercisesPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public ArrayList<ExerciseHeader> getExercisesByWorkout(WorkoutHeader workoutHeader) {
        ArrayList<ExerciseHeader> exercisesInOrder = new ArrayList<>();
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM SAVEDWORKOUTEXERCISES JOIN EXERCISELOOKUP ON SAVEDWORKOUTEXERCISES.EXERCISEID = EXERCISELOOKUP.ID WHERE WORKOUTID = ? ORDER BY INDEX ASC");
            statement.setInt(1, workoutHeader.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                int id = resultSet.getInt("ID");
                int setCount = resultSet.getInt("NUMSETS");
                int index = resultSet.getInt("INDEX");
                exercisesInOrder.add(new ExerciseHeader(name, id, setCount, index));
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return exercisesInOrder;
    }

    @Override
    public void addExercises(ArrayList<ExerciseHeader> exerciseHeaders, int workoutID) {
        try (Connection connection = connect()) {

            for (int i = 0; i < exerciseHeaders.size(); i++) {
                final PreparedStatement statement = connection.prepareStatement("INSERT INTO SAVEDWORKOUTEXERCISES VALUES(?, ?, DEFAULT, ?)");
                statement.setInt(1, workoutID);
                statement.setInt(2, exerciseHeaders.get(i).getId());
                statement.setInt(3, exerciseHeaders.get(i).getSetCount());
                statement.executeUpdate();
            }

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteExercise(ExerciseHeader exerciseHeader, WorkoutHeader workoutHeader) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM SAVEDWORKOUTEXERCISES WHERE WORKOUTID = ? AND EXERCISEID = ? AND INDEX = ?");
            statement.setInt(1, workoutHeader.getId());
            statement.setInt(2, exerciseHeader.getId());
            statement.setInt(3, exerciseHeader.getIndex());
            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWorkout(WorkoutHeader workoutHeader) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM SAVEDWORKOUTEXERCISES WHERE WORKOUTID = ?");
            statement.setInt(1, workoutHeader.getId());
            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }
}
