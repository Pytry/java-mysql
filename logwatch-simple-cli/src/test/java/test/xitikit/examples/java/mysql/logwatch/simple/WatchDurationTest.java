package test.xitikit.examples.java.mysql.logwatch.simple;

import org.junit.jupiter.api.Test;
import org.xitikit.examples.java.mysql.logwatch.simple.WatchDuration;

import static org.junit.jupiter.api.Assertions.*;

class WatchDurationTest{

    @Test
    void enforceLowerCaseSemantics(){
        // Using a lower case name for an enum is atypical, but in this case very useful for validation input.
        // This test makes sure that someone does not "accidentally" change the case.
        assertEquals("hourly", WatchDuration.hourly.toString());
        assertEquals("daily", WatchDuration.daily.toString());
    }
}