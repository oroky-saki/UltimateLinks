package com.ultimate.ultimatelinks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkService {

    @Value("${app-domain: http://localhost:8080/}")
    private String domain;

    public String checkSP() {
        return domain;
    }

}
