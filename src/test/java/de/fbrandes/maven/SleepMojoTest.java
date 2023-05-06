package de.fbrandes.maven;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class SleepMojoTest {
    @Test
    void shouldSleep() {
        //given
        SleepMojo sleepMojo = new SleepMojo();
        sleepMojo.setSeconds(3);

        // then
        Awaitility.await()
                .atLeast(3000, TimeUnit.MILLISECONDS)
                .atMost(3500, TimeUnit.MILLISECONDS)
                .untilAsserted(sleepMojo::execute);
    }
}