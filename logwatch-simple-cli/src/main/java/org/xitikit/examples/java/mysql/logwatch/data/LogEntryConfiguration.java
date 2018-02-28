package org.xitikit.examples.java.mysql.logwatch.data;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableJpaRepositories(
    basePackages = "org.xitikit.examples.java.mysql.logwatch.data",
    repositoryBaseClass = CrudRepository.class
)
public class LogEntryConfiguration{

}
