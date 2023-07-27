package funkyflamingos.bisonfit.application;

import funkyflamingos.bisonfit.persistence.IPerformedWorkoutRecordPersistence;
import funkyflamingos.bisonfit.persistence.ISavedWorkoutExercises;
import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;
import funkyflamingos.bisonfit.persistence.IWorkoutPersistence;
import funkyflamingos.bisonfit.persistence.IExerciseLookupPersistence;
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;
import funkyflamingos.bisonfit.persistence.IUserRegistrationPersistence;

import funkyflamingos.bisonfit.persistence.hsqldb.PerformedWorkoutRecordPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.WorkoutPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.SavedWorkoutExercisesPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.WaterTrackerPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.ExerciseLookupPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.GymHoursPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.UserRegistrationPersistenceHSQLDB;

public class Services {
    private static IWorkoutPersistence workoutsPersistence = null;
    private static IWaterTrackerPersistence waterTrackerPersistence = null;
    private static IExerciseLookupPersistence exerciseLookupPersistence = null;
    private static IGymHoursPersistence gymHoursPersistence = null;
    private static IUserRegistrationPersistence userRegistrationPersistence = null;
    private static ISavedWorkoutExercises savedWorkoutExercises = null;
    private static IPerformedWorkoutRecordPersistence performedWorkoutRecordPersistence = null;

    public static synchronized IWorkoutPersistence getWorkoutsPersistence() {
        if (workoutsPersistence == null) {
            workoutsPersistence = new WorkoutPersistenceHSQLDB(Main.getDBPathName());
        }
        return workoutsPersistence;
    }

    public static synchronized IWaterTrackerPersistence getWaterTrackPersistence() {
        if (waterTrackerPersistence == null) {
            waterTrackerPersistence = new WaterTrackerPersistenceHSQLDB(Main.getDBPathName());
        }
        return waterTrackerPersistence;
    }

    public static synchronized IUserRegistrationPersistence getUserRegistrationPersistence() {
        if (userRegistrationPersistence == null) {
            userRegistrationPersistence = new UserRegistrationPersistenceHSQLDB(Main.getDBPathName());
        }
        return userRegistrationPersistence;
    }

    public static synchronized IGymHoursPersistence getGymHoursPersistence() {
        if (gymHoursPersistence == null) {
            gymHoursPersistence = new GymHoursPersistenceHSQLDB(Main.getDBPathName());
        }
        return gymHoursPersistence;
    }

    public static synchronized IExerciseLookupPersistence getExerciseLookupPersistence() {
        if (exerciseLookupPersistence == null) {
            exerciseLookupPersistence = new ExerciseLookupPersistenceHSQLDB(Main.getDBPathName());
        }
        return exerciseLookupPersistence;
    }

    public static synchronized ISavedWorkoutExercises getSavedWorkoutExercises() {
        if (savedWorkoutExercises == null) {
            savedWorkoutExercises = new SavedWorkoutExercisesPersistenceHSQLDB(Main.getDBPathName());
        }
        return savedWorkoutExercises;
    }

    public static synchronized IPerformedWorkoutRecordPersistence getPerformedWorkoutRecordPersistence() {
        if (performedWorkoutRecordPersistence == null) {
            performedWorkoutRecordPersistence = new PerformedWorkoutRecordPersistenceHSQLDB(Main.getDBPathName());
        }
        return performedWorkoutRecordPersistence;
    }


    /**
     * Reset all services so to be reloaded from scratch next time they are referenced
     */
    public static synchronized void clean() {
        workoutsPersistence = null;
    }


}


