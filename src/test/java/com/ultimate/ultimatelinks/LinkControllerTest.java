package com.ultimate.ultimatelinks;

import com.ultimate.ultimatelinks.controller.LinkController;
import com.ultimate.ultimatelinks.dto.ClickStatsDto;
import com.ultimate.ultimatelinks.dto.LinkClicksDto;
import com.ultimate.ultimatelinks.dto.LinkDtoFromUser;
import com.ultimate.ultimatelinks.dto.LinkDtoToUser;
import com.ultimate.ultimatelinks.exceptions.linkEx.LinkAlreadyExistException;
import com.ultimate.ultimatelinks.exceptions.linkEx.LinkIsNotExistException;
import com.ultimate.ultimatelinks.exceptions.userEx.UserIsNotExistException;
import com.ultimate.ultimatelinks.service.LinkClickService;
import com.ultimate.ultimatelinks.service.LinkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LinkControllerTest {

    @InjectMocks
    LinkController linkController;

    @Mock
    LinkService linkService;

    @Mock
    LinkClickService clickService;

    @Test
    void createShortLink_ShouldReturnLinkDtoToUser() {
        // given
        LinkDtoFromUser link = new LinkDtoFromUser();
        link.setUserID(1L);
        link.setSourceLink("https://roadmap.sh/java");

        LinkDtoToUser shortLink = new LinkDtoToUser(1L, "https://roadmap.sh/java", "4iW7EDW", "roadmap.sh");
        doReturn(shortLink).when(this.linkService).createLink(link);

        // when
        var responseEntity = this.linkController.createShortLink(link);

        // then
        String s1 = responseEntity.getBody().getSourceLink();
        String s2 = link.getSourceLink();
        assertEquals(s1,s2);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertEquals(responseEntity.getBody(), shortLink);
        assertNotNull(responseEntity);
    }

    @Test
    void deleteLink_ShouldReturnEmptyBody() {
        // when
        var responseEntity = this.linkController.deleteLink(1L);

        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNull(responseEntity.getBody());
    }

    @Test
    void deleteLink_throwsLinkIsNotExistException() {
        // given
        when(linkController.deleteLink(1L)).thenThrow(LinkIsNotExistException.class);

        // then
        assertThrows((LinkIsNotExistException.class), () -> linkController.deleteLink(1L));
    }

    @Test
    void createShortLink_throwsLinkAlreadyExistException() {
        // given
        LinkDtoFromUser link = new LinkDtoFromUser();
        link.setUserID(1L);
        link.setSourceLink("https://roadmap.sh/java");
        when(linkController.createShortLink(link)).thenThrow(LinkAlreadyExistException.class);

        // then
        assertThrows(LinkAlreadyExistException.class, () -> linkController.createShortLink(link));
    }

    @Test
    void createShortLink_throwsUserIsNotExistException() {
        // given
        LinkDtoFromUser link = new LinkDtoFromUser();
        link.setUserID(1L);
        link.setSourceLink("https://roadmap.sh/java");
        when(linkController.createShortLink(link)).thenThrow(UserIsNotExistException.class);

        // then
        assertThrows(UserIsNotExistException.class, () -> linkController.createShortLink(link));
    }

    @Test
    void getLinkInfo_ShouldReturnLinkClicksDto() {
        // given
        LinkClicksDto clicksDto = new LinkClicksDto(1L, "https://roadmap.sh/java", "jh78g2b", 20L);
        doReturn(clicksDto).when(this.linkService).getLinkInfo(1L);

        //when
        var responseEntity = this.linkController.getLinkInfo(1L);

        // then
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody(), clicksDto);
    }

    @Test
    void getLinkInfo_throwsLinkIsNotExistException() {
        // given
        when(this.linkController.getLinkInfo(1L)).thenThrow(LinkIsNotExistException.class);

        // then
        assertThrows(LinkIsNotExistException.class, () -> linkController.getLinkInfo(1L));
    }

    @Test
    void getLinkClicksByDays_ShouldReturnListOfClickStatsDto() {
        // given
        List<ClickStatsDto> list = List.of(
                new ClickStatsDto("04-10-2023", 20L),
                new ClickStatsDto("04-10-2023", 19L),
                new ClickStatsDto("04-10-2023", 5L)
        );
        doReturn(list).when(this.clickService).getClicksCountByDays(1L);

        //when
        var responseEntity = this.linkController.getLinkClicksByDays(1L);

        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), list);
    }

    @Test
    void getLinkClicksByHours_ShouldReturnListOfClickStatsDto() {
        // given
        List<ClickStatsDto> list = List.of(
                new ClickStatsDto("11-10-2023 : 21", 3L)
        );
        doReturn(list).when(this.clickService).getClicksCountByHours(1L, "11-10-2023");

        //when
        var responseEntity = this.linkController.getLinkClicksByHours(1L, "11-10-2023");

        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), list);
    }

    @Test
    void getLinkClicksByMinutes_ShouldReturnListOfClickStatsDto() {
        // given
        List<ClickStatsDto> list = List.of(
                new ClickStatsDto("11-10-2023 : 21-10", 3L)
        );
        doReturn(list).when(this.clickService).getClicksCountByMinutes(1L, "11-10-2023");

        //when
        var responseEntity = this.linkController.getLinkClicksByMinutes(1L, "11-10-2023");

        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), list);
    }

    @Test
    void getPopularLinks_ShouldReturnListOfClickStatsDto() {
        // given
        List<ClickStatsDto> list = List.of(
                new ClickStatsDto("roadmap.sh", 3L)
        );
        doReturn(list).when(this.clickService).getPopularLinks();

        //when
        var responseEntity = this.linkController.getPopularLinks();

        // then
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), list);
    }


}
