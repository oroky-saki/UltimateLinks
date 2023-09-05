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
import java.util.Optional;

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
            throw new LinkAlreadyExistException("Link already exist");
        }

        Optional<UserEntity> user = userRepo.findById(userID);
        user.orElseThrow(() -> new UserIsNotExistException("Пользователь не существует!"));

        // Получение успешно сохраненной ссылки для возврата клиенту
        LinkEntity newLink = saveLink(sourceLink, user.get());

        newLink.setShortLink(domain + newLink.getShortLink());
        return linkMapper.toDto(newLink);
    }

    // Сохранение ссылки и исключение коллизии при сгенерированной код-строке короткой ссылки
    @Retryable(maxAttempts = 5)
    private LinkEntity saveLink(String sourceLink, UserEntity user) {
        String shortLink = linkUtil.hashLink(sourceLink);
        LinkEntity newLink = new LinkEntity(sourceLink, shortLink, user);
        linkRepo.save(newLink);
        return newLink;
    }

    // Поиск полной ссылки по коду короткой
    @Transactional
    public String findShortLink(String shortLink) {

        LinkEntity link = linkRepo.findByShortLink(shortLink);

        if (link == null) {
            throw new LinkIsNotExistException("Link is NOT exist!");
        }

        clickService.createClick(link);
        return link.getSourceLink();
    }

    // Удаление ссылки
    @Transactional
    public void deleteLink(Long linkID) {
        Optional<LinkEntity> link = linkRepo.findById(linkID);
        link.orElseThrow(() -> new LinkIsNotExistException("Link is Not Exist"));
        linkRepo.deleteById(linkID);
    }

    // Получение информации о ссылке по ID
    @Transactional(readOnly = true)
    public LinkClicksDto getLinkInfo(Long linkID) {
        Optional<LinkEntity> link = linkRepo.findById(linkID);
        link.orElseThrow(() -> new LinkIsNotExistException("Link is Not Exist"));

        LinkClicksDto linkDto = new LinkClicksDto(
                link.get().getId(),
                link.get().getSourceLink(),
                domain + link.get().getShortLink(),
                clickRepo.countByLinkId(link.get().getId())
        );
        return linkDto;
    }

    @Transactional(readOnly = true)
    public List<LinkDtoToUser> getAllLinks(Long userID) {
        Optional<UserEntity> user = userRepo.findById(userID);
        user.orElseThrow(() -> new UserIsNotExistException("Пользователь не существует!"));
        return linkMapper.toDtoList(linkRepo.findAllByUser(user.get()));
    }

}
