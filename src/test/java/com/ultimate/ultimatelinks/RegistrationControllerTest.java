package com.ultimate.ultimatelinks;

import com.ultimate.ultimatelinks.controller.RegistrationController;
import com.ultimate.ultimatelinks.controller.UserController;
import com.ultimate.ultimatelinks.dto.ReturnedUserDto;
import com.ultimate.ultimatelinks.dto.UserDto;
import com.ultimate.ultimatelinks.exceptions.linkEx.LinkIsNotExistException;
import com.ultimate.ultimatelinks.exceptions.userEx.UserAlreadyExistException;
import com.ultimate.ultimatelinks.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {
    @Mock
    UserService userService;

    @InjectMocks
    RegistrationController registrationController;

    @Test
    void createUser_ShouldReturnReturnedUserDto() {
        // given
        UserDto dto = new UserDto();
        dto.setEmail("mail@mail.ru");
        dto.setPassword("100");
        dto.setName("Gennady Gorin");

        ReturnedUserDto newUser = new ReturnedUserDto();
        newUser.setId(1L);
        newUser.setEmail("mail@mail.ru");
        newUser.setName("Gennady Gorin");

        doReturn(newUser).when(this.userService).createUser(dto);

        // when
        var responseEntity = this.registrationController.createUser(dto);

        // then
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertEquals(responseEntity.getBody(), newUser);
        assertNotNull(responseEntity);
    }

    @Test
    void createUser_throwsUserAlreadyExistsException() {
        // given
        UserDto dto = new UserDto();
        dto.setEmail("mail@mail.ru");
        dto.setPassword("100");
        dto.setName("Gennady Gorin");
        when(registrationController.createUser(dto)).thenThrow(UserAlreadyExistException.class);

        // then
        assertThrows((UserAlreadyExistException.class), () -> registrationController.createUser(dto));
    }
}
