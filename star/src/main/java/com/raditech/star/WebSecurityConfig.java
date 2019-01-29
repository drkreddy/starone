package com.raditech.star;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/oauth_login")
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .oauth2Login()
                    .successHandler(getmyhandler())
                    .loginPage("/oauth_login");

        }

        @Bean
        AuthenticationSuccessHandler getmyhandler(){
             return new MyAuthHandler();
        }
    }

