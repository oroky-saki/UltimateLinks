package com.ultimate.ultimatelinks.service;

import com.ultimate.ultimatelinks.dto.UserDto;
import com.ultimate.ultimatelinks.entities.UserEntity;
import com.ultimate.ultimatelinks.exceptions.userEx.UserAlreadyExistException;
import com.ultimate.ultimatelinks.exceptions.userEx.UserIsNotExistException;
import com.ultimate.ultimatelinks.repository.RoleRepo;
import com.ultimate.ultimatelinks.repository.UserRepo;
import com.ultimate.ultimatelinks.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;


    public UserEntity createUser(UserDto user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String email = user.getEmail();
        String password = passwordEncoder.encode(user.getPassword());
        String name = user.getName();
        UserEntity newUser = new UserEntity(email,password,name);

        // Присвоение роли
        Collection<Role> roles = List.of(roleRepo.findById(1L).get());
        newUser.setRoles(roles);

        if (userRepo.findByEmail(email) != null) {
            throw new UserAlreadyExistException("Пользователь уже существует!");
        }

        return userRepo.save(newUser);
    }


    @Transactional(readOnly = true)
    public UserEntity getUserInfo(Long id) {
        Optional<UserEntity> user = userRepo.findById(id);
        user.orElseThrow(() -> new UserIsNotExistException("Пользователь не существует!"));
        return user.get();
    }

    public void deleteUser(Long userID) {
        Optional<UserEntity> user = userRepo.findById(userID);
        user.orElseThrow(() -> new UserIsNotExistException("Пользователь не существует!"));
        userRepo.deleteById(userID);
    }
}
