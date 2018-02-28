package org.xitikit.examples.java.mysql.logwatch.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Copyright ${year}
 *
 * @author J. Keith Hoopes
 */
@Repository
public interface BlockedIpv4Repository extends CrudRepository<BlockedIpv4, Integer>{

}
