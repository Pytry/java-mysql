package org.xitikit.examples.java.mysql.logwatch.data;

import java.time.LocalDateTime;

public class Ipv4SearchQuery{

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer threshold;

    public Ipv4SearchQuery(){

    }

    public Ipv4SearchQuery(final LocalDateTime startDate, final LocalDateTime endDate, final Integer threshold){

        this.startDate = startDate;
        this.endDate = endDate;
        this.threshold = threshold;
    }

    public LocalDateTime getStartDate(){

        return startDate;
    }

    public void setStartDate(final LocalDateTime startDate){

        this.startDate = startDate;
    }

    public LocalDateTime getEndDate(){

        return endDate;
    }

    public void setEndDate(final LocalDateTime endDate){

        this.endDate = endDate;
    }

    public Integer getThreshold(){

        return threshold;
    }

    public void setThreshold(final Integer threshold){

        this.threshold = threshold;
    }
}
