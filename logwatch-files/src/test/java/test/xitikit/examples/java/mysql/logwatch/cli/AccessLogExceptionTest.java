package test.xitikit.examples.java.mysql.logwatch.cli;

import org.junit.jupiter.api.Test;
import org.xitikit.examples.java.mysql.logwatch.files.AccessLogException;

import static org.junit.jupiter.api.Assertions.*;

class AccessLogExceptionTest{

    @Test
    void LogEntryValidationException_Should_Override_Super_Constructors(){

        assertThrows(
            AccessLogException.class,
            () -> {
                throw new AccessLogException();
            });
        assertThrows(
            AccessLogException.class,
            () -> {
                throw new AccessLogException("message only");
            });
        assertThrows(
            AccessLogException.class,
            () -> {
                throw new AccessLogException("message", new Exception("and exception"));
            });
        assertThrows(
            AccessLogException.class,
            () -> {
                throw new AccessLogException(new Exception("exception only"));
            });
    }
}