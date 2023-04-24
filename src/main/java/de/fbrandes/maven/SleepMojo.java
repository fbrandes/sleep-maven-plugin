package de.fbrandes.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

@Mojo(name = "sleep", defaultPhase = LifecyclePhase.NONE)
public class SleepMojo extends AbstractMojo {
    private static final Logger LOGGER = LoggerFactory.getLogger(SleepMojo.class);

    @Parameter(property = "hours")
    long hours = 0L;
    @Parameter(property = "minutes")
    long minutes = 0L;
    @Parameter(property = "seconds")
    long seconds = 0L;
    @Parameter(property = "millis")
    long millis = 0L;

    public void execute() throws MojoExecutionException {
        try {
            long sleepDuration = Duration.ofHours(hours)
                    .plusMinutes(minutes)
                    .plusSeconds(seconds)
                    .plusMillis(millis)
                    .toMillis();

            LOGGER.info("Sleeping for {} milliseconds", sleepDuration);
            Thread.sleep(sleepDuration);
            LOGGER.info("Finished sleeping");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MojoExecutionException(e);
        }
    }
}
