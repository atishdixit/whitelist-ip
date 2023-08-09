package com.ext.whitelist.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HostRepository extends CrudRepository<HostDetails, Long> {
    Optional<HostDetails> findByIpAddress(String ipAddress);
}
