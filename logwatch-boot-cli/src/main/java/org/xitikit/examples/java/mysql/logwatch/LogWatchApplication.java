package org.xitikit.examples.java.mysql.logwatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

/**
 * Copyright ${year}
 *
 * @author J. Keith Hoopes
 */
@SpringBootApplication
public class LogWatchApplication{

    public static void main(final String[] args){

        System.out.println("****************************************************");
        System.out.println("LogWatchApplication::main(" + Arrays.toString(args) + ")");
        System.out.println("****************************************************");

        SpringApplication.run(LogWatchApplication.class, args);
    }
}
