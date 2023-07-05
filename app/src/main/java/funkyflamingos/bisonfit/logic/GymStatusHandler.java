package funkyflamingos.bisonfit.logic;

import java.time.*;

import funkyflamingos.bisonfit.application.Services;
import funkyflamingos.bisonfit.dso.GymHours;
import funkyflamingos.bisonfit.persistence.IGymHoursPersistence;
import funkyflamingos.bisonfit.persistence.stubs.GymHoursPersistenceStub;

public class GymStatusHandler {
    private IGymHoursPersistence persistence;

    // Constructor for the database

  //  public GymStatusHandler() {
  //      this.persistence = Services.getGymHoursPersistence();;
 //   }


    public GymStatusHandler(IGymHoursPersistence persistence) {
        this.persistence = persistence;
    }


    // Constructor for the stub
    public GymStatusHandler() {
        this.persistence = new GymHoursPersistenceStub();
    }

    public String getGymStatus() {
        Clock clock = Clock.systemDefaultZone();
        return getGymStatus(clock);
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

        LocalTime closingTime = todaysHours.getHours().get(0).getClosing();
        LocalTime openTime = todaysHours.getHours().get(0).getOpening();

        Duration currToOpening = Duration.between(currentTime, openTime);
        Duration currToClosing = Duration.between(currentTime, closingTime);
        Duration duration;

        if (gymIsOpen(currToOpening, currToClosing)) {
            duration = currToClosing;
            result = UNTIL_CLOSING;
        } else if (gymIsClosedAndTimeBeforeMidnight(currToOpening, currToClosing)) {
            duration = Duration.between(currentTime, LocalTime.MAX).plus(Duration.between(LocalTime.MIDNIGHT, tomorrowsHours.getHours().get(0).getOpening()));
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

    private boolean gymIsClosedAndTimeBeforeMidnight(Duration currToOpening, Duration currToClosing) {
        boolean atClosing = currToClosing.isZero();
        boolean afterClosingAndBeforeMidnight = currToClosing.isNegative() && currToOpening.isNegative();
        return atClosing || afterClosingAndBeforeMidnight;
    }

    private String getFormattedTime(String durationToString) {
        String output = durationToString.substring(2);

        if (output.contains("M")) {
            output = output.replaceAll("M[0-9.S]*", "m").toLowerCase().replaceAll("h", "h ");
        } else {
            if (output.contains("H")) {
                output = output.replaceAll("H[0-9.S]*", "h 0m");
            } else {
                output = "<1 Minute";
            }
        }
        return output;
    }
}
