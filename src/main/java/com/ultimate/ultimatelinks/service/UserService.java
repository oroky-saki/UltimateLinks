package com.ultimate.ultimatelinks.service;

import com.ultimate.ultimatelinks.dto.UserDto;
import com.ultimate.ultimatelinks.entities.UserEntity;
import com.ultimate.ultimatelinks.exceptions.UserAlreadyExistException;
import com.ultimate.ultimatelinks.exceptions.UserIsNotExistException;
import com.ultimate.ultimatelinks.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public UserEntity createUser(UserDto user) {
            String email = user.getEmail();
            String password = user.getPassword();
            String name = user.getName();

            if (userRepo.findByEmail(email) != null) {
                throw new UserAlreadyExistException("Пользователь уже существует!");
            }

            return userRepo.save(new UserEntity(email, password, name));
    }

    public UserEntity getUserInfo(Long id) {
        Optional<UserEntity> user = userRepo.findById(id);
        user.orElseThrow(() -> new UserIsNotExistException("Пользователь не существует!"));
        return user.get();
    }

}
