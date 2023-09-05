package com.ultimate.ultimatelinks.service;

import com.ultimate.ultimatelinks.dto.UserDto;
import com.ultimate.ultimatelinks.entities.UserEntity;
import com.ultimate.ultimatelinks.exceptions.userEx.UserAlreadyExistException;
import com.ultimate.ultimatelinks.exceptions.userEx.UserIsNotExistException;
import com.ultimate.ultimatelinks.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
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

    @Transactional(readOnly = true)
    public UserEntity getUserInfo(Long id) {
        Optional<UserEntity> user = userRepo.findById(id);
        user.orElseThrow(() -> new UserIsNotExistException("Пользователь не существует!"));
        return user.get();
    }

    public String deleteUser(Long userID) {
        Optional<UserEntity> user = userRepo.findById(userID);
        user.orElseThrow(() -> new UserIsNotExistException("Пользователь не существует!"));
        userRepo.deleteById(userID);
        return "User Deleted";
    }
}
