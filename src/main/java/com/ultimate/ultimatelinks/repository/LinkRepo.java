package com.ultimate.ultimatelinks.repository;

import com.ultimate.ultimatelinks.entities.LinkEntity;
import com.ultimate.ultimatelinks.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepo extends CrudRepository<LinkEntity, Long> {

    LinkEntity findByShortLink(String shortLink);

    LinkEntity findByUserIdAndSourceLink(Long userId, String sourceLink);

    List<LinkEntity> findAllByUser(UserEntity user);

}
