package de.fbrandes.maven;

import lombok.Setter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public void execute() throws MojoExecutionException {
        try {
            SleepConfiguration sleepConfiguration = SleepConfiguration.builder()
                    .minutes(minutes)
                    .seconds(seconds)
                    .millis(millis)
                    .build();

            long sleepDuration = sleepConfiguration.getDuration().toMillis();

            LOGGER.info("Sleeping for {} milliseconds", sleepDuration);
            Thread.sleep(sleepDuration);
            LOGGER.info("Finished sleeping");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MojoExecutionException(e);
        }
    }
}
