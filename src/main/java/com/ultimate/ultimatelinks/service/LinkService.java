package com.ultimate.ultimatelinks.service;

import com.ultimate.ultimatelinks.dto.LinkDtoFromUser;
import com.ultimate.ultimatelinks.dto.LinkDtoToUser;
import com.ultimate.ultimatelinks.entities.LinkEntity;
import com.ultimate.ultimatelinks.entities.UserEntity;
import com.ultimate.ultimatelinks.exceptions.linkEx.LinkAlreadyExistException;
import com.ultimate.ultimatelinks.exceptions.linkEx.LinkIsNotExistException;
import com.ultimate.ultimatelinks.exceptions.userEx.UserIsNotExistException;
import com.ultimate.ultimatelinks.mapper.LinkMapper;
import com.ultimate.ultimatelinks.repository.LinkRepo;
import com.ultimate.ultimatelinks.repository.UserRepo;
import com.ultimate.ultimatelinks.util.LinkUtil;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

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
    public String findShortLink(String shortLink) {

        LinkEntity link = linkRepo.findByShortLink(shortLink);

        if (link == null) {
            throw new LinkIsNotExistException("Link is NOT exist!");
        }

        //clickService.createClick(link);
        return link.getSourceLink();
    }
}
