package org.xitikit.examples.java.mysql.logwatch.simple;

import javax.sql.DataSource;

/**
 * Copyright ${year}
 *
 * @author J. Keith Hoopes
 */
public class BlockedIpv4Repository{

    private final DataSource dataSource;

    public BlockedIpv4Repository(DataSource dataSource){

        if(dataSource == null){
            throw new IllegalArgumentException("In 'BlockedIpv4Repository::new', the DataSource is required.");
        }
        this.dataSource = dataSource;
    }

    public BlockedIpv4 save(final BlockedIpv4 blockedIpv4){

        BlockedIpv4Validator.validateBlockedIpv4Insert(blockedIpv4);

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn
                .prepareStatement(
                    "INSERT INTO blocked_ipv4(ipv4, reason) VALUES (?,?); ",
                    Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, blockedIpv4.getIpv4());
            ps.setString(2, blockedIpv4.getReason());

            int affectedRows = ps.executeUpdate();

            if(affectedRows == 0){
                throw new BlockedIpv4InsertException("Failed to Insert:: " + blockedIpv4);
            }
            try(ResultSet generatedKeys = ps.getGeneratedKeys()){
                if(generatedKeys.next()){
                    blockedIpv4.setId(generatedKeys.getInt(1));
                }
                else{
                    throw new BlockedIpv4InsertException("Creating user failed, no ID obtained.");
                }
            }
            return blockedIpv4;
        }
        catch(SQLException e){

            throw new BlockedIpv4InsertException("Unknown SqlException: " + e.getMessage(), e);
        }
    }
}

class BlockedIpv4InsertException extends IllegalArgumentException{

    BlockedIpv4InsertException(final String reason, final Throwable cause){

        super(reason, cause);
    }

    BlockedIpv4InsertException(final String message){

        super(message);
    }
}