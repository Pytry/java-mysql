package test.xitikit.examples.java.mysql.logwatch.data;

import org.junit.jupiter.api.Test;
import org.xitikit.examples.java.mysql.logwatch.cli.LogEntryLoaderException;

import static org.junit.jupiter.api.Assertions.*;

class LogEntryLoaderExceptionTest{

    @Test
    void LogEntryLoaderException_Should_Override_Super_Constructors(){

        assertThrows(
            LogEntryLoaderException.class,
            () -> {
                throw new LogEntryLoaderException();
            });
        assertThrows(
            LogEntryLoaderException.class,
            () -> {
                throw new LogEntryLoaderException("message only");
            });
        assertThrows(
            LogEntryLoaderException.class,
            () -> {
                throw new LogEntryLoaderException("message", new Exception("and exception"));
            });
        assertThrows(
            LogEntryLoaderException.class,
            () -> {
                throw new LogEntryLoaderException(new Exception("exception only"));
            });
    }
}