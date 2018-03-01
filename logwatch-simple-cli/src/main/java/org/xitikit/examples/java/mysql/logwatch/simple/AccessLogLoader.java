package org.xitikit.examples.java.mysql.logwatch.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AccessLogLoader{

    private final LogEntryRepository logEntryRepository;

    public AccessLogLoader(final LogEntryRepository logEntryRepository){

        this.logEntryRepository = logEntryRepository;
    }

    public void parseAndSaveAccessLogEntries(final Path accessLogPath){

        if(accessLogPath == null || !accessLogPath.toFile().canRead()){
            throw new AccessLogLoaderException("Cannot read file at location " + accessLogPath);
        }
        try(BufferedReader reader = Files.newBufferedReader(accessLogPath)){
            reader
                .lines()
                .map(AccessLogParser::stringToLogEntry)
                .forEach(logEntryRepository::save);
        }
        catch(IOException e){
            e.printStackTrace();
            throw new AccessLogLoaderException("Error reading the access log at " + accessLogPath, e);
        }
    }

    public class AccessLogLoaderException extends IllegalArgumentException{

        AccessLogLoaderException(final String message, final Exception e){

            super(message, e);
        }

        AccessLogLoaderException(final String message){

            super(message);
        }
    }
}
