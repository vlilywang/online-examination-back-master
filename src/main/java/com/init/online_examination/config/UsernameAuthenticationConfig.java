package com.init.online_examination.config;

import com.init.online_examination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class UsernameAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private UserService userService;
    private UsernameAuthenticationSuccessHandler usernameAuthenticationSuccessHandler;
    private UsernameAuthenticationFailureHandler usernameAuthenticationFailureHandler;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUsernameAuthenticationSuccessHandler(UsernameAuthenticationSuccessHandler usernameAuthenticationSuccessHandler) {
        this.usernameAuthenticationSuccessHandler = usernameAuthenticationSuccessHandler;
    }

    @Autowired
    public void setUsernameAuthenticationFailureHandler(UsernameAuthenticationFailureHandler usernameAuthenticationFailureHandler) {
        this.usernameAuthenticationFailureHandler = usernameAuthenticationFailureHandler;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        UsernameAuthenticationFilter usernameAuthenticationFilter = new UsernameAuthenticationFilter();
        usernameAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        usernameAuthenticationFilter.setAuthenticationSuccessHandler(usernameAuthenticationSuccessHandler);
        usernameAuthenticationFilter.setAuthenticationFailureHandler(usernameAuthenticationFailureHandler);
        UsernameAuthenticationProvider usernameAuthenticationProvider = new UsernameAuthenticationProvider(userService);
        builder.authenticationProvider(usernameAuthenticationProvider)
                .addFilterBefore(usernameAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
