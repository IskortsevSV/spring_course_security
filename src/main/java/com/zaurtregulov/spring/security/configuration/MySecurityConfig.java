package com.zaurtregulov.spring.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

import javax.sql.DataSource;

//тут будут прописана username and password нашей компании
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override // аунтификация
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //берем информация о юзерах из БД
        auth.jdbcAuthentication().dataSource(dataSource);

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
