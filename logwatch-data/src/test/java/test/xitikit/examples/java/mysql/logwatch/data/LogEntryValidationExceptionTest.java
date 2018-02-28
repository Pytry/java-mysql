package test.xitikit.examples.java.mysql.logwatch.data;

import org.junit.jupiter.api.Test;
import org.xitikit.examples.java.mysql.logwatch.data.LogEntryValidationException;

import static org.junit.jupiter.api.Assertions.*;

class LogEntryValidationExceptionTest{

    @Test
    void LogEntryValidationException_Should_Override_Super_Constructors(){

        assertThrows(
            LogEntryValidationException.class,
            () -> {
                throw new LogEntryValidationException();
            });
        assertThrows(
            LogEntryValidationException.class,
            () -> {
                throw new LogEntryValidationException("message only");
            });
        assertThrows(
            LogEntryValidationException.class,
            () -> {
                throw new LogEntryValidationException("message", new Exception("and exception"));
            });
        assertThrows(
            LogEntryValidationException.class,
            () -> {
                throw new LogEntryValidationException(new Exception("exception only"));
            });
    }
}