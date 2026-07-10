package data.app.Clock;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Alarm {

    private LocalDateTime alarmTime;
    private boolean active = true;

    public Alarm(LocalDateTime alarmTime) {
        this.alarmTime = alarmTime;
    }

    public LocalDateTime getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(LocalDateTime alarmTime) {
        this.alarmTime = alarmTime;
    }

    public boolean isActive() {
        return active;
    }

    public void stop() {
        active = false;
    }

    // Remaining time
    public String getRemainingTime() {

        Duration duration = Duration.between(LocalDateTime.now(), alarmTime);

        if (duration.isNegative()) {
            return "Expired";
        }

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        return String.format("%02d:%02d:%02d remaining",
                hours,
                minutes,
                seconds);
    }

    @Override
    public String toString() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("hh:mm a");

        return alarmTime.format(formatter)
                + "   ("
                + getRemainingTime()
                + ")";
    }
}