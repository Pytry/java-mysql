package test.xitikit.examples.java.mysql.logwatch.simple;

import org.junit.jupiter.api.Test;
import org.xitikit.examples.java.mysql.logwatch.simple.LogEntry;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LogEntryTest{

    @Test
    void logEntryWYSIWYG(){

        LogEntry logEntry;

        logEntry = new LogEntry();
        assertNull(logEntry.getId());
        assertNull(logEntry.getDate());
        assertNull(logEntry.getIpv4());
        assertNull(logEntry.getRequest());
        assertNull(logEntry.getStatus());
        assertNull(logEntry.getUserAgent());

        //This String is 260 characters, which is longer than the allowed 256.
        final String reallyLongUserAgent = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        logEntry = new LogEntry(
            //The values for this are obviously wrong, but all the same they should not be validated nor modified yet.
            null,
            " \t\n\r192.168.234.82 \t\n\r",
            " \t\n\r",
            10_000,
            reallyLongUserAgent
        );
        assertNull(logEntry.getId());
        assertNull(logEntry.getDate());
        assertEquals(" \t\n\r192.168.234.82 \t\n\r", logEntry.getIpv4());
        assertEquals(" \t\n\r", logEntry.getRequest());
        assertEquals(new Integer(10_000), logEntry.getStatus());
        assertEquals(reallyLongUserAgent, logEntry.getUserAgent());

        LocalDateTime date = LocalDateTime.now();
        Integer id = -10_000;//This is not a valid value.
        logEntry.setId(id);
        logEntry.setDate(date);//This is valid
        logEntry.setIpv4(null);
        logEntry.setRequest(null);
        logEntry.setStatus(null);
        logEntry.setUserAgent(null);

        assertEquals(id, logEntry.getId());
        assertEquals(date, logEntry.getDate());
        assertTrue(date == logEntry.getDate());//Yes, this is a reference check. Setters should not clone or create new objects
        assertNull(logEntry.getIpv4());
        assertNull(logEntry.getRequest());
        assertNull(logEntry.getStatus());
        assertNull(logEntry.getUserAgent());
    }
}