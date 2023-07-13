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
    private  List<Workout> workouts;

    public WorkoutPersistenceHSQLDB(String dbPath ){
        this.dbPath = dbPath;
        this.workouts = new ArrayList<>();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Workout fromResultSet(final ResultSet rs) throws SQLException{
        int workoutID =  rs.getInt("ID");
        String workoutName = rs.getString("TITLE");

        return new Workout(new WorkoutHeader(workoutName,workoutID));
    }

    private void loadWorkouts() {
        try (Connection connection = connect()) {
            workouts = new ArrayList<>();
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM WORKOUTS");

            while (resultSet.next()) {
                final Workout oneWorkout = fromResultSet(resultSet);
                this.workouts.add(oneWorkout);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public List<WorkoutHeader> getAllWorkoutHeaders() {
        List<WorkoutHeader> allHeaders = new ArrayList<>();
        loadWorkouts();
        for (Workout workout : workouts)
            allHeaders.add(workout.getHeader());
        return allHeaders;
    }

    @Override
    public Workout getWorkoutByID(int workoutID) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM WORKOUTS WHERE WORKOUTS.ID = ?");
            statement.setString(1, Integer.toString(workoutID));
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return fromResultSet(resultSet);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return null;
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




