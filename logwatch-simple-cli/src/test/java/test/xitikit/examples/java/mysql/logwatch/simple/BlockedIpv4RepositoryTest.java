package test.xitikit.examples.java.mysql.logwatch.simple;

import org.junit.jupiter.api.Test;
import org.xitikit.examples.java.mysql.logwatch.simple.BlockedIpv4;
import org.xitikit.examples.java.mysql.logwatch.simple.BlockedIpv4Repository;
import org.xitikit.examples.java.mysql.logwatch.simple.LogWatchConfiguration;

import static org.junit.jupiter.api.Assertions.*;

class BlockedIpv4RepositoryTest{

    @Test
    void save(){

        BlockedIpv4Repository repository = new LogWatchConfiguration().getBlockedIpv4Repository();

        BlockedIpv4 question = new BlockedIpv4("192.168.0.1", "Client Address has made too many request.");
        BlockedIpv4 answer = repository.save(question);
        assertNotNull(answer);
        assertNotNull(answer.getId());
        assertEquals(
            "192.168.0.1",
            answer.getIpv4());
        assertEquals(
            "Client Address has made too many request.",
            answer.getReason());
    }
}