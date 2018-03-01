package org.xitikit.examples.java.mysql.logwatch.simple;

import java.util.Arrays;

/**
 * Copyright ${year}
 *
 * @author J. Keith Hoopes
 */
public class LogWatchApplication{

    public static void main(final String[] args){

        System.out.println("****************************************************");
        System.out.println("LogWatchApplication::main(" + Arrays.toString(args) + ")");
        System.out.println("****************************************************");

        LogWatchConfiguration configuration = new LogWatchConfiguration();
        configuration.getLogWatchCli().run(args);
    }
}
