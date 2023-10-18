package com.ultimate.ultimatelinks;

import com.ultimate.ultimatelinks.controller.RedirectController;
import com.ultimate.ultimatelinks.exceptions.linkEx.LinkIsNotExistException;
import com.ultimate.ultimatelinks.exceptions.userEx.UserAlreadyExistException;
import com.ultimate.ultimatelinks.service.LinkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RedirectControllerTest {

    @Mock
    LinkService linkService;

    @InjectMocks
    RedirectController redirectController;

    @Test
    void redirect_ShouldReturnRedirectView() {
        // given
        String shortLink = "j8h2y65";
        String sourceLink = "https://roadmap.sh/java";
        RedirectView view = new RedirectView(sourceLink);
        doReturn(sourceLink).when(this.linkService).findShortLink(shortLink);

        // when
        var responseEntity = this.redirectController.redirect(shortLink);

        // then
        assertTrue(responseEntity.isRedirectView());
        assertEquals(responseEntity.getUrl(), sourceLink);
        assertNotNull(responseEntity);
    }

    @Test
    void redirect_throwsLinkIsNotExistsException() {
        // given
        String shortLink = "j8h2y65";
        when(redirectController.redirect(shortLink)).thenThrow(LinkIsNotExistException.class);

        // then
        assertThrows((LinkIsNotExistException.class), () -> redirectController.redirect(shortLink));
    }

}
