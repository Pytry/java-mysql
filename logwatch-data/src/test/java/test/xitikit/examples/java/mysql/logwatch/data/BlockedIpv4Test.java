package test.xitikit.examples.java.mysql.logwatch.data;

import org.junit.jupiter.api.Test;
import org.xitikit.examples.java.mysql.logwatch.data.BlockedIpv4;

import static org.junit.jupiter.api.Assertions.*;

class BlockedIpv4Test{

    @Test
    void blockedIpv4WYSIWYG(){

        BlockedIpv4 blockedIpv4;

        blockedIpv4 = new BlockedIpv4();
        assertNull(blockedIpv4.getId());
        assertNull(blockedIpv4.getIpv4());
        assertNull(blockedIpv4.getReason());

        String reallyLongReason = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
        blockedIpv4 = new BlockedIpv4(" \t\n\r192.168.234.82 \t\n\r", reallyLongReason);
        assertNull(blockedIpv4.getId());
        assertEquals(" \t\n\r192.168.234.82 \t\n\r", blockedIpv4.getIpv4());
        assertEquals(reallyLongReason, blockedIpv4.getReason());

        Integer id = -10_000;//This is not a valid value.
        blockedIpv4.setId(id);
        blockedIpv4.setIpv4(null);
        blockedIpv4.setReason(null);

        assertEquals(id, blockedIpv4.getId());
        assertNull(blockedIpv4.getIpv4());
        assertNull(blockedIpv4.getReason());
    }
}