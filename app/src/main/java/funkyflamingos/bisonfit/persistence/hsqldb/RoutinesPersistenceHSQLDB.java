package funkyflamingos.bisonfit.persistence.hsqldb;

import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RoutinesPersistenceHSQLDB implements IRoutinesPersistence {

    private final String dbPath;
    private  List<Routine> routines;

    public RoutinesPersistenceHSQLDB (String dbPath ){
        this.dbPath = dbPath;
        this.routines = new ArrayList<>();
        loadRoutines();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Routine fromResultSet(final ResultSet rs) throws SQLException{
        String routineName = rs.getString("name");
        int routineID =  rs.getInt("id");

        return new Routine(new RoutineHeader(routineName,routineID));
    }

    private void loadRoutines() {
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM ROUTINES");

            while (resultSet.next()) {
                final Routine oneRoutine = fromResultSet(resultSet);
                //connectRecipeWithTags(recipe);
                this.routines.add(oneRoutine);
            }
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }

    }

    @Override
    public List<RoutineHeader> getAllRoutineHeaders() {
        List<RoutineHeader> allHeaders = new ArrayList<>();
        for (Routine routine : routines)
            allHeaders.add(routine.getHeader());
        return allHeaders;
    }

    @Override
    public Routine getRoutineByID(int routineID) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM ROUTINES WHERE ROUTINES.id = ?");
            statement.setString(1, Integer.toString(routineID));
            final ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return fromResultSet(resultSet);
            }

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return null;

    }
}




