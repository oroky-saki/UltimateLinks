package com.ultimate.ultimatelinks.service;

import com.ultimate.ultimatelinks.dto.LinkClicksDto;
import com.ultimate.ultimatelinks.dto.LinkDtoFromUser;
import com.ultimate.ultimatelinks.dto.LinkDtoToUser;
import com.ultimate.ultimatelinks.entities.LinkEntity;
import com.ultimate.ultimatelinks.entities.UserEntity;
import com.ultimate.ultimatelinks.exceptions.linkEx.LinkAlreadyExistException;
import com.ultimate.ultimatelinks.exceptions.linkEx.LinkIsNotExistException;
import com.ultimate.ultimatelinks.exceptions.userEx.UserIsNotExistException;
import com.ultimate.ultimatelinks.mapper.LinkMapper;
import com.ultimate.ultimatelinks.repository.LinkClickRepo;
import com.ultimate.ultimatelinks.repository.LinkRepo;
import com.ultimate.ultimatelinks.repository.UserRepo;
import com.ultimate.ultimatelinks.util.LinkUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class LinkService {

    @Value("${app-domain: http://localhost:8080/}")
    private String domain;

    private final LinkRepo linkRepo;
    private final UserRepo userRepo;
    private final LinkUtil linkUtil;
    private final LinkMapper linkMapper;
    private final LinkClickRepo clickRepo;
    private final LinkClickService clickService;


    public LinkDtoToUser createLink(@Valid LinkDtoFromUser linkDtoFromUser) {
        String sourceLink = linkDtoFromUser.getSourceLink();
        Long userID = linkDtoFromUser.getUserID();

        LinkEntity oldLink = null;
        // Если пользователь уже создал такую ссылку - проверка по полной ссылке И ID пользователя
        oldLink = linkRepo.findByUserIdAndSourceLink(userID, sourceLink);
        if (oldLink!=null) {
            throw new LinkAlreadyExistException(String.format("Link '%s' Already Exists!", sourceLink));
        }

        UserEntity user = userRepo.findById(userID)
                .orElseThrow(() -> new UserIsNotExistException(String.format("User with ID '%d' is Not Exists!", userID)));

        // Получение успешно сохраненной ссылки для возврата клиенту
        LinkEntity newLink = saveLink(sourceLink, user);

        newLink.setShortLink(domain + newLink.getShortLink());
        return linkMapper.toDto(newLink);
    }

    // Сохранение ссылки и исключение коллизии при сгенерированной код-строке короткой ссылки
    @Retryable(maxAttempts = 5)
    private LinkEntity saveLink(@NotBlank @Size(min = 8) String sourceLink, UserEntity user) {
        String shortLink = linkUtil.hashLink(sourceLink);
        String site = linkUtil.getSiteFromSource(sourceLink);
        LinkEntity newLink = new LinkEntity(sourceLink, shortLink, user, site);
        return linkRepo.save(newLink);
    }

    // Поиск полной ссылки по коду короткой
    @Transactional
    public String findShortLink(@NotBlank @Size(min = 7, max = 7) String shortLink) {

        LinkEntity link = linkRepo.findByShortLink(shortLink);

        if (link == null) {
            throw new LinkIsNotExistException(String.format("Link '%s' is Not exists!", shortLink));
        }

        clickService.createClick(link);
        return link.getSourceLink();
    }

    // Удаление ссылки
    @Transactional
    public void deleteLink(@Min(1) Long linkID) {
        linkRepo.deleteById(
                linkRepo.findById(linkID)
                .orElseThrow(() -> new LinkIsNotExistException(String.format("Link with ID '%d' is Not Exists!", linkID)))
                .getId());
    }

    // Получение информации о ссылке по ID
    @Transactional(readOnly = true)
    public LinkClicksDto getLinkInfo(@Min(1) Long linkID) {
        LinkEntity link = linkRepo.findById(linkID)
                .orElseThrow(() -> new LinkIsNotExistException(String.format("Link with ID '%d' is Not Exists!", linkID)));

        return new LinkClicksDto(
                link.getId(),
                link.getSourceLink(),
                domain + link.getShortLink(),
                clickRepo.countByLinkId(link.getId())
        );
    }

    @Transactional(readOnly = true)
    public List<LinkDtoToUser> getAllLinks(@Min(1) Long userID) {
        UserEntity user = userRepo.findById(userID)
                .orElseThrow(() -> new UserIsNotExistException(String.format("User with ID '%d' is Not Exists!", userID)));
        return linkMapper.toDtoList(linkRepo.findAllByUser(user));
    }

}
