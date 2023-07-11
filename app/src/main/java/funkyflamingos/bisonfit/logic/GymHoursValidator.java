package funkyflamingos.bisonfit.logic;

import static funkyflamingos.bisonfit.dso.GymHours.DAYS_PER_WEEK;
import static funkyflamingos.bisonfit.dso.GymHours.getDayOfWeek;
import static funkyflamingos.bisonfit.dso.GymHours.getNextDayOfWeek;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.dso.Hours;
import funkyflamingos.bisonfit.logic.exceptions.GymHoursNotConsecutiveException;
import funkyflamingos.bisonfit.logic.exceptions.GymHoursOverAWeekException;
import funkyflamingos.bisonfit.logic.exceptions.GymHoursNotStartTodayException;
import funkyflamingos.bisonfit.logic.exceptions.HoursEmptyException;
import funkyflamingos.bisonfit.logic.exceptions.HoursOrderException;
import funkyflamingos.bisonfit.logic.exceptions.NullGymHoursException;
import funkyflamingos.bisonfit.logic.exceptions.NullHoursException;

public class GymHoursValidator implements IGymHoursValidator {
    public static void validateGymHours(List<GymHours> nextWeekHours, LocalDate today) throws Exception {

        // should be truthy
        if (nextWeekHours == null)
            throw new NullGymHoursException("GymHours should not be null.");

        // size should be one week
        if (nextWeekHours.size() != DAYS_PER_WEEK)
            throw new GymHoursOverAWeekException("GymHours should be exactly 7 days long.");

        int todayID = getDayOfWeek(today);
        int firstID = nextWeekHours.get(0).getDayID();

        // first day should be today
        if (todayID != firstID)
            throw new GymHoursNotStartTodayException("First day ID does not match today.");

        // validate hours
        for (GymHours gymHours : nextWeekHours) {

            // should be truthy
            if (gymHours == null)
                throw new NullGymHoursException("GymHours should not be null.");

            validateHours(gymHours.getHours());
        }

        // days should be consecutive
        int currID = getNextDayOfWeek(todayID);

        for (int i = 1; i < nextWeekHours.size(); i++) {

            // days should be consecutive
            if (nextWeekHours.get(i).getDayID() != currID)
                throw new GymHoursNotConsecutiveException("Days should be consecutive.");

            currID = getNextDayOfWeek(currID);
        }
    }

    public static void validateHours(List<Hours> hours) throws Exception {

        // hours can be null
        if (hours != null) {

            // hours should not be empty
            if (hours.size() == 0)
                throw new HoursEmptyException("Hours should not be empty.");

            // all opening and closing times should be consecutive and not equal
            // except for the special case that the first opening and
            // last closing times can be both be zero
            LocalTime prev = LocalTime.MIN;
            LocalTime curr = null;
            for (int i = 0; i < hours.size(); i++) {

                // check list
                if (hours.get(i) == null)
                    throw new NullHoursException("Hours should not be null.");

                // check opening
                curr = hours.get(i).getOpening();
                if (curr == null)
                    throw new NullHoursException("Opening should not be null.");

                if (i != 0 && curr.compareTo(prev) <= 0)
                    throw new HoursOrderException("Opening should be after last closing.");

                prev = curr;

                // check closing
                curr = hours.get(i).getClosing();
                if (curr == null)
                    throw new NullHoursException("Closing should not be null.");

                if (i == hours.size() - 1 && curr.equals(LocalTime.MIDNIGHT))
                    continue;
                if (curr.compareTo(prev) <= 0)
                    throw new HoursOrderException("Closing should be after last opening.");

                prev = curr;
            }
        }
    }
}
