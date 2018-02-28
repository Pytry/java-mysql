package org.xitikit.examples.java.mysql.logwatch.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xitikit.examples.java.mysql.logwatch.data.LogEntryRepository;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.stream.Collectors.*;

@Service
@Transactional
public class AccessLogLoaderImpl implements AccessLogLoader{

    private final LogEntryRepository logEntryRepository;

    @Autowired
    public AccessLogLoaderImpl(final LogEntryRepository logEntryRepository){

        this.logEntryRepository = logEntryRepository;
    }

    @Override
    public void parseAndSaveAccessLogEntries(final Path accessLogPath){

        if(!accessLogPath.toFile().canRead()){
            throw new LogEntryLoaderException("Cannot read file at location " + accessLogPath);
        }
        try(BufferedReader reader = Files.newBufferedReader(accessLogPath)){

            logEntryRepository.saveAll(
                reader
                    .lines()
                    .parallel()
                    .map(LogEntryParser::stringToLogEntry)
                    .collect(toList()));
        }
        catch(IOException e){
            e.printStackTrace();
            throw new LogEntryLoaderException("Error reading the access log at " + accessLogPath, e);
        }
    }
}
