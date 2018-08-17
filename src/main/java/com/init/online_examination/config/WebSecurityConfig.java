package com.init.online_examination.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UsernameAuthenticationConfig usernameAuthenticationConfig;
    private AuthenticationEntryPointHandler authenticationEntryPointHandler;
    private AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;
    private ExitSuccessHandler exitSuccessHandler;

    @Autowired
    public void setExitSuccessHandler(ExitSuccessHandler exitSuccessHandler) {
        this.exitSuccessHandler = exitSuccessHandler;
    }

    @Autowired
    public void setUsernameAuthenticationConfig(UsernameAuthenticationConfig usernameAuthenticationConfig) {
        this.usernameAuthenticationConfig = usernameAuthenticationConfig;
    }

    @Autowired
    public void setAuthenticationEntryPointHandler(AuthenticationEntryPointHandler authenticationEntryPointHandler) {
        this.authenticationEntryPointHandler = authenticationEntryPointHandler;
    }

    @Autowired
    public void setAuthenticationAccessDeniedHandler(AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler) {
        this.authenticationAccessDeniedHandler = authenticationAccessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
//                .anyRequest()
//                .permitAll()
                .antMatchers("/api/**", "/")
                .authenticated()
                .and()
                .apply(usernameAuthenticationConfig)//使用此套配置执行认证
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointHandler)
                .accessDeniedHandler(authenticationAccessDeniedHandler)
                .and()
                .rememberMe()
                .tokenValiditySeconds(60 * 60 * 24 * 3)
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(exitSuccessHandler);
    }
}
