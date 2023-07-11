package funkyflamingos.bisonfit.persistence;

import static funkyflamingos.bisonfit.dso.GymHours.DAYS_PER_WEEK;
import static funkyflamingos.bisonfit.dso.GymHours.getDayOfWeek;
import static funkyflamingos.bisonfit.logic.GymHoursHandler.getNextDayOfWeek;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import funkyflamingos.bisonfit.dso.GymHours;

public abstract class AbstractGymHoursPersistence implements IGymHoursPersistence {
    private List<GymHours> gymHoursList;

    public AbstractGymHoursPersistence() {
        this.gymHoursList = new ArrayList<>();
    }

    public List<GymHours> getGymHoursList() {
        return gymHoursList;
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

    private GymHours getHoursByID(int dayID) {
        for (GymHours weekday : gymHoursList)
            if (weekday.getDayID() == dayID)
                return weekday;
        return null;
    }
}
