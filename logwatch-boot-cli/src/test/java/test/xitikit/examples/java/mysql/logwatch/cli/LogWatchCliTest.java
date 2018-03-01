package test.xitikit.examples.java.mysql.logwatch.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.xitikit.examples.java.mysql.logwatch.cli.LogWatchCli;
import org.xitikit.examples.java.mysql.logwatch.data.*;
import org.xitikit.examples.java.mysql.logwatch.files.AccessLogService;
import org.xitikit.examples.java.mysql.logwatch.files.AccessLogServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

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

        LogEntrySearchService logEntrySearchService = Mockito.mock(LogEntrySearchServiceImpl.class);
        AccessLogService accessLogService = Mockito.mock(AccessLogServiceImpl.class);
        BlockedIpv4Repository blockedIpv4Repository = Mockito.mock(BlockedIpv4Repository.class);
        LogWatchCli logWatchCli = new LogWatchCli(logEntrySearchService, accessLogService, blockedIpv4Repository);

        String[] args = args();
        Ipv4SearchQuery query = ipv4SearchQuery();
        List<LogEntrySearchResult> toBeReturned = asList(ipv4SearchResult());

        doReturn(toBeReturned)
            .when(logEntrySearchService)
            .findAddressesThatExceedThreshold(query);
        logWatchCli.run(args);

        verify(logEntrySearchService, times(1))
            .findAddressesThatExceedThreshold(any());
        verify(accessLogService, times(1))
            .parseAndSaveAccessLogEntries(any(String.class));
    }

    private static Ipv4SearchQuery ipv4SearchQuery(){

        Ipv4SearchQuery query = new Ipv4SearchQuery();
        query.setStartDate(
            LocalDateTime.from(LogWatchCli.formatter.parse("2017-01-01.13:00:00"))
        );
        query.setEndDate(query.getStartDate().plusHours(1));
        query.setThreshold(100);

        return query;
    }

    private static LogEntrySearchResult ipv4SearchResult(){

        return new LogEntrySearchResult(){

            private String ipv4 = "10.150.10.1";

            private Integer total = 110;

            @Override
            public String getIpv4(){

                return ipv4;
            }

            @Override
            public Integer getTotal(){

                return total;
            }
        };
    }

    private static String[] args(){

        return new String[]{
            "--accessLog=src/test/resources/access.log",
            "--startDate=2017-01-01.13:00:00",
            "--duration=hourly",
            "--threshold=100"};
    }

}