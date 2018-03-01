package test.xitikit.examples.java.mysql.logwatch.simple;

import org.junit.jupiter.api.Test;
import org.xitikit.examples.java.mysql.logwatch.simple.AccessLogSearchResult;

import static org.junit.jupiter.api.Assertions.*;

class Ipv4SearchResultTest{

    @Test
    void wysiwyg(){

        AccessLogSearchResult a = new AccessLogSearchResult();
        assertNull(a.getIpv4());
        assertNull(a.getTotal());

        a.setIpv4(" 192.168.0.1 ");
        assertEquals(" 192.168.0.1 ", a.getIpv4());
        a.setIpv4(" ");
        assertEquals(" ", a.getIpv4());
        a.setIpv4(null);
        assertNull(a.getIpv4());

        a.setTotal(1);
        assertEquals(Integer.valueOf(1), a.getTotal());
        a.setTotal(-1);
        assertEquals(Integer.valueOf(-1), a.getTotal());
        a.setTotal(null);
        assertNull(a.getTotal());
    }
}