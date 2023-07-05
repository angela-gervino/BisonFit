package funkyflamingos.bisonfit.logic;

import java.util.List;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.dso.Routine;
import funkyflamingos.bisonfit.dso.RoutineHeader;
import funkyflamingos.bisonfit.persistence.IRoutinesPersistence;
import funkyflamingos.bisonfit.persistence.stubs.RoutinesPersistenceStub;

public class RoutineHandler {


    private IRoutinesPersistence persistence;

    // Constructor for the stub

  //  public RoutineHandler() {
  //      persistence = new RoutinesPersistenceStub();
  //  }


    // Constructor for the databse
    public RoutineHandler (){
        persistence = Services.getRoutinesPersistence();
    }

    public RoutineHandler(IRoutinesPersistence p) {
        persistence = p;
    }

    public List<RoutineHeader> getAllRoutineHeaders() {
        return persistence.getAllRoutineHeaders();
    }

    public Routine getRoutineByID(int routineID) {
        return persistence.getRoutineByID(routineID);
    }
}
