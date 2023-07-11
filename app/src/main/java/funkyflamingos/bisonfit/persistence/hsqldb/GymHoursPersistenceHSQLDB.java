package funkyflamingos.bisonfit.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.Hours;
import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.persistence.AbstractGymHoursPersistence;
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;

public class GymHoursPersistenceHSQLDB extends AbstractGymHoursPersistence implements IGymHoursPersistence {

    private final String dbPath;

    public GymHoursPersistenceHSQLDB(String dbPathName) {
        super();
        this.dbPath = dbPathName;
        loadGymHours();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private void loadGymHours() {
        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM GYMHOURS");
            while (resultSet.next()) {
                final GymHours hourOneDay = fromResultSet(resultSet);
                super.getGymHoursList().add(hourOneDay);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    private GymHours fromResultSet(final ResultSet rs) throws SQLException{
        int dayWeek =  rs.getInt("dayWeek");
        int openingTimeHour = rs.getInt("openingTimeHour");
        int openingTimeMin = rs.getInt("openingTimeMin");
        int openingTimeSec = rs.getInt("openingTimeSec");
        int closingTimeHour = rs.getInt("closingTimeHour");
        int closingTimeMin = rs.getInt("closingTimeMin");
        int closingTimeSec = rs.getInt("closingTimeSec");
        Hours hours = new Hours(LocalTime.of(openingTimeHour, openingTimeMin, openingTimeSec),
                LocalTime.of(closingTimeHour, closingTimeMin, closingTimeSec));
        List<Hours> hoursList = new ArrayList<>();
        hoursList.add(hours);
        GymHours gymHours = new GymHours(dayWeek,hoursList);
        return gymHours;
    }
}
