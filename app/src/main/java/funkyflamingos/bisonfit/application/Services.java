package funkyflamingos.bisonfit.application;

// the interfaces
import funkyflamingos.bisonfit.persistence.ISavedRoutineExercises;
import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;
import funkyflamingos.bisonfit.persistence.IExerciseLookupPersistence;
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;
import funkyflamingos.bisonfit.persistence.IUserRegistrationPersistence;

//the DB
import funkyflamingos.bisonfit.persistence.hsqldb.RoutinesPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.SavedRoutineExercisesPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.WaterTrackPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.ExerciseLookupPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.GymHoursPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.UserRegistrationPersistenceHSQLDB;

public class Services {
    private static IRoutinesPersistence routinesPersistence = null;
    private static IWaterTrackerPersistence waterTrackerPersistence = null;
    private static IExerciseLookupPersistence exerciseLookupPersistence = null;
    private static IGymHoursPersistence gymHoursPersistence = null;
    private static IUserRegistrationPersistence userRegistrationPersistence = null;
    private static ISavedRoutineExercises savedRoutineExercises = null;


    public static synchronized IRoutinesPersistence getRoutinesPersistence() {
        if(routinesPersistence == null) {
            routinesPersistence = new RoutinesPersistenceHSQLDB(Main.getDBPathName());
        }
        return routinesPersistence;
    }

    public static synchronized IWaterTrackerPersistence getWaterTrackPersistence() {
        if(waterTrackerPersistence == null) {
            waterTrackerPersistence = new WaterTrackPersistenceHSQLDB(Main.getDBPathName());
        }
        return waterTrackerPersistence;
    }

    public static synchronized IUserRegistrationPersistence getUserRegistrationPersistence() {
        if(userRegistrationPersistence == null) {
            userRegistrationPersistence = new UserRegistrationPersistenceHSQLDB(Main.getDBPathName());
        }
        return userRegistrationPersistence;
    }

    public static synchronized IGymHoursPersistence getGymHoursPersistence() {
        if(gymHoursPersistence == null) {
            gymHoursPersistence = new GymHoursPersistenceHSQLDB(Main.getDBPathName());
        }
        return gymHoursPersistence;
    }

    public static synchronized IExerciseLookupPersistence getExerciseLookupPersistence() {
        if(exerciseLookupPersistence == null) {
            exerciseLookupPersistence = new ExerciseLookupPersistenceHSQLDB(Main.getDBPathName());
        }
        return exerciseLookupPersistence;
    }

    public static synchronized ISavedRoutineExercises getSavedRoutineExercises() {
        if(savedRoutineExercises == null) {
            savedRoutineExercises = new SavedRoutineExercisesPersistenceHSQLDB(Main.getDBPathName());
        }
        return savedRoutineExercises;
    }


    /**
     * clean
     *
     * Reset all services so to be reloaded from scratch next time they are referenced
     */
    public static synchronized void clean() {
        routinesPersistence = null;
    }


}


