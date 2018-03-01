package org.xitikit.examples.java.mysql.logwatch.simple;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

/**
 * This acts as the container for creating and injecting dependencies.
 */
public class LogWatchConfiguration{

    private final DataSource dataSource;

    private final BlockedIpv4Repository blockedIpv4Repository;

    private final LogEntryRepository logEntryRepository;

    private final AccessLogLoader accessLogLoader;

    private final LogWatchCli logWatchCli;

    public LogWatchConfiguration(){

        DataSourceProperties props = new DataSourceProperties();
        dataSource = buildDataSource(props);
        blockedIpv4Repository = new BlockedIpv4Repository(dataSource);
        logEntryRepository = new LogEntryRepository(dataSource);
        accessLogLoader = new AccessLogLoader(logEntryRepository);
        logWatchCli = new LogWatchCli(logEntryRepository, accessLogLoader, blockedIpv4Repository);
    }

    private static DataSource buildDataSource(final DataSourceProperties props){

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(props.getUrl());
        dataSource.setUsername(props.getUsername());
        dataSource.setPassword(props.getPassword());
        dataSource.setDriverClassName(props.getDriverClassName());
        dataSource.setConnectionInitSqls(props.getConnectionInitSqls());

        return dataSource;
    }

    public DataSource getDataSource(){

        return dataSource;
    }

    public BlockedIpv4Repository getBlockedIpv4Repository(){

        return blockedIpv4Repository;
    }

    public LogEntryRepository getLogEntryRepository(){

        return logEntryRepository;
    }

    public AccessLogLoader getAccessLogLoader(){

        return accessLogLoader;
    }

    public LogWatchCli getLogWatchCli(){

        return logWatchCli;
    }
}
