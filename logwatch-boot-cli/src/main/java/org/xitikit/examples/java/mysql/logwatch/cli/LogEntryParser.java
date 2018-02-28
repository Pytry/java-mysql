package org.xitikit.examples.java.mysql.logwatch.cli;

import org.xitikit.examples.java.mysql.logwatch.data.LogEntry;
import org.xitikit.examples.java.mysql.logwatch.data.LogEntryValidationException;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LogEntryParser{

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static LogEntry stringToLogEntry(String input){

        try{
            return convert(separate(input));
        }
        catch(DateTimeException e){
            throw new LogEntryValidationException("An Invalid Format for the  Date was found. The expected format is \"yyyy-MM-dd HH:mm:ss.SSS\"", e);
        }
        catch(NumberFormatException e){
            throw new LogEntryValidationException("A non integer value was found for the HTTP status code.", e);
        }
    }

    private static LogEntry convert(final String[] separatedValues){

        if(separatedValues == null || separatedValues.length != 5){
            throw new LogEntryValidationException("An unexpected log format was encounter. Expected 5 '|' delimited rows.");
        }

        return new LogEntry(
            date(separatedValues[0]),
            separatedValues[1],
            separatedValues[2],
            httpStatusCode(separatedValues[3]),
            separatedValues[4]
        );
    }

    private static Integer httpStatusCode(final String httpStatusCode){

        return Integer.valueOf(httpStatusCode);
    }

    private static LocalDateTime date(final String from){

        return LocalDateTime.from(formatter.parse(from));
    }

    /**
     * Provides null safe splitting of the given value.
     */
    private static String[] separate(String line){

        return line == null ? null : line.split("\\|");
    }
}
