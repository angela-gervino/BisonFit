package funkyflamingos.bisonfit.persistence.hsqldb;

import static java.lang.Long.parseLong;

import android.util.Log;

import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class WaterTrackPersistenceHSQLDB implements IWaterTrackerPersistence {

    Map<LocalDate, Integer> progress;
    private final String dbPath;
    int goal;

    public WaterTrackPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
        progress = new HashMap<>();
        goal = 8;
        loadWaterTrack();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }


    // if we are just using this for ite 3, we should save and delete!!!
    private void loadWaterTrack() {
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM WATERTRACKING");
            while (resultSet.next()) {
                String dateAsString = resultSet.getString("dateProgress");
                LocalDate date = Instant.ofEpochSecond(parseLong(dateAsString)).atZone(ZoneId.systemDefault()).toLocalDate();
                int cupsDrank = resultSet.getInt("cupsDrank");
                this.progress.put(date, cupsDrank);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    private void insertWater(LocalDate date) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO WATERTRACKING VALUES(?, ?)");
            statement.setString(1, Long.toString(date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()));
            statement.setInt(2, 1);
            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public void increment(LocalDate date) {
        try (Connection connection = connect()) {
            final PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM WATERTRACKING WHERE dateProgress = ?");
            selectStatement.setString(1, Long.toString(date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()));
            final ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                final PreparedStatement statement = connection.prepareStatement("UPDATE WATERTRACKING SET cupsDrank=cupsDrank+1 WHERE dateProgress = ?");
                statement.setString(1, Long.toString(date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()));
                statement.executeUpdate();
                loadWaterTrack();
            } else {
                insertWater(date);
            }
            resultSet.close();
            selectStatement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public int getGoal() {
        return goal;
    }

    @Override
    public int getProgress(LocalDate date) {
        int cupsDrank = 0;
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT cupsDrank FROM WATERTRACKING WHERE dateProgress = ?");
            statement.setString(1, Long.toString(date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()));
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cupsDrank = resultSet.getInt("cupsDrank");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException s) {

        }
        return cupsDrank;
    }
}
