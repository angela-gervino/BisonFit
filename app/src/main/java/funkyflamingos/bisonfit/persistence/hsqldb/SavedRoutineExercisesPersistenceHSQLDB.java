package funkyflamingos.bisonfit.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import funkyflamingos.bisonfit.dso.ExerciseHeader;
import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.persistence.ISavedRoutineExercises;

public class SavedRoutineExercisesPersistenceHSQLDB implements ISavedRoutineExercises {
    private final String dbPath;
    private int nextIndex;

    public SavedRoutineExercisesPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    public ArrayList<ExerciseHeader> getExercisesByRoutine(RoutineHeader routineHeader) {
        ArrayList<ExerciseHeader> exercisesInOrder = new ArrayList<>();
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM SAVEDROUTINEEXERCISES JOIN EXERCISELOOKUP ON SAVEDROUTINEEXERCISES.EXERCISEID = EXERCISELOOKUP.ID WHERE ROUTINEID = ? ORDER BY INDEX ASC");
            statement.setInt(1, routineHeader.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                int id = resultSet.getInt("ID");
                int setCount = resultSet.getInt("NUMSETS");
                int index = resultSet.getInt("INDEX");
                exercisesInOrder.add(new ExerciseHeader(name, id, setCount, index));
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return exercisesInOrder;
    }

    public void addExercises(ArrayList<ExerciseHeader> exerciseHeaders, RoutineHeader routineHeader) {
        try (Connection connection = connect()) {

            for (int i = 0; i < exerciseHeaders.size(); i++){
                final PreparedStatement statement = connection.prepareStatement("INSERT INTO SAVEDROUTINEEXERCISES VALUES(?, ?, DEFAULT, ?)");
                statement.setInt(1, routineHeader.getId());
                statement.setInt(2, exerciseHeaders.get(i).getId());
                statement.setInt(3, Integer.parseInt(exerciseHeaders.get(i).getSetCount()));
                statement.executeUpdate();
            }

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    public void deleteExercise(ExerciseHeader exerciseHeader, RoutineHeader routineHeader) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM SAVEDROUTINEEXERCISES WHERE ROUTINEID = ? AND EXERCISEID = ? AND INDEX = ?");
            statement.setInt(1, routineHeader.getId());
            statement.setInt(2, exerciseHeader.getId());
            statement.setInt(3, exerciseHeader.getIndex());
            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    public void deleteRoutine(RoutineHeader routineHeader) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM SAVEDROUTINEEXERCISES WHERE ROUTINEID = ?");
            statement.setInt(1, routineHeader.getId());
            statement.executeUpdate();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    public int getNumSets(RoutineHeader routineHeader) {
        int numSets = 0;
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT SUM(NUMSETS) AS SETCOUNT FROM SAVEDROUTINEEXERCISES WHERE ROUTINEID = ?");
            statement.setInt(1, routineHeader.getId());
            ResultSet resultSet = statement.executeQuery();
            numSets = resultSet.getInt("SETCOUNT");
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return numSets;
    }
}
