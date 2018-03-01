package org.xitikit.examples.java.mysql.logwatch.files;

public interface AccessLogService{

    void parseAndSaveAccessLogEntries(String accessLogPath);
}
