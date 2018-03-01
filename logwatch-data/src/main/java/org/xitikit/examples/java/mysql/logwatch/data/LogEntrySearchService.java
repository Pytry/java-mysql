package org.xitikit.examples.java.mysql.logwatch.data;

import java.util.List;

public interface LogEntrySearchService{

    List<LogEntrySearchResult> findAddressesThatExceedThreshold(Ipv4SearchQuery ipv4SearchQuery);
}
