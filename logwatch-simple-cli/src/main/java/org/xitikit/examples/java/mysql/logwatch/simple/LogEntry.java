package org.xitikit.examples.java.mysql.logwatch.simple;

import java.time.LocalDateTime;

//@formatter:off
/**
 * Represents a single row in the LOG_ENTRY table.
 * SQL Example:
 * <p>
 * CREATE TABLE IF NOT EXISTS LOG_ENTRY (
 *   ID         INT AUTO_INCREMENT PRIMARY KEY,
 *   DATE       TIMESTAMP    NOT NULL,
 *   IPV4       VARCHAR(15)  NOT NULL,
 *   REQUEST    VARCHAR(20)  NOT NULL,
 *   STATUS     INT          NOT NULL,
 *   USER_AGENT VARCHAR(256) NOT NULL
 * )
 *
 */
//@formatter:on
public class LogEntry{

    /**
     * The id as generated and incremented by the database.
     * This value has no relation to any field in the actual
     * entry in the log files; it is simply here to provide
     * identity.
     */
    private Integer id;

    /**
     * This is the first 'column' in the logs row entry.
     * Maps to a TIMESTAMP type in the database.
     */
    private LocalDateTime date;

    /**
     * The ip address, in IPv4 format, contained in the second column of the logs entry.
     */
    private String ipv4;

    /**
     * This should contain the request information, such as type and HTTP protocol.
     * The values are in the second column of the log entry
     */
    private String request;

    /**
     * The HTTP Status code, with a value from 100 and less than 600.
     */
    private Integer status;

    /**
     * The text which indicates the browser that was used to access the application.
     */
    private String userAgent;

    /**
     * Defaults all values to null.
     * With the exception of the 'id', all fields
     * will need a NonNull value before being persisted.
     */
    public LogEntry(){

    }

    /**
     * An almost-all-arg constructor with WYSIWYG behaviour.
     * Since the id should be generated by the database, it is omitted here.
     */
    public LogEntry(final LocalDateTime date, final String ipv4, final String request, final Integer status, final String userAgent){

        this.date = date;
        this.ipv4 = ipv4;
        this.request = request;
        this.status = status;
        this.userAgent = userAgent;
    }

    public Integer getId(){

        return id;
    }

    public void setId(final Integer id){

        this.id = id;
    }

    public LocalDateTime getDate(){

        return date;
    }

    public void setDate(final LocalDateTime date){

        this.date = date;
    }

    public String getIpv4(){

        return ipv4;
    }

    public void setIpv4(final String ipv4){

        this.ipv4 = ipv4;
    }

    public String getRequest(){

        return request;
    }

    public void setRequest(final String request){

        this.request = request;
    }

    public Integer getStatus(){

        return status;
    }

    public void setStatus(final Integer status){

        this.status = status;
    }

    public String getUserAgent(){

        return userAgent;
    }

    public void setUserAgent(final String userAgent){

        this.userAgent = userAgent;
    }
}