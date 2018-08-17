package com.init.online_examination.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class UsernameAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.addHeader("Location", "/teacher.html");
        response.setStatus(302);
//        PrintWriter out = response.getWriter();
//        out.flush();
//        String s = "登录成功";
//        out.write(s, 0, s.length());
//        out.flush();
//        out.close();
    }
}
