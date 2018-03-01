package org.xitikit.examples.java.mysql.logwatch.simple;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;

import static org.xitikit.examples.java.mysql.logwatch.simple.LogEntryValidator.*;

/**
 * Copyright ${year}
 *
 * @author J. Keith Hoopes
 */
public class LogEntryRepository{

    private final DataSource dataSource;

    LogEntryRepository(DataSource dataSource){

        if(dataSource == null){
            throw new IllegalArgumentException("In 'BlockedIpv4Repository::new', the DataSource is required.");
        }
        this.dataSource = dataSource;
    }

    /**
     * Inserts the given LogEntry into the database.
     *
     * @param logEntry Domain to insert
     *
     * @return LogEntry with the id populated.
     */
    public LogEntry save(final LogEntry logEntry){

        validateLogEntryInsert(logEntry);

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn
                .prepareStatement(
                    "INSERT INTO log_entry(date, ipv4, request, status, user_agent) " +
                        "VALUES (?, ?, ?, ?, ?); ",
                    Statement.RETURN_GENERATED_KEYS)){

            ps.setTimestamp(1, Timestamp.from(logEntry.getDate().toInstant(ZoneOffset.UTC)));
            ps.setString(2, logEntry.getIpv4());
            ps.setString(3, logEntry.getRequest());
            ps.setInt(4, logEntry.getStatus());
            ps.setString(5, logEntry.getUserAgent());

            final int affectedRows = ps.executeUpdate();

            if(affectedRows == 0){
                throw new LogEntryDataException("Failed to Insert:: " + logEntry);
            }
            try(ResultSet generatedKeys = ps.getGeneratedKeys()){
                if(generatedKeys.next()){
                    logEntry.setId(generatedKeys.getInt(1));
                }
                else{
                    throw new LogEntryDataException("LogEntry insert failed; the ID was not returned.");
                }
            }
            return logEntry;
        }
        catch(SQLException e){

            throw new LogEntryDataException("Unknown SqlException: " + e.getMessage(), e);
        }
    }

    // @Query(nativeQuery = true, value =

    // )
    public List<AccessLogSearchResult> findAllByDateRangeAndThreshold(LocalDateTime startDate, LocalDateTime endDate, Integer threshold){

        validateLogEntrySearchParameters(startDate, endDate, threshold);

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT ipv4, total \n" +
                    "FROM ( \n" +
                    "       SELECT ipv4, count(*) AS total \n" +
                    "       FROM log_entry l \n" +
                    "       WHERE l.date >= ? AND \n" +
                    "             l.date <= ? \n" +
                    "       GROUP BY ipv4 \n" +
                    "       ORDER BY total DESC \n" +
                    "     ) AS ipv4_total \n" +
                    "WHERE total > ? ",
                Statement.RETURN_GENERATED_KEYS)){

            ps.setTimestamp(1, toTimestamp(startDate));
            ps.setTimestamp(2, toTimestamp(endDate));
            ps.setInt(3, threshold);

            try(ResultSet rs = ps.executeQuery()){
                List<AccessLogSearchResult> accessLogSearchResults = new LinkedList<>();
                while(rs.next()){
                    accessLogSearchResults.add(mapAccessLogSearchResult(rs));
                }
                return accessLogSearchResults;
            }
        }
        catch(SQLException e){

            throw new LogEntryDataException("Unknown SqlException: " + e.getMessage(), e);
        }
    }

    private AccessLogSearchResult mapAccessLogSearchResult(final ResultSet rs) throws SQLException{

        return new AccessLogSearchResult(
            rs.getString("ipv4"),
            rs.getInt("total"));
    }

    private Timestamp toTimestamp(LocalDateTime localDateTime){

        return Timestamp.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

}

class LogEntryDataException extends IllegalStateException{

    LogEntryDataException(final String reason, final Throwable cause){

        super(reason, cause);
    }

    LogEntryDataException(final String message){

        super(message);
    }
}