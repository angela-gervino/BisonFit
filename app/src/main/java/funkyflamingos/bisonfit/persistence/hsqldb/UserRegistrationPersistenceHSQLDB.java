package funkyflamingos.bisonfit.persistence.hsqldb;


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import funkyflamingos.bisonfit.persistence.IUserRegistrationPersistence;

public class UserRegistrationPersistenceHSQLDB implements IUserRegistrationPersistence {
    private final String dbPath;

    public UserRegistrationPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public void setUserName(String userName) {
        try (Connection connection = connect()) {
            deleteEntries(connection);
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO USERREGISTRATION VALUES(?)");
            statement.setString(1, userName);
            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    private void deleteEntries(Connection connection) {
        try {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM USERREGISTRATION");
            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public void clearTable() {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM USERREGISTRATION");
            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public String getUserName() {
        String userName = null;
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM USERREGISTRATION");
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userName = resultSet.getString("userName");
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return userName;
    }
}
