package com.ultimate.ultimatelinks.service;

import com.ultimate.ultimatelinks.dto.ReturnedUserDto;
import com.ultimate.ultimatelinks.dto.UserDto;
import com.ultimate.ultimatelinks.entities.UserEntity;
import com.ultimate.ultimatelinks.exceptions.userEx.UserAlreadyExistException;
import com.ultimate.ultimatelinks.exceptions.userEx.UserIsNotExistException;
import com.ultimate.ultimatelinks.mapper.UserMapper;
import com.ultimate.ultimatelinks.repository.RoleRepo;
import com.ultimate.ultimatelinks.repository.UserRepo;
import com.ultimate.ultimatelinks.entities.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final UserMapper userMapper;


    public ReturnedUserDto createUser(@Valid UserDto user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String email = user.getEmail();
        String password = passwordEncoder.encode(user.getPassword());
        String name = user.getName();
        UserEntity newUser = new UserEntity(email,password,name);

        // Присвоение роли
        Collection<Role> roles = List.of(roleRepo.findById(1L).get());
        newUser.setRoles(roles);

        if (userRepo.findByEmail(email) != null) {
            throw new UserAlreadyExistException(String.format("User with email '%s' Already Exists!", email));
        }
        return userMapper.toDto(userRepo.save(newUser));
    }


    @Transactional(readOnly = true)
    public ReturnedUserDto getUserInfo(@Min(1) Long id) {
        UserEntity user = userRepo.findById(id)
                .orElseThrow(() -> new UserIsNotExistException(String.format("User with ID '%d' is Not Exists!", id)));
        return userMapper.toDto(user);
    }

    public void deleteUser(@Min(1) Long userID) {
        userRepo.deleteById(userRepo.findById(userID)
                .orElseThrow(() -> new UserIsNotExistException(String.format("User with ID '%d' is Not Exists!", userID)))
                .getId());
    }
}
