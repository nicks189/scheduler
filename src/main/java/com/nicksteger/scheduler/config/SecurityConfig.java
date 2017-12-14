package com.nicksteger.scheduler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests().antMatchers("/", "/scheduler", "/console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/scheduler/login").permitAll()
                .defaultSuccessUrl("/scheduler", true)
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/scheduler");
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }

}
