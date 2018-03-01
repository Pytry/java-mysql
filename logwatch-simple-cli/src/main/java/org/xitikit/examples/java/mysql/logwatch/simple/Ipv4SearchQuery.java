package org.xitikit.examples.java.mysql.logwatch.simple;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;

public class Ipv4SearchQuery{

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer threshold;

    private Path accessLog;

    public Ipv4SearchQuery(){

    }

    public Ipv4SearchQuery(final LocalDateTime startDate, final LocalDateTime endDate, final Integer threshold, final Path accessLog){

        this.startDate = startDate;
        this.endDate = endDate;
        this.threshold = threshold;
        this.accessLog = accessLog;
    }

    @Override
    public boolean equals(final Object o){

        if(this == o){
            return true;
        }
        if(!(o instanceof Ipv4SearchQuery)){
            return false;
        }
        Ipv4SearchQuery that = (Ipv4SearchQuery) o;
        return Objects.equals(getStartDate(), that.getStartDate()) &&
            Objects.equals(getEndDate(), that.getEndDate()) &&
            Objects.equals(getThreshold(), that.getThreshold()) &&
            Objects.equals(getAccessLog(), that.getAccessLog());
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

    public Path getAccessLog(){

        return accessLog;
    }

    public void setAccessLog(final Path accessLog){

        this.accessLog = accessLog;
    }

    @Override
    public int hashCode(){

        return Objects.hash(getStartDate(), getEndDate(), getThreshold(), getAccessLog());
    }

    @Override
    public String toString(){

        return "Ipv4SearchQuery{" +
            "startDate=" + startDate +
            ", endDate=" + endDate +
            ", threshold=" + threshold +
            ", accessLog=" + accessLog +
            '}';
    }
}
