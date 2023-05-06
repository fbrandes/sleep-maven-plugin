package de.fbrandes.maven;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder
public class SleepConfiguration {
    private final long hours;
    private final long minutes;
    private final long seconds;
    private final long millis;

    public Duration getDuration() {
        return Duration.ofHours(hours)
                .plusMinutes(minutes)
                .plusSeconds(seconds)
                .plusMillis(millis);
    }
}
