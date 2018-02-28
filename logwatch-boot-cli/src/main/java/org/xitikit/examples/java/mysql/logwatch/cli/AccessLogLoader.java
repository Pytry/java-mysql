package org.xitikit.examples.java.mysql.logwatch.cli;

import java.nio.file.Path;

public interface AccessLogLoader{

    void parseAndSaveAccessLogEntries(Path accessLogPath);
}
