package com.RSVP.rsvp.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@EnableWebMvcSecurity
@Configuration
@ImportResource("classpath:security.xml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

}
