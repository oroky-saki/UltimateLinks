package com.ultimate.ultimatelinks.repository;

import com.ultimate.ultimatelinks.entities.LinkClickEntity;
import com.ultimate.ultimatelinks.entities.LinkEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LinkClickRepo extends CrudRepository<LinkClickEntity, Long> {

    List<LinkClickEntity> findAllByLink(LinkEntity link);

    Long countByLinkId(Long linkID);
}
