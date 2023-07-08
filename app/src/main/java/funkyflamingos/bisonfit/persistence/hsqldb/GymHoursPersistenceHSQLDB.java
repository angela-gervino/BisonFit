package funkyflamingos.bisonfit.persistence.hsqldb;


import static funkyflamingos.bisonfit.dso.GymHours.DAYS_PER_WEEK;
import static funkyflamingos.bisonfit.dso.GymHours.getDayOfWeek;
import static funkyflamingos.bisonfit.dso.GymHours.getNextDayOfWeek;

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
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;

public class GymHoursPersistenceHSQLDB implements IGymHoursPersistence {

    private final String dbPath;
    private List<GymHours> gymHoursList;

    public GymHoursPersistenceHSQLDB(String dbPathName) {
        this.dbPath = dbPathName;
        this.gymHoursList = new ArrayList<>();
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
                this.gymHoursList.add(hourOneDay);
            }
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

    @Override
    public List<GymHours> getNextWeekHours(LocalDate today) {
        List<GymHours> nextWeekHours = new ArrayList<GymHours>();
        int dayOfWeek = getDayOfWeek(today);

        for (int i = 0; i < DAYS_PER_WEEK; i++) {
            nextWeekHours.add(getHoursByID(dayOfWeek));
            dayOfWeek = getNextDayOfWeek(dayOfWeek);
        }

        return nextWeekHours;
    }

    private GymHours getHoursByID(int dayID) {
        for (GymHours weekday : gymHoursList)
            if (weekday.getDayID() == dayID)
                return weekday;
        return null;
    }

    /*
    @Override
    public List<GymHours> getNextWeekHours(LocalDate today) {
        List<GymHours> nextWeekHours = new ArrayList<GymHours>();
        int dayOfWeek = getDayOfWeek(today);
        for (int i = 0; i < DAYS_PER_WEEK; i++) {

            nextWeekHours.add(getHoursByID(dayOfWeek));
            dayOfWeek = getNextDayOfWeek(dayOfWeek);
        }
        return nextWeekHours;
    }

    private GymHours getHoursByID(int dayID) {
        GymHours gymHoursDay = null;
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM GYMHOURS WHERE dayWeek = ?");
            statement.setInt(1, dayID);
            final ResultSet resultSet = statement.executeQuery();
           System.out.println("The result is " + resultSet);
            System.out.println("Day id is " + dayID );
            if (resultSet.next()) {
                gymHoursDay = fromResultSet(resultSet);
                System.out.println("We go into the resultSet. " + gymHoursDay );
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }

        return gymHoursDay;
    }


     */
}
