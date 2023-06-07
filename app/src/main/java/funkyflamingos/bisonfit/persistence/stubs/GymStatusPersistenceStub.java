package funkyflamingos.bisonfit.persistence.stubs;

import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.persistence.IGymStatusPersistence;

import java.time.*;
import java.util.*;
import java.util.ArrayList;
public class GymStatusPersistenceStub implements IGymStatusPersistence {
    private List<GymHours>  gymHoursList;
    public GymStatusPersistenceStub(){
        this.gymHoursList = new ArrayList<>();

        // Add hours for the work-days
        for (int i = 1; i <= 5; i++)
            gymHoursList.add( new GymHours(
                    i,
                    LocalTime.of(6, 0, 0),
                    LocalTime.of(22,0,0)
            ));

        // Add hours for the week-end
        for (int i = 6; i <= 7; i++)
            gymHoursList.add( new GymHours(
                    i,
                    LocalTime.of(8, 0, 0),
                    LocalTime.of(20,0,0)
            ));
    }

    @Override
    public GymHours getHoursByID(int dayID) {
        for(GymHours weekday : gymHoursList)
            if(weekday.getDayID() == dayID)
                return weekday;
        return null;
    }
}
