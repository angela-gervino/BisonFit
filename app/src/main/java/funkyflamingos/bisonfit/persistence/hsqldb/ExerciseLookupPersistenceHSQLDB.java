package funkyflamingos.bisonfit.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.persistence.IExerciseLookupPersistence;


public class ExerciseLookupPersistenceHSQLDB implements IExerciseLookupPersistence {

    private final String dbPath;
    private ArrayList<ExerciseHeader> exerciseHeaders;


    public ExerciseLookupPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
        this.exerciseHeaders = new ArrayList<>();
        loadExercises();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private ExerciseHeader fromResultSet(final ResultSet rs) throws SQLException{
        int exerciseID =  rs.getInt("ID");
        String exerciseName = rs.getString("NAME");

        return new ExerciseHeader(exerciseName, exerciseID);
    }

    private void loadExercises() {
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM EXERCISELOOKUP");

            while (resultSet.next()) {
                final ExerciseHeader header = fromResultSet(resultSet);
                this.exerciseHeaders.add(header);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<ExerciseHeader> getAllExerciseHeaders() {
        return exerciseHeaders;
    }
}

