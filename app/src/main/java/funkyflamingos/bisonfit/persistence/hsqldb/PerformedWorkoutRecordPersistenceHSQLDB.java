package funkyflamingos.bisonfit.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import funkyflamingos.bisonfit.dso.Exercise;
import funkyflamingos.bisonfit.dso.ExerciseSet;
import funkyflamingos.bisonfit.dso.PerformedWorkoutHeader;
import funkyflamingos.bisonfit.dso.Workout;
import funkyflamingos.bisonfit.dso.WorkoutHeader;
import funkyflamingos.bisonfit.persistence.IPerformedWorkoutRecordPersistence;

public class PerformedWorkoutRecordPersistenceHSQLDB implements IPerformedWorkoutRecordPersistence {
    private String dbPath;

    public PerformedWorkoutRecordPersistenceHSQLDB(String dbPath){
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public void addPerformedWorkout(Workout workout) {
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO PERFORMEDWORKOUTRECORD VALUES (DEFAULT, ?, ?, ?)");
            PerformedWorkoutHeader header = (PerformedWorkoutHeader) workout.getHeader();
            statement.setString(1, header.getName());
            statement.setString(2, LocalDateTimeToString(header.getDateStarted()));
            statement.setString(3, LocalDateTimeToString(header.getDateEnded()));
            statement.executeUpdate();
            addPerformedExercises(workout, connection);
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<PerformedWorkoutHeader> getPerformedWorkoutHeaders() {
        ArrayList<PerformedWorkoutHeader> headers = new ArrayList<>();

        try (Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM PERFORMEDWORKOUTRECORD ORDER BY WORKOUTRECORDID DESC");
            while (resultSet.next()) {
                String workoutName = resultSet.getString("TITLE");
                int workoutRecordId = resultSet.getInt("WORKOUTRECORDID");
                LocalDateTime start = StringToLocalDateTime(resultSet.getString("START"));
                LocalDateTime end = StringToLocalDateTime(resultSet.getString("END"));
                PerformedWorkoutHeader header = new PerformedWorkoutHeader(workoutName, workoutRecordId);
                header.setDateStarted(start);
                header.setDateEnded(end);
                headers.add(header);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }

        return headers;
    }

    @Override
    public Workout getPerformedWorkoutById(int id) {
        Workout workout = null;
        PerformedWorkoutHeader header = getPerformedWorkoutHeaderById(id);
        if (header != null) {
            workout = new Workout(header);

            try (Connection connection = connect()) {
                final PreparedStatement statement = connection.prepareStatement("SELECT * FROM EXERCISELOOKUP JOIN PERFORMEDEXERCISERECORD ON EXERCISELOOKUP.ID = PERFORMEDEXERCISERECORD.EXERCISEID WHERE WORKOUTRECORDID = ? ORDER BY INDEX ASC");
                statement.setInt(1, id);
                final ResultSet resultSet = statement.executeQuery();

                int previousIdentifier = -1;
                Exercise currentExercise = null;
                while (resultSet.next()) {
                    double weight = resultSet.getDouble("WEIGHT");
                    int reps = resultSet.getInt("REPS");
                    int currIdentifier = resultSet.getInt("SETIDENTIFIER");
                    if (previousIdentifier != currIdentifier) {
                        int exerciseId = resultSet.getInt("EXERCISEID");
                        String exerciseName = resultSet.getString("NAME");

                        if (previousIdentifier != -1)
                            workout.addExercise(currentExercise);

                        currentExercise = new Exercise(exerciseName, exerciseId);
                        previousIdentifier = currIdentifier;
                    }
                    currentExercise.addSet(new ExerciseSet(weight, reps));
                }
                workout.addExercise(currentExercise);

                resultSet.close();
                statement.close();
            } catch (final SQLException e) {
                Log.e("Connect SQL", e.getMessage() + e.getSQLState());
                e.printStackTrace();
            }
        }
        return workout;
    }

    private void addPerformedExercises(Workout workout, Connection connection) throws SQLException {
        int exerciseSetIdentifier = 1;
        for (Exercise exercise : workout.getAllExercises()) {
            for (ExerciseSet exerciseSet: exercise.getAllSets()) {
                final PreparedStatement statement = connection.prepareStatement("INSERT INTO PERFORMEDEXERCISERECORD VALUES (?, DEFAULT, ?, ?, ?, ?)");
                int workoutRecordId = getPerformedWorkoutRecordId(((PerformedWorkoutHeader)workout.getHeader()).getDateStarted());
                statement.setInt(1, workoutRecordId);
                statement.setInt(2, exercise.getID());
                statement.setDouble(3, exerciseSet.getWeight());
                statement.setInt(4, exerciseSet.getReps());
                statement.setInt(5, exerciseSetIdentifier);
                statement.executeUpdate();
            }
            exerciseSetIdentifier++;
        }
    }

    private PerformedWorkoutHeader getPerformedWorkoutHeaderById(int id) {
        PerformedWorkoutHeader header = null;
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM PERFORMEDWORKOUTRECORD WHERE WORKOUTRECORDID = ?");
            statement.setInt(1, id);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                LocalDateTime start = StringToLocalDateTime(resultSet.getString("START"));
                LocalDateTime end = StringToLocalDateTime(resultSet.getString("END"));
                String workoutName = resultSet.getString("TITLE");
                header = new PerformedWorkoutHeader(workoutName, id);
                header.setDateStarted(start);
                header.setDateEnded(end);
            }
            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return header;
    }

    private int getPerformedWorkoutRecordId(LocalDateTime start) {
        int id = -1;
        try (Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT WORKOUTRECORDID FROM PERFORMEDWORKOUTRECORD WHERE START = ?");
            statement.setString(1, LocalDateTimeToString(start));
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                id = resultSet.getInt("WORKOUTRECORDID");

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return id;
    }

    private String LocalDateTimeToString(LocalDateTime date) {
        String dateAsString = null;
        if (date != null) {
            ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
            dateAsString = Long.toString(zdt.toInstant().toEpochMilli());
        }
        return dateAsString;
    }

    private LocalDateTime StringToLocalDateTime(String date) {
        LocalDateTime dateTime = null;
        if (date != null)
            dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(date)), ZoneId.systemDefault());
        return dateTime;
    }
    
}
