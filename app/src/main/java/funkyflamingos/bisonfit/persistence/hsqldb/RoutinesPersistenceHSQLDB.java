package funkyflamingos.bisonfit.persistence.hsqldb;

import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;
import funkyflamingos.bisonfit.persistence.utils.DBHelper;

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
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Routine fromResultSet(final ResultSet rs) throws SQLException{
        int routineID =  rs.getInt("ID");
        String routineName = rs.getString("TITLE");

        return new Routine(new RoutineHeader(routineName,routineID));
    }

    private void loadRoutines() {
        try (Connection connection = connect()) {
            routines = new ArrayList<>();
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM ROUTINES");

            while (resultSet.next()) {
                final Routine oneRoutine = fromResultSet(resultSet);
                this.routines.add(oneRoutine);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public List<RoutineHeader> getAllRoutineHeaders() {
        List<RoutineHeader> allHeaders = new ArrayList<>();
        loadRoutines();
        for (Routine routine : routines)
            allHeaders.add(routine.getHeader());
        return allHeaders;
    }

    @Override
    public Routine getRoutineByID(int routineID) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM ROUTINES WHERE ROUTINES.ID = ?");
            statement.setString(1, Integer.toString(routineID));
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
    public void addRoutine(String name) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO ROUTINES VALUES (DEFAULT, ?)");
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRoutineById(int routineId) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM ROUTINES WHERE ROUTINES.ID = ?");
            statement.setInt(1, routineId);
            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }
}




