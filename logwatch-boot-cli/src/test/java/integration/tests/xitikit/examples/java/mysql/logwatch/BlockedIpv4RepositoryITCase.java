package integration.tests.xitikit.examples.java.mysql.logwatch;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.xitikit.examples.java.mysql.logwatch.LogWatchApplication;
import org.xitikit.examples.java.mysql.logwatch.data.BlockedIpv4;
import org.xitikit.examples.java.mysql.logwatch.data.BlockedIpv4Repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@ExtendWith(SpringExtension.class)
@TestInstance(PER_CLASS)
@SpringBootTest(
    webEnvironment = NONE,
    classes = LogWatchApplication.class)
class BlockedIpv4RepositoryITCase{

    @Autowired
    @Qualifier("blockedIpv4Repository")
    private BlockedIpv4Repository repository;

    @AfterAll
    void clean(){

        repository.deleteAll();
    }

    @Test
    void load(){

    }

    @Test
    void save(){

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