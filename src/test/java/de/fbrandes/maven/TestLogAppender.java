package de.fbrandes.maven;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.List;

public class TestLogAppender extends AppenderBase<ILoggingEvent> {
    static List<String> log = new ArrayList<>();

    @Override
    protected void append(ILoggingEvent e) {
        log.add(e.getFormattedMessage());
    }

    public List<String> getLog() {
        return log;
    }

    public void clearLog() {
        log.clear();
    }
}