package com.ultimate.ultimatelinks.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class LinkUtil {

    public String hashLink(String sourceLink) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(sourceLink.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.substring(0,6);
        } catch (NoSuchAlgorithmException e) {
            return e.getMessage();
        }
    }
}
