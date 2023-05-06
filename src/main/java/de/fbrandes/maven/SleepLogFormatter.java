package de.fbrandes.maven;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SleepLogFormatter {
    public static String formatDuration(Duration sleepDuration) {
        StringBuilder sb = new StringBuilder();
        long hoursToSleep = sleepDuration.toHours();
        if (hoursToSleep > 0) {
            sb.append(hoursToSleep).append("h ");
        }
        long minutesPart = sleepDuration.toMinutesPart();
        if (minutesPart > 0) {
            sb.append(minutesPart).append("m ");
        }
        long secondsPart = sleepDuration.toSecondsPart();
        if (secondsPart > 0) {
            sb.append(secondsPart).append("s ");
        }

        formatMillis(sleepDuration, sb);
        return sb.toString().trim();
    }

    private static void formatMillis(Duration sleepDuration, StringBuilder stringBuilder) {
        String nanosPart = String.valueOf(sleepDuration.toNanosPart());
        String millis = nanosPart.substring(0,1);
        String nanos = nanosPart.substring(1);

        if(sleepDuration.toMillisPart() > 0 && sleepDuration.toNanosPart() > 0) {
            stringBuilder.append(millis).append(".").append(nanos).append("ms");
        }
    }
}
