package org.xitikit.examples.java.mysql.logwatch.data;

import java.nio.file.Path;
import java.util.List;

public interface LogEntryService{

    List<AccessLogSearchResult> findAddressesThatExceedThreshold(Ipv4SearchQuery ipv4SearchQuery);

    void persistLogFileEntries(Path accessLogPath);
}
