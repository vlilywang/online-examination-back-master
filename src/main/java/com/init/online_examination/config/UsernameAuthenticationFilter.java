package com.init.online_examination.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UsernameAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected UsernameAuthenticationFilter() {
        super(new AntPathRequestMatcher("/auth/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        //获取用户名和密码
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        if (username == null || password == null) {
            username = "";
            password = "";
        }
        username = username.trim();
        password = password.trim();
        Map<String, String> principal = new HashMap<>();
        principal.put("username", username);
        principal.put("password", password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, null);
        authenticationToken.setDetails(authenticationDetailsSource.buildDetails(httpServletRequest));
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}
