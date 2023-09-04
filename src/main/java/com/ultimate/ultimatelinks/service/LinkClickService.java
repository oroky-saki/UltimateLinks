package com.ultimate.ultimatelinks.service;

import com.ultimate.ultimatelinks.entities.LinkClickEntity;
import com.ultimate.ultimatelinks.entities.LinkEntity;
import com.ultimate.ultimatelinks.repository.LinkClickRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class LinkClickService {
    private final LinkClickRepo clickRepo;

    public void createClick(LinkEntity link) {
        LinkClickEntity newClick = new LinkClickEntity(link, LocalDateTime.now(ZoneId.of("Asia/Yekaterinburg")));
        clickRepo.save(newClick);
    }

    public Long getClicksCount(Long linkID) {
        return clickRepo.countByLinkId(linkID);
    }
}
