package funkyflamingos.bisonfit.persistence.hsqldb;


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;

public class GymHoursPersistenceHSQLDB implements IGymHoursPersistence {

    private final String dbPath;

    public GymHoursPersistenceHSQLDB(String dbPathName) {
        this.dbPath = dbPathName;
    }

    @Override
    public GymHours getHoursByID(int dayID) {
        return null;
    }
}
