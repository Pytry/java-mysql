package test.xitikit.examples.java.mysql.logwatch.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.xitikit.examples.java.mysql.logwatch.data.LogEntry;
import org.xitikit.examples.java.mysql.logwatch.data.LogEntryRepository;
import org.xitikit.examples.java.mysql.logwatch.files.AccessLogHelper;
import org.xitikit.examples.java.mysql.logwatch.files.AccessLogService;
import org.xitikit.examples.java.mysql.logwatch.files.AccessLogServiceImpl;

import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(PER_METHOD)
class AccessLogServiceTest{

    private LogEntryRepository repository = Mockito.mock(LogEntryRepository.class);

    private AccessLogService accessLogService = new AccessLogServiceImpl(repository);

    @Test
    void loadFileFromClasspath(){

        final String good = "" +
            "2017-01-01 00:00:11.763|" +
            "192.168.234.82|" +
            "\"GET / HTTP/1.1\"|" +
            "200|" +
            "\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";

        LogEntry question = AccessLogHelper.toLogEntry(good);
        question.setId(1);
        when(repository.save(any())).thenReturn(question);
        accessLogService.parseAndSaveAccessLogEntries("classpath: test.access.log");
        verify(
            repository,
            times(10)
        ).save(any());
    }

    @Test
    void loadFileFromFolder(){

        final String good = "" +
            "2017-01-01 00:00:11.763|" +
            "192.168.234.82|" +
            "\"GET / HTTP/1.1\"|" +
            "200|" +
            "\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";

        LogEntry question = AccessLogHelper.toLogEntry(good);
        question.setId(1);
        when(repository.save(any())).thenReturn(question);
        accessLogService.parseAndSaveAccessLogEntries("src/test/resources/test.access.log");
        verify(
            repository,
            times(10)
        ).save(any());
    }
}