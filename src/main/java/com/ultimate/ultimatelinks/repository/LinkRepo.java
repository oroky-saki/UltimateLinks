package com.ultimate.ultimatelinks.repository;

import com.ultimate.ultimatelinks.entities.LinkEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepo extends CrudRepository<LinkEntity, Long> {

}
