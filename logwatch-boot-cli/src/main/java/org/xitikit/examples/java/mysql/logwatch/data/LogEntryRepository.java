package org.xitikit.examples.java.mysql.logwatch.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Copyright ${year}
 *
 * @author J. Keith Hoopes
 */
@Repository
public interface LogEntryRepository extends CrudRepository<LogEntry, Integer>{

    @Query(nativeQuery = true, value =
        "SELECT\n" +
            "  ipv4,\n" +
            "  total\n" +
            "FROM (\n" +
            "       SELECT\n" +
            "         ipv4,\n" +
            "         count(*) AS total \n" +
            "       FROM log_entry l \n" +
            "       WHERE l.date >= TIMESTAMP(:startDate) AND \n" +
            "             l.date <= TIMESTAMP(:endDate) \n" +
            "       GROUP BY ipv4 \n" +
            "       ORDER BY total DESC \n" +
            "     ) AS ipv4_total \n" +
            "WHERE total > :threshold \n")
    List<AccessLogSearchResult> findAllByCustomQuery(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("threshold") Integer threshold);
}
