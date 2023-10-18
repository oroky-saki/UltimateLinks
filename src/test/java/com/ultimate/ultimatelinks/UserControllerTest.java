package com.ultimate.ultimatelinks;

import com.ultimate.ultimatelinks.controller.UserController;
import com.ultimate.ultimatelinks.dto.LinkDtoToUser;
import com.ultimate.ultimatelinks.dto.ReturnedUserDto;
import com.ultimate.ultimatelinks.exceptions.userEx.UserIsNotExistException;
import com.ultimate.ultimatelinks.service.LinkService;
import com.ultimate.ultimatelinks.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    LinkService linkService;

    @InjectMocks
    UserController userController;

    @Test
    void getAuthUser_ShouldReturnReturnedUserDto() {
        // given
        ReturnedUserDto dto = new ReturnedUserDto();
        dto.setId(1L);
        dto.setEmail("mail@mail.ru");
        dto.setName("1");

        doReturn(dto).when(this.userService).getAuthUserInfo();

        // when
        var responseEntity = this.userController.getAuthUser();

        // then
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody(), dto);
    }

    @Test
    void deleteUser_ShouldReturnEmptyBody() {
        // when
        var responseEntity = this.userController.deleteUser(1L);

        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNull(responseEntity.getBody());
    }

    @Test
    void deleteUser_throwsUserIsNotExistException() {
        // given
        when(this.userController.deleteUser(1L)).thenThrow(UserIsNotExistException.class);

        // then
        assertThrows((UserIsNotExistException.class), () -> userController.deleteUser(1L));
    }

    @Test
    void getUsersLink_ShouldReturnListOfLinkDtoToUser() {
        // given
        List<LinkDtoToUser> list = List.of(
                new LinkDtoToUser(1L, "https://roadmap.sh/java", "4iW7EDW", "roadmap.sh"),
                new LinkDtoToUser(1L, "https://translate.yandex.ru/", "j8h6ggw", "translate.yandex.ru"),
                new LinkDtoToUser(1L, "https://stepik.org/", "kijuh75", "stepik.org")
        );
        doReturn(list).when(this.linkService).getAllLinks(1L);

        // when
        var responseEntity = this.userController.getUsersLink(1L);

        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), list);

    }


}
