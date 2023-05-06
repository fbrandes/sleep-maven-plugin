package de.fbrandes.maven;

import lombok.Setter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

@Setter
@Mojo(name = "sleep", defaultPhase = LifecyclePhase.NONE, threadSafe = true)
public class SleepMojo extends AbstractMojo {
    private static final Logger LOGGER = LoggerFactory.getLogger(SleepMojo.class);

    @Parameter(property = "hours", defaultValue = "0L")
    private long hours;
    @Parameter(property = "minutes", defaultValue = "0L")
    private long minutes;
    @Parameter(property = "seconds", defaultValue = "0L")
    private long seconds;
    @Parameter(property = "millis", defaultValue = "0L")
    private long millis;
    @Parameter(property = "nanos", defaultValue = "0L")
    private long nanos;

    public void execute() throws MojoExecutionException {
        checkForNegativeDurations();

        try {
            SleepConfiguration sleepConfiguration = SleepConfiguration.builder()
                    .hours(hours)
                    .minutes(minutes)
                    .seconds(seconds)
                    .millis(millis)
                    .nanos(nanos)
                    .build();

            Duration sleepDuration = sleepConfiguration.getDuration();

            if (sleepDuration.isZero()) {
                LOGGER.info("No sleep configured");
            } else {
                String sleepMessage = SleepLogFormatter.formatDuration(sleepDuration);
                LOGGER.info("Sleeping for {}", sleepMessage);
                Thread.sleep(sleepDuration.toMillis());
            }

            LOGGER.info("Finished sleeping");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MojoExecutionException(e);
        }
    }

    private void checkForNegativeDurations() throws MojoExecutionException {
        if(hours < 0 || minutes < 0 || seconds < 0 || millis < 0 || nanos < 0) {
            throw new MojoExecutionException("Negative values are not allowed for sleep duration");
        }
    }
}
