package funkyflamingos.bisonfit.persistence.stubs;

import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.persistence.GymStatusPersistence;

import java.time.*;
import java.util.*;
import java.util.ArrayList;
public class GymStatusPersistenceStub implements GymStatusPersistence {
    private List<GymHours>  gymHoursList;
    public GymStatusPersistenceStub(){
        this.gymHoursList = new ArrayList<>();
        gymHoursList.add( new GymHours(
                1,
                LocalTime.of(6, 0, 0),
                LocalTime.of(22,0,0)
        ));
        gymHoursList.add( new GymHours(
                2,
                LocalTime.of(6, 0, 0),
                LocalTime.of(22,0,0)
        ));
        gymHoursList.add( new GymHours(
                3,
                LocalTime.of(6, 0, 0),
                LocalTime.of(22,0,0)
        ));
        gymHoursList.add( new GymHours(
                4,
                LocalTime.of(6, 0, 0),
                LocalTime.of(22,0,0)
        ));
        gymHoursList.add( new GymHours(
                5,
                LocalTime.of(6, 0, 0),
                LocalTime.of(22,0,0)
        ));
        gymHoursList.add( new GymHours(
                6,
                LocalTime.of(8, 0, 0),
                LocalTime.of(20,0,0)
        ));
        gymHoursList.add( new GymHours(
                7,
                LocalTime.of(8, 0, 0),
                LocalTime.of(20,0,0)
        ));
    }

    @Override
    public GymHours getHoursByID(int dayID) {
        for(int i = 0; i < gymHoursList.size(); i++)
            if(gymHoursList.get(i).getDayID() == dayID)
                return gymHoursList.get(i);
        return null;
    }
}
