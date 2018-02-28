package test.xitikit.examples.java.mysql.logwatch.data;

import org.junit.jupiter.api.Test;
import org.xitikit.examples.java.mysql.logwatch.data.LogEntry;
import org.xitikit.examples.java.mysql.logwatch.cli.LogEntryParser;
import org.xitikit.examples.java.mysql.logwatch.data.LogEntryValidationException;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class LogEntryParserTest{

    @Test
    void stringToLogEntryWithGoodEntry(){

        final String good = "" +
            "2017-01-01 00:00:11.763|" +
            "192.168.234.82|" +
            "\"GET / HTTP/1.1\"|" +
            "200|" +
            "\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";

        LogEntry logEntry = LogEntryParser.stringToLogEntry(good);
        assertNotNull(logEntry);
        assertNull(logEntry.getId());//new entries should not set the id. Integration tests will verify auto-generated values.
        assertEquals(
            "2017-01-01 00:00:11.763",
            DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                .format(logEntry.getDate()));
        assertEquals("192.168.234.82", logEntry.getIpv4());
        assertEquals("\"GET / HTTP/1.1\"", logEntry.getRequest());
        assertEquals(new Integer(200), logEntry.getStatus());
        assertEquals(
            "\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"",
            logEntry.getUserAgent());
    }

    @Test
    void invalidDateFormatShouldThrowValidationException(){

        final String badDate = "" +
            "01/01/2017 00:00:11.763|" +
            "192.168.234.82|" +
            "\"GET / HTTP/1.1\"|" +
            "200|" +
            "\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";

        assertThrows(
            LogEntryValidationException.class,
            () -> LogEntryParser.stringToLogEntry(badDate));
    }

    @Test
    void nonIntegerStatusShouldThrowValidationException(){

        final String badStatus = "" +
            "2017-01-01 00:00:11.763|" +
            "192.168.234.82|" +
            "\"GET / HTTP/1.1\"|" +
            "HTTP 200 OK|" +
            "\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";

        assertThrows(
            LogEntryValidationException.class,
            () -> LogEntryParser.stringToLogEntry(badStatus));
    }

    @Test
    void incorrectNumberOfColumnsShouldThrowValidationException(){

        final String badStatus = "" +
            "2017-01-01 00:00:11.763|" +
            "192.168.234.82|" +
            "\"GET / HTTP/1.1\"|" +
            "HTTP 200 OK";

        assertThrows(
            LogEntryValidationException.class,
            () -> LogEntryParser.stringToLogEntry(badStatus));
    }

    @Test
    void everythingElseShouldNotBeValidatedUntilPersisted(){

        final String badButShouldNotThrowException = "" +
            "2017-01-01 00:00:11.763|" +
            "192.168.234.82.wrong|" +
            " |" +
            "200|" +
            //This String is 260 characters, which is longer than the allowed 256.
            "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";

        assertNotNull(LogEntryParser.stringToLogEntry(badButShouldNotThrowException));
    }
}