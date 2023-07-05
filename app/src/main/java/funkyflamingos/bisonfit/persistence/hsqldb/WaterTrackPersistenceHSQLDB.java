package funkyflamingos.bisonfit.persistence.hsqldb;

import android.util.Log;

import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
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
        insertWater(LocalDate.now());
        loadWaterTrack();


    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }


    private void loadWaterTrack() {
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM WATERTRACKING");
            while (resultSet.next()) {
                Timestamp dateAsTimestamp = resultSet.getTimestamp("dateProgress");
                LocalDate date = dateAsTimestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int cupsDrank = resultSet.getInt("cupsDrank");
                this.progress.put(date, cupsDrank);
            }
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }


    }

    public void insertWater(LocalDate date) {
        System.out.println("hello");
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO WATERTRACKING VALUES(?, ?");
            statement.setTimestamp(1, Timestamp.valueOf(String.valueOf(date.atTime(LocalTime.MIDNIGHT))));
            statement.setInt(2, 0);
            statement.executeUpdate();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();

        }
    }




    @Override
    public void increment(LocalDate date) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("UPDATE WATERTRACKING SET cupsDrank=cupsDrank+1 WHERE dateProgress = ?");
            statement.setTimestamp(1, Timestamp.valueOf(String.valueOf(date.atTime(LocalTime.MIDNIGHT))));
            statement.executeQuery();
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
            statement.setTimestamp(1, Timestamp.valueOf(String.valueOf(date.atTime(LocalTime.MIDNIGHT))));
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cupsDrank = resultSet.getInt("cupsDrank");
            }
        } catch (SQLException s) {

        }
         return cupsDrank;
    }


}
