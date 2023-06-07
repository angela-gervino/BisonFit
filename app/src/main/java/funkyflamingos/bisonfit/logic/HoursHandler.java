package funkyflamingos.bisonfit.logic;

import java.time.*;

import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.persistence.IGymStatusPersistence;

public class HoursHandler {
    private IGymStatusPersistence persistence;

    public HoursHandler(IGymStatusPersistence persistence) {
        this.persistence = persistence;
    }

    public String getGymStatus(Clock clock) {
        String result;
        String UNTIL_CLOSING = " Until Closing";
        String UNTIL_OPENING = " Until Opening";

        LocalTime currentTime = LocalTime.now(clock);
        LocalDate currentDay = LocalDate.now(clock);

        DayOfWeek day = currentDay.getDayOfWeek();
        int dayOfWeekTodayAsInt = day.getValue();
        int dayOfWeekTomorrowAsInt = dayOfWeekTodayAsInt == 7 ? 1 : dayOfWeekTodayAsInt + 1;

        GymHours todaysHours = persistence.getHoursByID(dayOfWeekTodayAsInt);
        GymHours tomorrowsHours = persistence.getHoursByID(dayOfWeekTomorrowAsInt);

        LocalTime closingTime = todaysHours.getClosing();
        LocalTime openTime = todaysHours.getOpening();

        Duration currToOpening = Duration.between(currentTime, openTime);
        Duration currToClosing = Duration.between(currentTime, closingTime);
        Duration duration;

        if (gymIsOpen(currToOpening, currToClosing)) {
            duration = currToClosing;
            result = UNTIL_CLOSING;
        } else if (gymIsClosedAndTimeBeforeMidnight(currToOpening, currToClosing)) {
            duration = Duration.between(currentTime, LocalTime.MAX).plus(Duration.between(LocalTime.MIDNIGHT, tomorrowsHours.getOpening()));
            duration = duration.plusSeconds(1);
            result = UNTIL_OPENING;
        } else { // gym is closed and the current time after midnight
            duration = currToOpening;
            result = UNTIL_OPENING;
        }
        return getFormattedTime(duration.toString()) + result;
    }

    private boolean gymIsOpen(Duration currToOpening, Duration currToClosing) {
        boolean atOpening = currToOpening.isZero();
        boolean betweenOpeningAndClosing = currToOpening.isNegative() && !currToClosing.isNegative() && !currToClosing.isZero();
        return atOpening || betweenOpeningAndClosing;
    }

    private boolean gymIsClosedAndTimeBeforeMidnight(Duration currToOpening, Duration currToClosing)
    {
        boolean atClosing = currToClosing.isZero();
        boolean afterClosingAndBeforeMidnight = currToClosing.isNegative() && currToOpening.isNegative();
        return atClosing || afterClosingAndBeforeMidnight;
    }

    private String getFormattedTime(String durationToString) {
        String output = durationToString.substring(2)
                .replaceAll("[.][1-9]+S", "s")
                .replaceAll("H", "h ")
                .replaceAll("M", "m ")
                .replaceAll("S", "s");

        if (!output.contains("h")) {
            output = "0h " + output;
        }

        if (!output.contains("m")) {
            output = output.replaceAll("h", "h 0m");
        }

        if (!output.contains("s")) {
            output = output + "0s";
        }
        return output;
    }
}
