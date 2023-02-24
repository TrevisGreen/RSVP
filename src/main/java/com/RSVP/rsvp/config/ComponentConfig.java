package com.RSVP.rsvp.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {
        "com.RSVP.rsvp.model",
        "com.RSVP.rsvp.dao",
        "com.RSVP.rsvp.utils",
        "com.RSVP.rsvp.service"})
@PropertySource("classpath:rsvp.properties")
public class ComponentConfig {
}
