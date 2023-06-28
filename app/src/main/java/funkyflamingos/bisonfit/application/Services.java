package funkyflamingos.bisonfit.application;

// the interfaces
import funkyflamingos.bisonfit.persistence.IWaterTrackerPersistence;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;

//the DB
import funkyflamingos.bisonfit.persistence.hsqldb.RoutinesPersistenceHSQLDB;

// the stubs
import funkyflamingos.bisonfit.persistence.stubs.WaterTrackerPersistenceStub;
import funkyflamingos.bisonfit.persistence.stubs.RoutinesPersistenceStub;
import funkyflamingos.bisonfit.persistence.stubs.GymHoursPersistenceStub;


public class Services {
    private static IRoutinesPersistence routinesPersistence = null;


    public static synchronized IRoutinesPersistence getRoutinesPersistence() {
        if(routinesPersistence == null) {
          //  if (forProduction) {
                routinesPersistence = new RoutinesPersistenceHSQLDB(Main.getDBPathName());
          //  } else {
          //      routinesPersistence = new RoutinesPersistenceStub();
           // }
        }
        return routinesPersistence;
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


