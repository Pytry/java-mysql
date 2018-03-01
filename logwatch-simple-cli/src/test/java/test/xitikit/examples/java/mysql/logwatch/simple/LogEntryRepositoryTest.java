package test.xitikit.examples.java.mysql.logwatch.simple;

import org.junit.jupiter.api.Test;
import org.xitikit.examples.java.mysql.logwatch.simple.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogEntryRepositoryTest{

    @Test
    void saveTest(){

        LogEntryRepository repository = new LogWatchConfiguration().getLogEntryRepository();
        final String good = "" +
            "2017-01-01 00:00:11.763|" +
            "192.168.234.82|" +
            "\"GET / HTTP/1.1\"|" +
            "200|" +
            "\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";

        LogEntry question = AccessLogParser.stringToLogEntry(good);
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

        List<AccessLogSearchResult> results = repository.findAllByDateRangeAndThreshold(
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
        AccessLogSearchResult first = results.get(0);
        assertNotNull(first);
        assertNotNull(first.getIpv4());
        assertTrue(!"".equals(first.getIpv4().trim()));
        assertNotNull(first.getTotal());
        assertTrue(first.getTotal() > 0);
    }
}