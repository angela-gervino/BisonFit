package funkyflamingos.bisonfit.application;

// the interfaces
import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;
import funkyflamingos.bisonfit.persistence.IExerciseLookupPersistence;
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;
import funkyflamingos.bisonfit.persistence.IUserRegistrationPersistence;

//the DB
import funkyflamingos.bisonfit.persistence.hsqldb.RoutinesPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.WaterTrackPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.ExerciseLookupPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.GymHoursPersistenceHSQLDB;
import funkyflamingos.bisonfit.persistence.hsqldb.UserRegistrationPersistenceHSQLDB;

// the stubs
import funkyflamingos.bisonfit.persistence.stubs.WaterTrackerPersistenceStub;
import funkyflamingos.bisonfit.persistence.stubs.RoutinesPersistenceStub;
import funkyflamingos.bisonfit.persistence.stubs.GymHoursPersistenceStub;
import funkyflamingos.bisonfit.persistence.stubs.ExerciseLookupPersistenceStub;


public class Services {
    private static IRoutinesPersistence routinesPersistence = null;
    private static IWaterTrackerPersistence waterTrackerPersistence = null;
    private static IExerciseLookupPersistence exerciseLookupPersistence = null;
    private static IGymHoursPersistence gymHoursPersistence = null;
    private static IUserRegistrationPersistence userRegistrationPersistence = null;


    public static synchronized IRoutinesPersistence getRoutinesPersistence(boolean forProduction) {
        if(routinesPersistence == null) {
            if (forProduction) {
                routinesPersistence = new RoutinesPersistenceHSQLDB(Main.getDBPathName());
            } else {
                routinesPersistence = new RoutinesPersistenceStub();
            }
        }
        return routinesPersistence;
    }

    public static synchronized IWaterTrackerPersistence getWaterTrackPersistence(boolean forProduction) {
        if(waterTrackerPersistence == null) {
              if (forProduction) {
            waterTrackerPersistence = new WaterTrackPersistenceHSQLDB(Main.getDBPathName());
              } else {
                  waterTrackerPersistence = new WaterTrackerPersistenceStub();
             }
        }
        return waterTrackerPersistence;
    }

    public static synchronized IUserRegistrationPersistence getUserRegistrationPersistence(boolean forProduction) {
        if(userRegistrationPersistence == null) {
              if (forProduction) {
            userRegistrationPersistence = new UserRegistrationPersistenceHSQLDB(Main.getDBPathName());
              } else {
                //  userRegistrationPersistence = ;
             }
        }
        return userRegistrationPersistence;
    }

    public static synchronized IGymHoursPersistence getGymHoursPersistence(boolean forProduction) {
        if(gymHoursPersistence == null) {
              if (forProduction) {
            gymHoursPersistence = new GymHoursPersistenceHSQLDB(Main.getDBPathName());
              } else {
                  gymHoursPersistence = new GymHoursPersistenceStub();
             }
        }
        return gymHoursPersistence;
    }

    public static synchronized IExerciseLookupPersistence getExerciseLookupPersistence(boolean forProduction) {
        if(exerciseLookupPersistence == null) {
              if (forProduction) {
            exerciseLookupPersistence = new ExerciseLookupPersistenceHSQLDB(Main.getDBPathName());
              } else {
                  exerciseLookupPersistence = new ExerciseLookupPersistenceStub();
             }
        }
        return exerciseLookupPersistence;
    }


    /**
     * clean
     *
     * Reset all services so to be reloaded from scratch next time they are referenced
     */
    public static synchronized void clean() {
        routinesPersistence = null;
        waterTrackerPersistence = null;
        exerciseLookupPersistence=null;
        gymHoursPersistence=null;
        userRegistrationPersistence=null;
    }


}


