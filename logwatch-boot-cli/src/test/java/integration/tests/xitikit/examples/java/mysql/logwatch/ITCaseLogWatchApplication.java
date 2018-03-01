package integration.tests.xitikit.examples.java.mysql.logwatch;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import ch.vorburger.mariadb4j.springframework.MariaDB4jSpringService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * Copyright ${year}
 *
 * @author J. Keith Hoopes
 */
@Configuration
@SpringBootApplication(
    scanBasePackages = {
        "org.xitikit.examples.java.mysql.logwatch",
        "org.xitikit.examples.java.mysql.logwatch.data",
        "org.xitikit.examples.java.mysql.logwatch.cli"
    })
public class ITCaseLogWatchApplication{

    public static void main(final String[] args){

        System.out.println("****************************************************");
        System.out.println("TestLogWatchApplication::main(" + Arrays.toString(args) + ")");
        System.out.println("****************************************************");

        SpringApplication.run(ITCaseLogWatchApplication.class, args);
    }

    @Configuration
    @Profile("integration-tests")
    public class EmbeddedMariaDbConfig{

        @Bean
        public MariaDB4jSpringService mariaDB4jSpringService(){

            return new MariaDB4jSpringService();
        }

        @Bean
        @Primary
        @FlywayDataSource
        public DataSource dataSource(MariaDB4jSpringService mariaDB4jSpringService,
            @Value("${spring.datasource.name}") String name,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.driver-class-name}") String driverClassName) throws ManagedProcessException{

            mariaDB4jSpringService.getDB().createDB(name);
            DBConfigurationBuilder config = mariaDB4jSpringService.getConfiguration();

            return DataSourceBuilder.create()
                .username(username)
                .password(password)
                .url(config.getURL(name))
                .driverClassName(driverClassName)
                .build();
        }

    }
}
