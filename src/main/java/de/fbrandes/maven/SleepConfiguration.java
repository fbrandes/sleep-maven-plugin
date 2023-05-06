package de.fbrandes.maven;

import lombok.Builder;

import java.time.Duration;

@Builder
public class SleepConfiguration {
    private final long hours;
    private final long minutes;
    private final long seconds;
    private final long millis;
    private final long nanos;

    public Duration getDuration() {
        return Duration.ofHours(hours)
                .plusMinutes(minutes)
                .plusSeconds(seconds)
                .plusMillis(millis)
                .plusNanos(millis);
    }
}
