package com.ultimate.ultimatelinks.util;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LinkUtil {

    private Random random = new Random();
    private char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    private char getRandomChar() {
        return chars[random.nextInt(chars.length)];
    }

    public String hashLink(String sourceLink) {

        String code = "";
        for (int i = 0; i < 7; i++) {
            code += getRandomChar();
        }
        return code;
    }

    public String getSiteFromSource(String url) {

        // Регулярное выражение для извлечения названия сайта
        Pattern pattern = Pattern.compile("https://(.*?)/");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "google.com";
        }
    }
}
