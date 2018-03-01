package integration.tests.xitikit.examples.java.mysql.logwatch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.xitikit.examples.java.mysql.logwatch.LogWatchApplication;
import org.xitikit.examples.java.mysql.logwatch.data.LogEntry;
import org.xitikit.examples.java.mysql.logwatch.data.LogEntryRepository;
import org.xitikit.examples.java.mysql.logwatch.data.LogEntrySearchResult;
import org.xitikit.examples.java.mysql.logwatch.files.AccessLogHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@ExtendWith(SpringExtension.class)
@TestInstance(PER_CLASS)
@SpringBootTest(
    webEnvironment = NONE,
    classes = LogWatchApplication.class)
class LogEntryRepositoryITCase{

    @Autowired
    private LogEntryRepository repository;

    @Test
    void load(){

    }

    @Test
    void saveTest(){

        final String good = "" +
            "2017-01-01 00:00:11.763|" +
            "192.168.234.82|" +
            "\"GET / HTTP/1.1\"|" +
            "200|" +
            "\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";

        LogEntry question = AccessLogHelper.toLogEntry(good);
        LogEntry answer = repository.save(question);
        assertNotNull(answer);
        assertNotNull(answer.getId());
        assertEquals(
            "2017-01-01 00:00:11.763",
            DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                .format(answer.getDate()));
        assertEquals("192.168.234.82", answer.getIpv4());
        assertEquals("\"GET / HTTP/1.1\"", answer.getRequest());
        assertEquals(new Integer(200), answer.getStatus());
        assertEquals(
            "\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"",
            answer.getUserAgent());

        //------------------------------------
        // Test findAllByDateRangeAndThreshold
        //------------------------------------
        question.setId(null);
        repository.save(question);
        question.setId(null);
        repository.save(question);

        List<LogEntrySearchResult> results = repository.findAllByDateRangeAndThreshold(
            LocalDateTime
                .from(
                    DateTimeFormatter
                        .ofPattern("yyyy-MM-dd.HH:mm:ss")
                        .parse("2000-01-01.00:00:00")),
            LocalDateTime
                .from(
                    DateTimeFormatter
                        .ofPattern("yyyy-MM-dd.HH:mm:ss")
                        .parse("2018-01-02.00:00:00")),
            1);
        assertNotNull(results);
        assertTrue(results.size() > 0);
        LogEntrySearchResult first = results.get(0);
        assertNotNull(first);
        assertNotNull(first.getIpv4());
        assertTrue(!"".equals(first.getIpv4().trim()));
        assertNotNull(first.getTotal());
        assertTrue(first.getTotal() > 0);
    }
}