package com.demo.utils;

import javax.servlet.http.HttpServletRequest;

public class BrowserUtil {

    public static String getBrowser(HttpServletRequest request) {
        String UserAgent = request.getHeader("User-Agent").toLowerCase();
        if (UserAgent.contains("msie"))
            return "IE";
        if (UserAgent.contains("firefox"))
            return "FF";
        if (UserAgent.contains("safari"))
            return "SF";
        return "";
    }

}
