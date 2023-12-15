package de.fbrandes.maven;

import ch.qos.logback.classic.Logger;
import org.apache.maven.plugin.MojoExecutionException;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

class SleepMojoTest {
    private final Logger testLogger = (Logger) LoggerFactory.getLogger(SleepMojo.class);
    private TestLogAppender testLogAppender;

    @BeforeEach
    void setup() {
        testLogAppender = new TestLogAppender();
        testLogAppender.start();
        testLogger.addAppender(testLogAppender);
    }

    @Test
    void shouldSleepForThreeSeconds() {
        //given
        SleepMojo sleepMojo = new SleepMojo();
        sleepMojo.setSeconds(3);

        // then
        Awaitility.await()
                .atLeast(3000, TimeUnit.MILLISECONDS)
                .atMost(3500, TimeUnit.MILLISECONDS)
                .untilAsserted(sleepMojo::execute);

        Assertions.assertEquals("Sleeping for 3s", testLogAppender.getLog().get(0));
        Assertions.assertEquals("Finished sleeping", testLogAppender.getLog().get(1));

    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 0, 0, No sleep configured",
            "1, 0, 0, 1, 1, Sleeping for 1h 1.000001ms",
            "0, 1, 0, 1, 1, Sleeping for 1m 1.000001ms",
            "0, 0, 1, 1, 1, Sleeping for 1s 1.000001ms",
            "0, 0, 0, 1, 1, Sleeping for 1.000001ms",
            "0, 0, 0, 0, 1, Sleeping for 0.000001ms",
            "0, 0, 0, 0, 123456, Sleeping for 0.123456ms",
            "0, 0, 0, 1, 0, Sleeping for 1m"
    })
    void shouldSleepForGivenAmountOfTime(int hours, int minutes, int seconds, int millis, int nanos, String expectedLog) {
        SleepMojo sleepMojo = new SleepMojo();
        sleepMojo.setHours(hours);
        sleepMojo.setMinutes(minutes);
        sleepMojo.setSeconds(seconds);
        sleepMojo.setMillis(millis);
        sleepMojo.setNanos(nanos);

        try {
            Awaitility.await().atMost(500, TimeUnit.MILLISECONDS).untilAsserted(sleepMojo::execute);
        } catch (ConditionTimeoutException e) {
            Assertions.assertTrue(testLogAppender.getLog().contains(expectedLog));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 0, 0, No sleep configured",
            "1, 0, 0, 0, 0, Sleeping for 1h",
            "0, 1, 0, 0, 0, Sleeping for 1m",
            "0, 0, 1, 0, 0, Sleeping for 1s",
            "0, 0, 0, 1, 0, Sleeping for 1ms",
            "0, 0, 0, 0, 1, Sleeping for 1n",
            "1, 1, 1, 1, 1, Sleeping for 1h 1m 1s 1.000001ms"
    })
    void shouldSleepCorrectAmountGivenMillisAndNanos(int hours, int minutes, int seconds, int millis, int nanos, String expectedLog) {
        SleepMojo sleepMojo = new SleepMojo();
        sleepMojo.setHours(hours);
        sleepMojo.setMinutes(minutes);
        sleepMojo.setSeconds(seconds);
        sleepMojo.setMillis(millis);
        sleepMojo.setNanos(nanos);

        try {
            Awaitility.await().atMost(500, TimeUnit.MILLISECONDS).untilAsserted(sleepMojo::execute);
        } catch (ConditionTimeoutException e) {
            Assertions.assertTrue(testLogAppender.getLog().contains(expectedLog));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 0, 0, 0, 0",
            "0, -1, 0, 0, 0",
            "0, 0, -1, 0, 0",
            "0, 0, 0, -1, 0",
            "0, 0, 0, 0, -1"
    })
    void shouldThrowWhenGivenNegativeValues(int hours, int minutes, int seconds, int millis, int nanos) {
        SleepMojo sleepMojo = new SleepMojo();
        sleepMojo.setHours(hours);
        sleepMojo.setMinutes(minutes);
        sleepMojo.setSeconds(seconds);
        sleepMojo.setMillis(millis);
        sleepMojo.setNanos(nanos);

        Assertions.assertThrows(MojoExecutionException.class, sleepMojo::execute);
    }

    @AfterEach
    void tearDown() {
        testLogAppender.clearLog();
        testLogger.detachAppender(testLogAppender);
    }
}