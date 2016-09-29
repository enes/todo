package com.todo.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Service
public class CookieService {
    public static final int ONE_DAY = 24 * 60 * 60;

    public String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public void persistCookie(HttpServletResponse response, String cookieName, String value) {
        StringBuilder header = new StringBuilder(cookieName + "=" + value);
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.SECOND, ONE_DAY);
        header.append("; Max-Age=").append(ONE_DAY);
        header.append("; Expires=").append(new SimpleDateFormat("dd MMM yyyy kk:mm:ss z", Locale.US).format(cal.getTime()));
        response.addHeader("Set-Cookie", header.toString());
    }

    public void deleteCookie(String cookieName, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
