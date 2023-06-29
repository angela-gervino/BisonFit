package funkyflamingos.bisonfit.persistence.stubs;

import static funkyflamingos.bisonfit.dso.GymHours.DAYS_PER_WEEK;
import static funkyflamingos.bisonfit.dso.GymHours.getDayOfWeek;
import static funkyflamingos.bisonfit.dso.GymHours.getNextDayOfWeek;

import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.dso.Hours;
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;

import java.time.*;
import java.util.*;
import java.util.ArrayList;

public class GymHoursPersistenceStub implements IGymHoursPersistence {

    private List<GymHours> gymHoursList;


    public GymHoursPersistenceStub() {
        this.gymHoursList = new ArrayList<>();

        // Monday
        List mondayHours = new ArrayList<Hours>();
        mondayHours.add(new Hours(
                LocalTime.of(6, 0, 0),
                LocalTime.of(22, 0, 0)
        ));
        gymHoursList.add(new GymHours(1, mondayHours));

        // Tuesday
        List tuesdayHours = new ArrayList<Hours>();
        tuesdayHours.add(new Hours(
                LocalTime.of(6, 0, 0),
                LocalTime.of(22, 0, 0)
        ));
        gymHoursList.add(new GymHours(2, tuesdayHours));

        // Wednesday
        List wednesdayHours = new ArrayList<Hours>();
        wednesdayHours.add(new Hours(
                LocalTime.of(6, 0, 0),
                LocalTime.of(22, 0, 0)
        ));
        gymHoursList.add(new GymHours(3, wednesdayHours));

        // Thursday
        List thursdayHours = new ArrayList<Hours>();
        thursdayHours.add(new Hours(
                LocalTime.of(6, 0, 0),
                LocalTime.of(0, 0, 0)
        ));
        gymHoursList.add(new GymHours(4, thursdayHours));

        // Friday
        List fridayHours = new ArrayList<Hours>();
        fridayHours.add(new Hours(
                LocalTime.of(0, 0, 0),
                LocalTime.of(0, 0, 0)
        ));
        gymHoursList.add(new GymHours(5, fridayHours));

        //Saturday
        List saturdayHours = new ArrayList<Hours>();
        saturdayHours.add(new Hours(
                LocalTime.of(0, 0, 0),
                LocalTime.of(20, 0, 0)
        ));
        gymHoursList.add(new GymHours(6, saturdayHours));

        //Sunday
        List sundayHours = new ArrayList<Hours>();
        sundayHours.add(new Hours(
                LocalTime.of(8, 0, 0),
                LocalTime.of(20, 0, 0)
        ));
        gymHoursList.add(new GymHours(7, sundayHours));
    }

    @Override
    public List<GymHours> getNextWeekHours(LocalDate today) {
        List<GymHours> nextWeekHours = new ArrayList<GymHours>();
        int dayOfWeek = getDayOfWeek(today);

        for (int i = 0; i < DAYS_PER_WEEK; i++) {
            nextWeekHours.add(getHoursByID(dayOfWeek));
            dayOfWeek = getNextDayOfWeek(dayOfWeek);
        }

        return nextWeekHours;
    }

    @Override
    public GymHours getHoursByID(int dayID) {
        for (GymHours weekday : gymHoursList)
            if (weekday.getDayID() == dayID)
                return weekday;
        return null;
    }
}
