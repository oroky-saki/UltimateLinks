package com.ultimate.ultimatelinks.service;

import com.ultimate.ultimatelinks.entities.LinkClickEntity;
import com.ultimate.ultimatelinks.entities.LinkEntity;
import com.ultimate.ultimatelinks.repository.LinkClickRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class LinkClickService {
    private final LinkClickRepo clickRepo;

    @Transactional
    public void createClick(LinkEntity link) {
        LinkClickEntity newClick = new LinkClickEntity(link, LocalDateTime.now(ZoneId.of("Asia/Yekaterinburg")));
        clickRepo.save(newClick);
    }

    public Long getClicksCount(Long linkID) {
        return clickRepo.countByLinkId(linkID);
    }
}
