package test.xitikit.examples.java.mysql.logwatch.cli;

import org.junit.jupiter.api.Test;
import org.xitikit.examples.java.mysql.logwatch.cli.TerminalCommands;
import org.xitikit.examples.java.mysql.logwatch.cli.WatchDuration;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TerminalCommandsTest{

    @Test
    void getStartDate(){

        TerminalCommands args = new TerminalCommands();

        assertNull(args.getStartDate());
        assertNull(args.getDuration());
        assertNull(args.getThreshold());
        assertNull(args.getAccessLog());
        assertNull(args.getIpv4());

        LocalDateTime now = LocalDateTime.now();
        args = new TerminalCommands(now, WatchDuration.hourly, 100, "access.log", "192.168.0.1");

        assertEquals(now, args.getStartDate());
        assertEquals(WatchDuration.hourly, args.getDuration());
        assertEquals(new Integer(100), args.getThreshold());
        assertEquals("access.log", args.getAccessLog());
        assertEquals("192.168.0.1", args.getIpv4());

        now = LocalDateTime.now();
        args.setStartDate(now);
        args.setDuration(WatchDuration.daily);
        args.setThreshold(250);
        args.setAccessLog("classpath: access.log");
        args.setIpv4("10.0.0.1");

        assertEquals(now, args.getStartDate());
        assertEquals(WatchDuration.daily, args.getDuration());
        assertEquals(new Integer(250), args.getThreshold());
        assertEquals("classpath: access.log", args.getAccessLog());
        assertEquals("10.0.0.1", args.getIpv4());
    }
}