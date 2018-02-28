package test.xitikit.examples.java.mysql.logwatch.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.xitikit.examples.java.mysql.logwatch.cli.LogWatchCli;
import org.xitikit.examples.java.mysql.logwatch.data.*;

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

    private static Ipv4SearchQuery ipv4SearchQuery(){

        Ipv4SearchQuery query = new Ipv4SearchQuery();
        query.setStartDate(
            LocalDateTime.from(LogWatchCli.formatter.parse("2017-01-01.13:00:00"))
        );
        query.setEndDate(query.getStartDate().plusHours(1));
        query.setThreshold(100);
        return query;
    }

    private static AccessLogSearchResult ipv4SearchResult(){

        return new AccessLogSearchResult(){

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

    @Test
    void emptyCommand(){

        LogEntryService logEntryService = Mockito.mock(LogEntryServiceImpl.class);
        BlockedIpv4Repository blockedIpv4Repository = Mockito.mock(BlockedIpv4Repository.class);
        LogWatchCli logWatchCli = new LogWatchCli(logEntryService, blockedIpv4Repository);

        String[] args = args();
        Ipv4SearchQuery query = ipv4SearchQuery();
        List<AccessLogSearchResult> toBeReturned = asList(ipv4SearchResult());

        doReturn(toBeReturned)
            .when(logEntryService)
            .findAddressesThatExceedThreshold(query);
        logWatchCli.run(args);
    }

}