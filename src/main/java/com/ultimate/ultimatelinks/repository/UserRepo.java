package com.ultimate.ultimatelinks.repository;

import com.ultimate.ultimatelinks.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
