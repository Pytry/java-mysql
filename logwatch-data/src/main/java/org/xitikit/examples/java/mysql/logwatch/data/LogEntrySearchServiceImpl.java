package org.xitikit.examples.java.mysql.logwatch.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This services forgoes transactions in favor of speed.
 */
@Service
public class LogEntrySearchServiceImpl implements LogEntrySearchService{

    private final LogEntryRepository logEntryRepository;

    @Autowired
    public LogEntrySearchServiceImpl(final LogEntryRepository logEntryRepository){

        this.logEntryRepository = logEntryRepository;
    }

    @Override
    public List<LogEntrySearchResult> findAddressesThatExceedThreshold(final Ipv4SearchQuery ipv4SearchQuery){

        List<LogEntrySearchResult> allByCustomQuery = logEntryRepository.findAllByDateRangeAndThreshold(
            ipv4SearchQuery.getStartDate(),
            ipv4SearchQuery.getEndDate(),
            ipv4SearchQuery.getThreshold()
        );
        return allByCustomQuery == null ? new ArrayList<>() : allByCustomQuery;
    }
}