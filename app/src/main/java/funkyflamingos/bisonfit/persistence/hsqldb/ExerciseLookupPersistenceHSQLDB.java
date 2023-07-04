package funkyflamingos.bisonfit.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.Exercise;
import funkyflamingos.bisonfit.persistence.IExerciseLookupPersistence;


public class ExerciseLookupPersistenceHSQLDB implements IExerciseLookupPersistence {

    private final String dbPath;
    private  List<Exercise> exercisesList;

    public ExerciseLookupPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
        this.exercisesList = new ArrayList<>();
        loadExercises();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Exercise fromResultSet(final ResultSet rs) throws SQLException{
        int exerciseID =  rs.getInt("EXERCISEID");
        String exercise_name = rs.getString("EXERCISENAME");
        int exercise_type = rs.getInt("EXERCISETYPE");

        return new Exercise(exerciseID,exercise_name,exercise_type);
    }

    private void loadExercises() {
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM EXERCISES");

            while (resultSet.next()) {
                final Exercise oneExercise = fromResultSet(resultSet);
                this.exercisesList.add(oneExercise);
            }
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }



    @Override
    public List<Exercise> getExerciseList() {
        return exercisesList;
    }
}
