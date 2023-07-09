package funkyflamingos.bisonfit.persistence.stubs;

import static funkyflamingos.bisonfit.dso.GymHours.DAYS_PER_WEEK;
import static funkyflamingos.bisonfit.dso.GymHours.getDayOfWeek;
import static funkyflamingos.bisonfit.dso.GymHours.getNextDayOfWeek;

import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.dso.Hours;
import funkyflamingos.bisonfit.persistence.AbstractGymHoursPersistence;
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;

import java.time.*;
import java.util.*;
import java.util.ArrayList;

public class GymHoursPersistenceStub extends AbstractGymHoursPersistence implements IGymHoursPersistence {
    public GymHoursPersistenceStub() {
        super();

        // Monday
        List mondayHours = new ArrayList<Hours>();
        mondayHours.add(new Hours(
                LocalTime.of(6, 0, 0),
                LocalTime.of(12, 0, 0)
        ));
        mondayHours.add(new Hours(
                LocalTime.of(13, 0, 0),
                LocalTime.of(14, 0, 0)
        ));
        mondayHours.add(new Hours(
                LocalTime.of(15, 0, 0),
                LocalTime.of(20, 0, 0)
        ));
        super.getGymHoursList().add(new GymHours(1, mondayHours));

        // Tuesday
        List tuesdayHours = null;
        super.getGymHoursList().add(new GymHours(2, tuesdayHours));

        // Wednesday
        List wednesdayHours = new ArrayList<Hours>();
        wednesdayHours.add(new Hours(
                LocalTime.of(6, 0, 0),
                LocalTime.of(0, 0, 0)
        ));
        super.getGymHoursList().add(new GymHours(3, wednesdayHours));

        // Thursday
        List thursdayHours = new ArrayList<Hours>();
        thursdayHours.add(new Hours(
                LocalTime.of(0, 0, 0),
                LocalTime.of(10, 0, 0)
        ));
        thursdayHours.add(new Hours(
                LocalTime.of(12, 0, 0),
                LocalTime.of(0, 0, 0)
        ));
        super.getGymHoursList().add(new GymHours(4, thursdayHours));

        // Friday
        List fridayHours = new ArrayList<Hours>();
        fridayHours.add(new Hours(
                LocalTime.of(6, 0, 0),
                LocalTime.of(0, 0, 0)
        ));
        super.getGymHoursList().add(new GymHours(5, fridayHours));

        //Saturday
        List saturdayHours = new ArrayList<Hours>();
        saturdayHours.add(new Hours(
                LocalTime.of(0, 0, 0),
                LocalTime.of(0, 0, 0)
        ));
        super.getGymHoursList().add(new GymHours(6, saturdayHours));

        //Sunday
        List sundayHours = new ArrayList<Hours>();
        sundayHours.add(new Hours(
                LocalTime.of(0, 0, 0),
                LocalTime.of(20, 0, 0)
        ));
        super.getGymHoursList().add(new GymHours(7, sundayHours));
    }
}
