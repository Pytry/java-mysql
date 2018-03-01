package org.xitikit.examples.java.mysql.logwatch.simple;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

final class LogEntryValidator{

    static void validateLogEntryInsert(LogEntry logEntry){

        List<String> errors = new ArrayList<>();
        if(logEntry == null){
            errors.add("'logEntry' is required");
        }
        else{
            if(logEntry.getId() != null){
                errors.add("'logEntry.id' must not be provided for insert");
            }
            final String ipv4 = logEntry.getIpv4();
            if(ipv4 == null || "".equals(ipv4.trim())){
                errors.add("'logEntry.ipv4' is required");
            }
            else if(ipv4.length() > 15){
                errors.add("'logEntry.ipv4' is invalid. Found: " + ipv4);
            }
            final String request = logEntry.getRequest();
            if(request == null || "".equals(request.trim())){
                errors.add("'logEntry.request' is required");
            }
            else if(request.length() > 20){
                errors.add("'logEntry.request' exceeded the maximum length of 20 characters. Found: " + request);
            }
            final String userAgent = logEntry.getUserAgent();
            if(userAgent == null || "".equals(userAgent.trim())){
                errors.add("'logEntry.userAgent' is required");
            }
            else if(userAgent.length() > 255){
                errors.add("'logEntry.userAgent' exceeded the maximum length of 20 characters. Found: " + userAgent);
            }
            final LocalDateTime date = logEntry.getDate();
            if(date == null){
                errors.add("'logEntry.date' is required");
            }
            final Integer status = logEntry.getStatus();
            if(status == null){
                errors.add("'logEntry.status' is required");
            }
            else if(status < 100 || status > 599){
                errors.add("'logEntry.status' was not a valid HTTP status. Found: " + status);
            }
        }
        if(errors.size() > 0){
            throw new IllegalArgumentException("Invalid BlockedIpv4 Insert Request: " + errors);
        }
    }

    static void validateLogEntrySearchParameters(final LocalDateTime startDate, final LocalDateTime endDate, final Integer threshold){

        List<String> errors = new ArrayList<>();
        if(startDate == null){
            errors.add("'startDate' is required");
        }
        else if(endDate == null){
            errors.add("'endDate' is required");
        }
        else if(startDate.isAfter(endDate)){
            errors.add("'startDate' cannot be after 'endDate'");
        }
        if(threshold == null){
            errors.add("'threshold' is required");
        }
        else if(threshold <= 0){
            errors.add("'threshold' must be a positive integer");
        }

        if(errors.size() > 0){
            throw new IllegalArgumentException("Invalid search parameters: " + errors);
        }
    }
}
