package funkyflamingos.bisonfit.persistence.hsqldb;

import static funkyflamingos.bisonfit.application.Constants.RECOMMENDED_CUPS_OF_WATER_PER_DAY;

import android.util.Log;

import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

public class WaterTrackerPersistenceHSQLDB implements IWaterTrackerPersistence {
    private final String dbPath;

    public WaterTrackerPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
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
        return RECOMMENDED_CUPS_OF_WATER_PER_DAY;
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

    @Override
    public void clear(LocalDate date) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM WATERTRACKING WHERE dateProgress = ?");
            statement.setString(1, Long.toString(date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()));

            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }
}
