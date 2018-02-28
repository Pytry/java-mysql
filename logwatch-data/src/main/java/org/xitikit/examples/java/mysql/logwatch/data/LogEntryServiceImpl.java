package org.xitikit.examples.java.mysql.logwatch.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static javax.transaction.Transactional.TxType.*;

@Service("logEntryService")
@Transactional(
    value = REQUIRES_NEW,
    rollbackOn = Exception.class)
public class LogEntryServiceImpl implements LogEntryService{

    private final LogEntryRepository logEntryRepository;

    @Autowired
    public LogEntryServiceImpl(@Qualifier("logEntryRepository") final LogEntryRepository logEntryRepository){

        this.logEntryRepository = logEntryRepository;
    }

    @Override
    public List<AccessLogSearchResult> findAddressesThatExceedThreshold(final Ipv4SearchQuery ipv4SearchQuery){

        List<AccessLogSearchResult> allByCustomQuery = logEntryRepository.findAllByCustomQuery(
            ipv4SearchQuery.getStartDate(),
            ipv4SearchQuery.getEndDate(),
            ipv4SearchQuery.getThreshold()
        );
        return allByCustomQuery == null ? new ArrayList<>() : allByCustomQuery;
    }
}