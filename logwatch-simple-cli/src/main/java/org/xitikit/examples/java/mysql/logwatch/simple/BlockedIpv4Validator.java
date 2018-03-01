package org.xitikit.examples.java.mysql.logwatch.simple;

import java.util.ArrayList;
import java.util.List;

public final class BlockedIpv4Validator{

    public static void validateBlockedIpv4Insert(BlockedIpv4 blockedIpv4){

        List<String> errors = new ArrayList<>();
        if(blockedIpv4 == null){
            errors.add("'blockedIpv4' is required");
        }
        else{

            if(blockedIpv4.getId() != null){
                errors.add("'blockedIpv4.id' must not be provided for insert");
            }

            final String ipv4 = blockedIpv4.getIpv4();
            if(ipv4 == null || "".equals(ipv4.trim())){
                errors.add("'blockedIpv4.ipv4' is required");
            }
            else if(ipv4.length() > 15){
                errors.add("'blockedIpv4.ipv4' is invalid. Found: " + ipv4);
            }

            final String reason = blockedIpv4.getReason();
            if(reason == null || "".equals(reason.trim())){
                errors.add("'blockedIpv4.reason' is required");
            }
            else if(reason.length() > 255){
                errors.add("'blockedIpv4.reason' exceeded the maximum length of 255 characters. Found: " + reason);
            }
        }
        if(errors.size() > 0){
            throw new IllegalArgumentException("Invalid BlockedIpv4 Insert Request: " + errors);
        }
    }
}
