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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {

    @Value("${app-domain: http://localhost:8080/}")
    private String domain;

    private final LinkRepo linkRepo;
    private final UserRepo userRepo;
    private final LinkUtil linkUtil;
    private final LinkMapper linkMapper;
    private final LinkClickRepo clickRepo;
    private final LinkClickService clickService;


    public LinkDtoToUser createLink(LinkDtoFromUser linkDtoFromUser) {
        String sourceLink = linkDtoFromUser.getSourceLink();
        Long userID = linkDtoFromUser.getUserID();

        LinkEntity oldLink = null;
        // Если пользователь уже создал такую ссылку - проверка по полной ссылке И ID пользователя
        oldLink = linkRepo.findByUserIdAndSourceLink(userID, sourceLink);
        if (oldLink!=null) {
            throw new LinkAlreadyExistException("Link Already Exists!");
        }

        UserEntity user = userRepo.findById(userID)
                .orElseThrow(() -> new UserIsNotExistException("User is Not Exists!"));

        // Получение успешно сохраненной ссылки для возврата клиенту
        LinkEntity newLink = saveLink(sourceLink, user);

        newLink.setShortLink(domain + newLink.getShortLink());
        return linkMapper.toDto(newLink);
    }

    // Сохранение ссылки и исключение коллизии при сгенерированной код-строке короткой ссылки
    @Retryable(maxAttempts = 5)
    private LinkEntity saveLink(String sourceLink, UserEntity user) {
        String shortLink = linkUtil.hashLink(sourceLink);
        LinkEntity newLink = new LinkEntity(sourceLink, shortLink, user);
        return linkRepo.save(newLink);
    }

    // Поиск полной ссылки по коду короткой
    @Transactional
    public String findShortLink(String shortLink) {

        LinkEntity link = linkRepo.findByShortLink(shortLink);

        if (link == null) {
            throw new LinkIsNotExistException("Link is NOT exists!");
        }

        clickService.createClick(link);
        return link.getSourceLink();
    }

    // Удаление ссылки
    @Transactional
    public void deleteLink(Long linkID) {
        linkRepo.deleteById(
                linkRepo.findById(linkID)
                .orElseThrow(() -> new LinkIsNotExistException("Link is Not Exists!"))
                .getId());
    }

    // Получение информации о ссылке по ID
    @Transactional(readOnly = true)
    public LinkClicksDto getLinkInfo(Long linkID) {
        LinkEntity link = linkRepo.findById(linkID)
                .orElseThrow(() -> new LinkIsNotExistException("Link is Not Exists!"));

        return new LinkClicksDto(
                link.getId(),
                link.getSourceLink(),
                domain + link.getShortLink(),
                clickRepo.countByLinkId(link.getId())
        );
    }

    @Transactional(readOnly = true)
    public List<LinkDtoToUser> getAllLinks(Long userID) {
        UserEntity user = userRepo.findById(userID)
                .orElseThrow(() -> new UserIsNotExistException("User is Not Exists!"));
        return linkMapper.toDtoList(linkRepo.findAllByUser(user));
    }

}
