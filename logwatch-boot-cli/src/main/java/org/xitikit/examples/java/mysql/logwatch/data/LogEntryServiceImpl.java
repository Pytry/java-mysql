package org.xitikit.examples.java.mysql.logwatch.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.xitikit.examples.java.mysql.logwatch.cli.LogEntryLoaderException;
import org.xitikit.examples.java.mysql.logwatch.cli.LogEntryParser;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        return allByCustomQuery == null ? new ArrayList<>(): allByCustomQuery;
    }

    @Override
    public void persistLogFileEntries(final Path accessLogPath){

        if(!accessLogPath.toFile().canRead()){
            throw new LogEntryLoaderException("Cannot read file at location " + accessLogPath);
        }
        try(BufferedReader reader = Files.newBufferedReader(accessLogPath)){
            reader
                .lines()
                .parallel()
                .map(LogEntryParser::stringToLogEntry)
                .forEach(logEntryRepository::save);
        }
        catch(IOException e){
            e.printStackTrace();
            throw new LogEntryLoaderException("Error reading the access log at " + accessLogPath, e);
        }
    }
}