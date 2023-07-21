package funkyflamingos.bisonfit.persistence.hsqldb;

import funkyflamingos.bisonfit.dso.WorkoutHeader;
import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.persistence.IWorkoutPersistence;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class WorkoutPersistenceHSQLDB implements IWorkoutPersistence {
    private final String dbPath;

    public WorkoutPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public List<WorkoutHeader> getAllWorkoutHeaders() {
        List<WorkoutHeader> workoutHeaders = new ArrayList<>();

        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM WORKOUTS");

            while (resultSet.next()) {
                String name = resultSet.getString("TITLE");
                int id = resultSet.getInt("ID");
                workoutHeaders.add(new WorkoutHeader(name, id));
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }

        return workoutHeaders;
    }

    @Override
    public WorkoutHeader getWorkoutHeaderByID(int workoutID) {
        WorkoutHeader workoutHeader = null;
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM WORKOUTS WHERE WORKOUTS.ID = ?");
            statement.setString(1, Integer.toString(workoutID));
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String workoutName = resultSet.getString("TITLE");
                workoutHeader = new WorkoutHeader(workoutName, workoutID);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return workoutHeader;
    }

    @Override
    public void addWorkout(String name) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO WORKOUTS VALUES (DEFAULT, ?)");
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWorkoutById(int workoutId) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM WORKOUTS WHERE WORKOUTS.ID = ?");
            statement.setInt(1, workoutId);
            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }
}




