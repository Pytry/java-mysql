package org.xitikit.examples.java.mysql.logwatch.cli;

import java.time.LocalDateTime;

public class TerminalCommands{

    /**
     * For holding the "--startDate=" argument.
     * The value must have the format "yyyy-MM-dd.HH:mm:ss".
     * <p>
     * To perform a search, startDate, duration, and threshold
     * need to all be passed as arguments, or the search will
     * be skipped.
     */
    private LocalDateTime startDate;

    /**
     * Represents the "--duration=" argument.
     * Possible values are "hourly" and "daily" exactly.
     * <p>
     * To perform a search, startDate, duration, and threshold
     * need to all be passed as arguments, or the search will
     * be skipped.
     */
    private WatchDuration duration;

    /**
     * Represents the "--threshold" argument.
     * Values must be an integer greater than 0.
     * <p>
     * To perform a search, startDate, duration, and threshold
     * need to all be passed as arguments, or the search will
     * be skipped.
     */
    private Integer threshold;

    /**
     * File system path to the "access.log" file that
     * will be imported into the database.
     * <p>
     * This can be used on it's own, or conjunction with other arguments.
     * <p>
     * If other commands/arguments are given, this will always
     * be the first one processed.
     */
    private String accessLog;

    /**
     * Represents the "--ipv4=" argument.
     * <p>
     * The value must conform to the standard format
     * for an IPv4 address. Host names are not supported.
     * <p>
     * This can be passed along with other arguments, and will
     * always be the last one to be processed.
     * <p>
     * This argument was not specifically listed in the requirements,
     * but is included so that users may list all log entries
     * made by a specific IP, in ascending order (oldest to newest),
     * as per the following requirement (from instructions):
     * <p>
     * 1. Write MySQL query to find requests made by a given IP.
     * <p>
     * Other search parameters and sorting options are not supported.
     * <p>
     * This argument will have no affect on the results
     * of the search for requests from IPs that have exceeded
     * the threshold.
     *
     *
     * Spring Data is used for this retrieval, however a standalone SQL script
     * called "find_access_logs_by_ip.sql" is included.
     *
     * SELECT ipv4, date, request, status, user_agent
     * FROM log_entry
     * WHERE ipv4 = @ipv4_param
     * ORDER BY date
     */
    private String ipv4;

    public TerminalCommands(){

    }

    public TerminalCommands(final LocalDateTime startDate, final WatchDuration duration, final Integer threshold, final String accessLog, final String ipv4){

        this.startDate = startDate;
        this.duration = duration;
        this.threshold = threshold;
        this.accessLog = accessLog;
        this.ipv4 = ipv4;
    }

    public LocalDateTime getStartDate(){

        return startDate;
    }

    public void setStartDate(final LocalDateTime startDate){

        this.startDate = startDate;
    }

    public WatchDuration getDuration(){

        return duration;
    }

    public void setDuration(final WatchDuration duration){

        this.duration = duration;
    }

    public Integer getThreshold(){

        return threshold;
    }

    public void setThreshold(final Integer threshold){

        this.threshold = threshold;
    }

    public String getAccessLog(){

        return accessLog;
    }

    public void setAccessLog(final String accessLog){

        this.accessLog = accessLog;
    }

    public String getIpv4(){

        return ipv4;
    }

    public void setIpv4(final String ipv4){

        this.ipv4 = ipv4;
    }
}
