package com.zaurtregulov.spring.security.configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

//тут будут прописана username and password нашей компании
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Override // аунтификация
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //дефолтное шифрование паролей
        UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser(userBuilder.username("zaur").password("zaur").roles("EMPLOYEE"))
                .withUser(userBuilder.username("elena").password("elena").roles("HR"))
                .withUser(userBuilder.username("ivan").password("ivan").roles("MANAGER", "HR"));

    }

    @Override // авторизация
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").hasAnyRole("EMPLOYEE", "HR", "MANAGER")
                .antMatchers("/hr_info").hasRole("HR")
                .antMatchers("/manager_info").hasRole("MANAGER")
                .and().formLogin().permitAll();// форма логина и пароля будет запрашиватся у всех
             //   .antMatchers("/manager_info/**).hasRole("MANAGER"); // доступ на любой адрес **
    }
}
