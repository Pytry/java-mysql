package test.xitikit.examples.java.mysql.logwatch.simple;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.xitikit.examples.java.mysql.logwatch.simple.*;

import java.nio.file.Path;

import static java.util.Arrays.*;
import static org.mockito.Mockito.*;

/**
 * Copyright ${year}
 *
 * @author J. Keith Hoopes
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LogWatchCliTest{

    @Test
    void emptyCommand(){

        LogEntryRepository logEntryRepository = Mockito.mock(LogEntryRepository.class);
        AccessLogLoader accessLogLoader = Mockito.mock(AccessLogLoader.class);
        BlockedIpv4Repository blockedIpv4Repository = Mockito.mock(BlockedIpv4Repository.class);

        doReturn(blockedIpv4())
            .when(blockedIpv4Repository)
            .save(any());
        doReturn(asList(ipv4SearchResult()))
            .when(logEntryRepository)
            .findAllByDateRangeAndThreshold(any(), any(), any());

        new LogWatchCli(logEntryRepository, accessLogLoader, blockedIpv4Repository).run(args());

        verify(logEntryRepository, times(1)).findAllByDateRangeAndThreshold(any(), any(), any());
        verify(accessLogLoader, times(1)).parseAndSaveAccessLogEntries(any(Path.class));
    }

    private static AccessLogSearchResult ipv4SearchResult(){

        return new AccessLogSearchResult("10.150.10.1", 110);
    }

    private static String[] args(){

        return new String[]{
            "--accessLog=src/test/resources/access.log",
            "--startDate=2017-01-01.13:00:00",
            "--duration=hourly",
            "--threshold=100"};
    }

    private static BlockedIpv4 blockedIpv4(){

        BlockedIpv4 blockedIpv4 = new BlockedIpv4("whatever", "cuz");
        blockedIpv4.setId(1);
        return blockedIpv4;
    }

}