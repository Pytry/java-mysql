package test.xitikit.examples.java.mysql.logwatch.cli;

import org.junit.jupiter.api.Test;
import org.xitikit.examples.java.mysql.logwatch.cli.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DurationTest{

    @Test
    void enforceLowerCaseSemantics(){
        // Using a lower case name for an enum is atypical, but in this case very useful for validation input.
        // This test makes sure that someone does not "accidentally" change the case.
        assertEquals("hourly", Duration.hourly.toString());
        assertEquals("daily", Duration.daily.toString());
    }
}