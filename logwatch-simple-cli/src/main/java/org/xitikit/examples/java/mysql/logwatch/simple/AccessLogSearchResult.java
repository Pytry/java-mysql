package org.xitikit.examples.java.mysql.logwatch.simple;

public class AccessLogSearchResult{

    private String ipv4;

    private Integer total;

    public AccessLogSearchResult(){

    }

    public AccessLogSearchResult(final String ipv4, final Integer total){

        this.ipv4 = ipv4;
        this.total = total;
    }

    public String getIpv4(){

        return ipv4;
    }

    public void setIpv4(final String ipv4){

        this.ipv4 = ipv4;
    }

    public Integer getTotal(){

        return total;
    }

    public void setTotal(final Integer total){

        this.total = total;
    }
}
